package com.uppu.giftregistry.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uppu.giftregistry.model.Events;
import com.uppu.giftregistry.service.EventsService;
import com.uppu.giftregistry.service.InviteeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Controller
@RequestMapping({ "/events" })
public class EventsController {
	@Autowired
	EventsService eventsService;
	@Autowired
	InviteeService inviteeService;
	@PostMapping
	public HashMap<String, Object> addEvent(@RequestBody Events event) {
		HashMap<String, Object> eventDetails = new HashMap<String, Object>();
		eventDetails.put("service", "addEvent");
		eventDetails.put("results", event);
		eventsService.addEvent(event);
		return eventDetails;
	}
	
	@GetMapping(path = { "/{username}" })
	public HashMap<String, Object> getEvents(@PathVariable("username") String username) {
		HashMap<String, Object> eventDetails = new HashMap<String, Object>();
		List<Events> events= eventsService.getEvents(username,"1");
		eventDetails.put("service", "getEvents");
		eventDetails.put("results", events);
		return eventDetails;
	}
	@GetMapping(path = { "otherEvents/{username}" })
	public HashMap<String, Object> getOtherEvents(@PathVariable("username") String username) {
		HashMap<String, Object> eventDetails = new HashMap<String, Object>();
		List<Events> events = eventsService.getEvents(username,"0");
		eventDetails.put("service", "getOtherEvents");
		eventDetails.put("results", events);
		return eventDetails;
	}
	@RequestMapping(method = RequestMethod.GET)
	public HashMap<String, Object> getEventById(@RequestParam(value="eventId") int eventId) {
		HashMap<String, Object> eventDetails = new HashMap<String, Object>();
		Events event =  eventsService.getEventById(eventId);
		eventDetails.put("service", "getEventById");
		eventDetails.put("results", event);
		return eventDetails;
	}
	@RequestMapping(value="/updateEvent",method = { RequestMethod.GET, RequestMethod.POST })
	//@PostMapping(path = { "/updateEvent/{eventId}" })
	public HashMap<String, Object> updateEvent(@RequestBody Events event) {
		HashMap<String, Object> eventDetails = new HashMap<String, Object>();
		eventsService.updateEvent(event);
		eventDetails.put("service", "updateEvent");
		eventDetails.put("results", "updated");
		return eventDetails;
	}
	@GetMapping(path = { "/getInvitees/{eventId}" })
	public HashMap<String, Object> getInvitees(@PathVariable("eventId") String eventId) {
		HashMap<String, Object> inviteeNames = new HashMap<String, Object>();
		List<String> invitees = inviteeService.getInvitees(eventId);
		inviteeNames.put("service", "getInvitees");
		inviteeNames.put("results", invitees);
		return inviteeNames;
	}
	@DeleteMapping(path = { "deleteEvent/{eventId}" })
	public HashMap<String, Object> deleteEvent(@PathVariable("eventId") String eventId) {
		HashMap<String, Object> delete = new HashMap<String, Object>();
		eventsService.deleteEvent(eventId);
		delete.put("service", "deleteEvent");
		delete.put("results", eventId);
		return delete;
	}
	@DeleteMapping(path = { "deleteInvitee/{eventId}/{username}" })
	public HashMap<String, Object> deleteInvitee(@PathVariable("eventId") String eventId, @PathVariable("username") String username) {
		HashMap<String, Object> delete = new HashMap<String, Object>();
		inviteeService.deleteInviteeByName(username, eventId);
		delete.put("service", "deleteInvitee");
		delete.put("results", eventId);
		return delete;
	}
	@GetMapping(path = { "eventNotifications/{username}" })
	public HashMap<String, Object> eventNotification(@PathVariable("username") String username) {
		HashMap<String, Object> notifcation = new HashMap<String, Object>();
		int notifications = eventsService.eventNotifications(username);
		notifcation.put("service", "eventNotification");
		notifcation.put("results", notifications);
		return notifcation;
	}
	@RequestMapping(value="/updateNotification",method = { RequestMethod.POST })
	public HashMap<String, Object> updateNotification(@RequestBody String username) {
		HashMap<String, Object> notifcation = new HashMap<String, Object>();
		notifcation.put("service", "addEvent");
		notifcation.put("results", "updated");
		eventsService.updateNotifications(username);
		return notifcation;
	}
}
