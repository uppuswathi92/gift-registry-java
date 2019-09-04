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

}
