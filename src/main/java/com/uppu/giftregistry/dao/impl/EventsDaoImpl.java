package com.uppu.giftregistry.dao.impl;

import java.util.Date;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.uppu.giftregistry.dao.EventsDao;
import com.uppu.giftregistry.model.Events;
import com.uppu.giftregistry.service.InviteeService;
import com.uppu.giftregistry.service.ProductsService;

@Repository
public class EventsDaoImpl extends JdbcDaoSupport implements EventsDao {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	InviteeService inviteeService;
	@Autowired
	ProductsService productsService;
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}
	public String addEvent(Events event) {
		String eventId = getEventId();
		String addEventQuery = "INSERT INTO events " +
				"(eventId, eventName, eventAddress, eventDateTime, eventMsg) VALUES (?,?,?,?,?)" ;
		getJdbcTemplate().update(addEventQuery, new Object[]{
				eventId, event.getEventName(), event.getEventAddress(), event.getEventDate(), event.getEventMsg()
		});
		inviteeService.addInvitee(eventId, event.getUsername(), true);
		return null;
	}
	
	public String getEventId() {
		String eventId = "" + Math.round((Math.random()) *100000);
		while(!isValidEventId(eventId)) {
			eventId = "" + Math.round((Math.random()) *100000);
		}
		return eventId;
	}
	
	public boolean isValidEventId(String eventId) {
		List<String> eventIds = getEventIds();
		for(String eId: eventIds) {
			if(eId.equals(eventId)) {
				return false;
			}
		}
		return true;
	}
	
	public List<String> getEventIds(){
		List<String> eventIds = new ArrayList<String>();
		String sql = "SELECT eventId FROM events";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for(Map<String, Object> row:rows){
			eventIds.add((String)row.get("eventId"));
		}
		return eventIds;
	}
	public List<Events> getEvents(String username, String isHost) {
		List<Events> events = new ArrayList<Events>();
		List<String> eventIds = inviteeService.getEventIdsByUsername(username, isHost);
		for(String eventId: eventIds) {
			String eventsQuery =  "select * from events where eventId='"+eventId+"'";
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(eventsQuery);
			for(Map<String, Object> row:rows){
				Events event = new Events((String)row.get("eventId"), (String)row.get("eventName"), (String)row.get("eventAddress"), (String)row.get("eventDateTime"), username, (String)row.get("eventMsg"));
				events.add(event);
			}
		}
		return events;
	}
	public Events getEventById(int eventId) {
		Events event = new Events();
		String sql = "SELECT * FROM events where eventId = "+eventId;
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for(Map<String, Object> row:rows){
			event.setEventName((String)row.get("eventName"));
			event.setEventAddress((String)row.get("eventAddress"));
			event.setEventDate((String)row.get("eventDateTime"));
			event.setEventMsg((String)row.get("eventMsg"));
		}
		return event;
	}
	public String updateEvent(Events event) {
		String updateSql = "UPDATE events SET eventName = ?, eventAddress = ?, eventDateTime = ?, eventMsg= ?  WHERE eventId = ?";
		Object[] params = { event.getEventName(), event.getEventAddress(), event.getEventDate(), event.getEventMsg(), event.getEventId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
		int rows = getJdbcTemplate().update(updateSql, params, types);
		if(rows > 0) {
			sendUpdates(event.getEventId());
		}
		return null;
	}
	
	public void sendUpdates(String eventId) {
		String inviteeQuery = "select inviteeName from invitees where eventId='"+eventId +"' and isHost=0";
		List<String> invitees = new ArrayList<String>();
		List<String> inviteesEmail = new ArrayList<String>();
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(inviteeQuery);
		for(Map<String, Object> row:rows){
			invitees.add((String)row.get("inviteeName"));
		}
		for(String username: invitees) {
			String email = inviteeService.getEmailIdByUsername(username);
			inviteesEmail.add(email);
		}
		for(String email: inviteesEmail) {
			inviteeService.sendEmailNotification(eventId, "updateevent", email, "");
		}
	}
	
	public String deleteEvent(String eventId) {
		inviteeService.deleteInvitees(eventId);
		productsService.deleteProductByEvent(eventId);
		String deleteQuery = "DELETE from events where eventId=?";
		getJdbcTemplate().update(deleteQuery, new Object[]{
				 eventId
		});
		return null;
	}
	public int eventNotifications(String username) {
		String notificationQuery = "select * from invitees where inviteeName='"+username+"' and notified='no'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(notificationQuery);
		return rows.size();
	}
	public String updateNotifications(String username) {
		String updateSql = "UPDATE invitees SET notified = ?  WHERE inviteeName = ?";
		Object[] params = { "yes", username};
		int[] types = {Types.VARCHAR, Types.VARCHAR};
		getJdbcTemplate().update(updateSql, params, types);
		return null;
	}
	
	public List<Events> getUpcomingEvents(String username){
		List<Events> events = new ArrayList<Events>();
		List<Events> myEvents = getEvents(username, "1");
		for(Events event: myEvents) {
			String eventDate = event.getEventDate().split(" at ")[0];
		    Date eDate;
		    Date currentDate = new Date();
			try {
				eDate = new SimpleDateFormat("MM-dd-yyyy").parse(eventDate);
				long diffInMillies = eDate.getTime() - currentDate.getTime();
			    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    if(diff <= 7 && diff >= 0) {
			    	event.setHost(true);
			    	events.add(event);
			    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Events> otherEvents = getEvents(username, "0");
		for(Events event: otherEvents) {
			String eventDate = event.getEventDate().split(" at ")[0];
		    Date eDate;
		    Date currentDate = new Date();
			try {
				eDate = new SimpleDateFormat("MM-dd-yyyy").parse(eventDate);
				long diffInMillies = Math.abs(eDate.getTime() - currentDate.getTime());
			    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    if(diff <= 7 && diff >= 0) {
			    	event.setHost(false);
			    	events.add(event);
			    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return events;
	}
	public boolean validateUserForEvent(String username, String eventId) {
		String eventQuery = "select * from invitees where inviteeName='"+username+"' and eventId='"+eventId+"'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(eventQuery);
		if(rows.size() > 0) {
			return true;
		}
		return false;
	}
}
