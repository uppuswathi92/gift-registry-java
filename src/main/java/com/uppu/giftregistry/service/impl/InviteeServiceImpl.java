package com.uppu.giftregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppu.giftregistry.dao.InviteeDao;
import com.uppu.giftregistry.service.InviteeService;

@Service
public class InviteeServiceImpl implements InviteeService{

	@Autowired
	InviteeDao inviteeDao;
	public String addInvitee(String eventId, String username, boolean isHost) {
		// TODO Auto-generated method stub
		return inviteeDao.addInvitee(eventId, username, isHost);
	}
	public List<String> getEventIdsByUsername(String username, String isHost) {
		return inviteeDao.getEventIdsByUsername(username,isHost);
	}
	public List<String> getInvitees(String eventId) {
		return inviteeDao.getInvitees(eventId);
	}
	public String deleteInvitees(String eventId) {
		// TODO Auto-generated method stub
		return inviteeDao.deleteInvitees(eventId);
	}
	public String deleteInviteeByName(String username, String eventId) {
		// TODO Auto-generated method stub
		return inviteeDao.deleteInviteeByName(username, eventId);
	}
	public void sendEmailNotification(String eventId, String status, String emailId, String rsvp) {
		// TODO Auto-generated method stub
		inviteeDao.sendEmailNotification(eventId, status, emailId, rsvp);
	}
	public String getEmailIdByUsername(String username) {
		// TODO Auto-generated method stub
		return inviteeDao.getEmailIdByUsername(username);
	}
	public String getRsvp(String eventId, String username) {
		// TODO Auto-generated method stub
		return inviteeDao.getRsvp(eventId, username);
	}
	public String updateRsvp(String eventId, String username, String status) {
		// TODO Auto-generated method stub
		return inviteeDao.updateRsvp(eventId, username, status);
	}

}
