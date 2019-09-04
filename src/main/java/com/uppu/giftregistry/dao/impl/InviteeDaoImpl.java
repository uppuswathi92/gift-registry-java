package com.uppu.giftregistry.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.uppu.giftregistry.dao.InviteeDao;

@Repository
public class InviteeDaoImpl extends JdbcDaoSupport implements InviteeDao{
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}
	public String addInvitee(String eventId, String username, boolean isHost) {
		String notified = isHost ? "yes":"no";
		String addInviteeQuery = "INSERT INTO invitees " +
				"(eventId, inviteeName, isHost, notified) VALUES (?,?,?,?)" ;
		getJdbcTemplate().update(addInviteeQuery, new Object[]{
				eventId, username, isHost,notified
		});
		return null;
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
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(inviteesQuery);
			for(Map<String, Object> row:rows){
				invitees.add((String)row.get("firstname") + " " + (String)row.get("lastname") + "-" + username);
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

}
