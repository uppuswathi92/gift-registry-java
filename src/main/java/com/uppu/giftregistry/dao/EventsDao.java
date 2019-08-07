package com.uppu.giftregistry.dao;

import com.uppu.giftregistry.model.Events;

public interface EventsDao {
	String addEvent(Events events, String username);
}
