package com.uppu.giftregistry.service;

import java.util.List;

public interface InviteeService {
	String addInvitee(String eventId, String username, boolean isHost);
	List<String> getEventIdsByUsername(String username, String isHost);
	List<String> getInvitees(String eventId);
	String deleteInvitees(String eventId);
	String deleteInviteeByName(String username, String eventId);
	void sendEmailNotification(String eventId, String status, String emailId, String rsvp);
	String getEmailIdByUsername(String username);
	String getRsvp(String eventId, String username);
	String updateRsvp(String eventId, String username, String status);
}
