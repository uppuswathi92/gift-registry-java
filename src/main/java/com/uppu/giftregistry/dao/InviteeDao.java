package com.uppu.giftregistry.dao;

import java.util.List;

public interface InviteeDao {
	String addInvitee(String eventId, String username, boolean isHost);
	List<String> getEventIdsByUsername(String username, String isHost);
	List<String> getInvitees(String eventId);
	String deleteInvitees(String eventId);
	String deleteInviteeByName(String username, String eventId);
}
