package com.uppu.giftregistry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uppu.giftregistry.model.Events;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Controller
@RequestMapping({ "/events" })
public class EventsController {
	@PostMapping
	public Events create(@RequestBody Events events, String username) {
		Events event = new Events();
		return event;
	}
}
