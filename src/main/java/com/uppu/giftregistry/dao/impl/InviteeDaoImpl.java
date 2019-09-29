package com.uppu.giftregistry.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import com.uppu.giftregistry.dao.InviteeDao;

@Repository
public class InviteeDaoImpl extends JdbcDaoSupport implements InviteeDao{
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
    private JavaMailSender mailSender;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}
	public String addInvitee(String eventId, String username, boolean isHost) {
		String notified = isHost ? "yes":"no";
		String addInviteeQuery = "INSERT INTO invitees " +
				"(eventId, inviteeName, isHost, notified, rsvp) VALUES (?,?,?,?,?)" ;
		String rsvp = isHost ? "attending":"no response";
		getJdbcTemplate().update(addInviteeQuery, new Object[]{
				eventId, username, isHost,notified,rsvp
		});
		String emailId = getEmailIdByUsername(username);
		if(!isHost) {
			sendEmailNotification(eventId, "newevent", emailId, "");
		}
		return null;
	}
	
	public String getEmailIdByUsername(String username) {
		String emailId = "";
		String emailSql = "select email from registryuser where username='"+username+"'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(emailSql);
		for(Map<String, Object> row:rows){
			emailId = (String)row.get("email");
		}
		return emailId;
	}
	public List<String> getEventIdsByUsername(String username, String isHost) {
		String eventIdQuery = "select eventId from invitees where inviteeName='"+username+"' and isHost="+isHost;
		List<String> eventIds = new ArrayList<String>();
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(eventIdQuery);
		for(Map<String, Object> row:rows){
			eventIds.add((String)row.get("eventId"));
		}
		return eventIds;
	}
	public List<String> getInviteesUsernames(String eventId) {
		String eventIdQuery = "select inviteeName from invitees where eventId='"+eventId+"' and isHost=0";
		List<String> usernames = new ArrayList<String>();
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(eventIdQuery);
		for(Map<String, Object> row:rows){
			usernames.add((String)row.get("inviteeName"));
		}
		//System.out.println(usernames.size());
		return usernames;
	}
	public List<String> getInvitees(String eventId) {
		List<String> usernames = getInviteesUsernames(eventId);
		List<String> invitees = new ArrayList<String>();
		for(String username: usernames) {
			String inviteesQuery = "SELECT firstname, lastname FROM registryuser where username = '"+ username + "'";
			String rsvp = getRsvp(eventId, username);
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(inviteesQuery);
			for(Map<String, Object> row:rows){
				invitees.add((String)row.get("firstname") + " " + (String)row.get("lastname") + "-" + username + "-"+rsvp);
			}
		}
		return invitees;
	}
	public String deleteInvitees(String eventId) {
		String deleteQuery = "delete from invitees where eventId=?";
		getJdbcTemplate().update(deleteQuery, new Object[]{
				 eventId
		});
		return null;
	}
	public String deleteInviteeByName(String username, String eventId) {
		String deleteQuery = "delete from invitees where eventId=? and inviteeName=?";
		getJdbcTemplate().update(deleteQuery, new Object[]{
				 eventId,username
		});
		return null;
	}
	public void sendEmailNotification(String eventId, String status, String emailId, String rsvp) {
		 String result =null;
		    MimeMessage message =mailSender.createMimeMessage();
		    try {
		        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
		        String header = "";
		        String footer = "";
		        String redirectUrl = "";
		        if(status.equals("updateevent")) {
		        	redirectUrl = "http://localhost:4200/viewevent?eventId="+eventId + "&event=other";
		        }else if(status.equals("eventrsvp")) {
		        	redirectUrl = "http://localhost:4200/addinvitees?eventId="+eventId;
		        }
		        String htmlMsg = setContent(status, redirectUrl, rsvp);
		        /*String content = setContent(status, redirectUrl);
				try {
					File file = new ClassPathResource("templates/email/header-template.html").getFile();
					header = new String(Files.readAllBytes(file.toPath()));
					file = new ClassPathResource("templates/email/footer-template.html").getFile();
					footer = new String(Files.readAllBytes(file.toPath()));
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				//htmlMsg = header + content + footer; 
		        message.setContent(htmlMsg, "text/html");
		        helper.setTo(emailId);
		        helper.setSubject("subject");
		        result="success";
		        mailSender.send(message);
		    } catch (MessagingException e) {
		        throw new MailParseException(e);
		    }finally {
		        if(result !="success"){
		            result="fail";
		        }
		    }
	}
	public String setContent(String status, String redirectUrl, String rsvp) {
		String content = "", htmlMsg = "", header = "", footer = "";
		if(status.equals("newevent")) {
			try {
				File file = new ClassPathResource("templates/email/new-event-template.html").getFile();
				content = new String(Files.readAllBytes(file.toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			htmlMsg = content;
		}else if(status.equals("updateevent")) {
			try {
				File file = new ClassPathResource("templates/email/event-update-template.html").getFile();
				header = new String(Files.readAllBytes(file.toPath()));
				file = new ClassPathResource("templates/email/footer-template.html").getFile();
				footer = new String(Files.readAllBytes(file.toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			content = "<div align='center' style='line-height: 24px;'>";
	        content += "<a href='"+redirectUrl + "' target='_blank' class='btn btn-primary block-center' style='background-color: #007bff;border-radius: 5px;color: white;padding: .5em;text-decoration: none;'>";
	        content += "<font face=\"Arial, Helvetica, sans-serif\" size=\"3\">Click here to check it out!</font>" + 
	        		"    </a>\r\n" + 
	        		"</div>\r\n" + 
	        		"<div style='height: 60px; line-height: 60px; font-size: 10px;'></div>";
			htmlMsg = header + content + footer;
		}else if(status.equals("eventrsvp")) {
			String templateUrl = (rsvp.equals("attending")?"templates/email/event-rsvp-attending-template.html":"templates/email/event-rsvp-not-attending-template.html");
			try {
				File file = new ClassPathResource(templateUrl).getFile();
				header = new String(Files.readAllBytes(file.toPath()));
				file = new ClassPathResource("templates/email/footer-template.html").getFile();
				footer = new String(Files.readAllBytes(file.toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			content = "<div align='center' style='line-height: 24px;'>";
	        content += "<a href='"+redirectUrl + "' target='_blank' class='btn btn-primary block-center' style='background-color: #007bff;border-radius: 5px;color: white;padding: .5em;text-decoration: none;'>";
	        content += "<font face=\"Arial, Helvetica, sans-serif\" size=\"3\">Click here to check it out!</font>" + 
	        		"    </a>\r\n" + 
	        		"</div>\r\n" + 
	        		"<div style='height: 60px; line-height: 60px; font-size: 10px;'></div>";
			htmlMsg = header + content + footer;
		}
		return htmlMsg;
	}
	public String getRsvp(String eventId, String username) {
		String rsvpQuery = "select * from invitees where eventId='"+eventId +"' and inviteeName='"+username+"'";
		String response = "";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(rsvpQuery);
		for(Map<String, Object> row:rows){
			response = (String)row.get("rsvp");
		}
		return response;
	}
	
	public String getHostusername(String eventId) {
		String hostQuery = "select * from invitees where eventId='"+eventId +"' and isHost=1";
		String username = "";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(hostQuery);
		for(Map<String, Object> row:rows){
			username = (String)row.get("inviteeName");
		}
		return username;
	}
	public String updateRsvp(String eventId, String username, String status) {
		String updateStatus = (status.equals("yes")? "attending": "not attending");
		String updateSql = "UPDATE invitees SET rsvp = ?  WHERE eventId = ? and inviteeName = ?";
		Object[] params = { updateStatus, eventId, username};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
		int rows = getJdbcTemplate().update(updateSql, params, types);
		if(rows > 0) {
			String hostUsername = getHostusername(eventId);
			String hostEmail = getEmailIdByUsername(hostUsername);
			sendEmailNotification(eventId, "eventrsvp", hostEmail, updateStatus);
		}
		return updateStatus;
	}
}
