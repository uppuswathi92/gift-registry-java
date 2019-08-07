package com.uppu.giftregistry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppu.giftregistry.dao.EventsDao;
import com.uppu.giftregistry.model.Events;
import com.uppu.giftregistry.service.EventsService;

@Service
public class EventsServiceImpl implements EventsService{
	@Autowired
	EventsDao eventsDao;

	public String addEvent(Events events, String username) {
		return eventsDao.addEvent(events, username);
	}
}
