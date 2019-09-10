package com.uppu.giftregistry.model;

public class Events {
	public Events() {
		super();
	}
	private String eventId;
	private String eventName;
	private String eventAddress;
	private String eventDate;
	private String username;
	private String eventMsg;
	private boolean host;
	public boolean isHost() {
		return host;
	}
	public void setHost(boolean host) {
		this.host = host;
	}
	public String getEventMsg() {
		return eventMsg;
	}
	public void setEventMsg(String eventMsg) {
		this.eventMsg = eventMsg;
	}
	public Events(String eventId, String eventName, String eventAddress, String eventDate, String username, String eventMsg) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventAddress = eventAddress;
		this.eventDate = eventDate;
		this.username = username;
		this.eventMsg = eventMsg;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventAddress() {
		return eventAddress;
	}
	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
}
