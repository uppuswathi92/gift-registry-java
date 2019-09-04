package com.uppu.giftregistry.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.uppu.giftregistry.model.Invitee;
import com.uppu.giftregistry.model.RegistryUser;
import com.uppu.giftregistry.service.InviteeService;
import com.uppu.giftregistry.service.LoginService;
import com.uppu.giftregistry.service.RegistryUserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Controller
@RequestMapping({ "/giftregistry" })
public class RegistryController {
	@Autowired
	RegistryUserService userService;
	@Autowired
	LoginService loginService;
	@Autowired
	InviteeService inviteeService;
	private List<RegistryUser> users = createList();

	@GetMapping(produces = "application/json")
	public HashMap<String, Object> getUsers() {
		HashMap<String, Object> userDetails = new HashMap<String, Object>();
		List<RegistryUser> users = userService.getUsers();
		userDetails.put("service", "getUsers");
		userDetails.put("results", users);
		return userDetails;
	}

	@DeleteMapping(path = { "/{username}" })
	public RegistryUser delete(@PathVariable("username") int username) {
		RegistryUser deletedUser = null;
		for (RegistryUser user : users) {
			if (user.getUsername().equals(username)) {
				users.remove(user);
				deletedUser = user;
				break;
			}
		}
		return deletedUser;
	}

	@PostMapping
	public RegistryUser create(@RequestBody RegistryUser user) {
		users.add(user);
		userService.addUser(user);
		return user;
	}
	
	@GetMapping(path = { "/{username}/{password}" })
	public HashMap<String, Object> login(@PathVariable("username") String username, @PathVariable("password") String password) {
		HashMap<String, Object> userDetails = new HashMap<String, Object>();
		userDetails.put("service", "login");
		String loggedIn = loginService.login(username, password);
		userDetails.put("results", loggedIn);
		return userDetails;
	}
	
	@RequestMapping(value="/addInvitee",method = RequestMethod.GET) 
	public HashMap<String, Object> addInvitee(@RequestParam(value="eventId") String eventId, @RequestParam(value="username") String username) {
		HashMap<String, Object> addInvitees = new HashMap<String, Object>();
		inviteeService.addInvitee(eventId, username, false);
		addInvitees.put("service", "addInvitees");
		addInvitees.put("results", null);
		return addInvitees;
		//System.out.println(idNumber);
		//return null;
	}
	private static List<RegistryUser> createList() {
		
		List<RegistryUser> tempUsers = new ArrayList<RegistryUser>();
		RegistryUser user1 = new RegistryUser();
		user1.setUsername("user1");
		user1.setPassword("user1");
		user1.setEmail("user1@gmail.com");
		user1.setFirstName("user1first");
		user1.setLastName("user1lastname");
		user1.setPhoneNumber("9132036853");

		RegistryUser user2 = new RegistryUser();
		user2.setUsername("user2");
		user2.setPassword("user2");
		user2.setEmail("user2@gmail.com");
		user2.setFirstName("user2first");
		user2.setLastName("user2lastname");
		user2.setPhoneNumber("9132036853");
		tempUsers.add(user1);
		tempUsers.add(user2);
		return tempUsers;
	}
	
}
