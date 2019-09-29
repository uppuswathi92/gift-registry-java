package com.uppu.giftregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppu.giftregistry.dao.EventsDao;
import com.uppu.giftregistry.model.Events;
import com.uppu.giftregistry.service.EventsService;

@Service
public class EventsServiceImpl implements EventsService{
	@Autowired
	EventsDao eventsDao;

	public String addEvent(Events event) {
		return eventsDao.addEvent(event);
	}

	public List<Events> getEvents(String username, String isHost) {
		return eventsDao.getEvents(username, isHost);
	}

	public Events getEventById(int eventId) {
		return eventsDao.getEventById(eventId);
	}

	public String updateEvent(Events event) {
		return eventsDao.updateEvent(event);
	}

	public String deleteEvent(String eventId) {
		// TODO Auto-generated method stub
		return eventsDao.deleteEvent(eventId);
	}

	public int eventNotifications(String username) {
		return eventsDao.eventNotifications(username);
	}

	public String updateNotifications(String username) {
		// TODO Auto-generated method stub
		return eventsDao.updateNotifications(username);
	}

	public List<Events> getUpcomingEvents(String username) {
		// TODO Auto-generated method stub
		return eventsDao.getUpcomingEvents(username);
	}

	public boolean validateUserForEvent(String username, String eventId) {
		// TODO Auto-generated method stub
		return eventsDao.validateUserForEvent(username, eventId);
	}
}
