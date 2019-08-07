package com.uppu.giftregistry.model;

public class Events {
	public Events() {
		super();
	}
	private int eventId;
	private String eventName;
	private String eventAddress;
	private String eventDate;
	public Events(int eventId, String eventName, String eventAddress, String eventDate) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventAddress = eventAddress;
		this.eventDate = eventDate;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
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
