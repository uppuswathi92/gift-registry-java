package com.uppu.giftregistry.service;

import java.util.List;

import com.uppu.giftregistry.model.Events;

public interface EventsService {
	String addEvent(Events event);
	List<Events> getEvents(String username, String isHost);
	Events getEventById(int eventId);
	String updateEvent(Events event);
	String deleteEvent(String eventId);
	int eventNotifications(String username);
	String updateNotifications(String username);
	List<Events> getUpcomingEvents(String username);
	boolean validateUserForEvent(String username, String eventId);
}
