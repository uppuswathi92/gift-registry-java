package com.uppu.giftregistry.controller;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	//private List<RegistryUser> users = createList();

	@GetMapping(produces = "application/json")
	public HashMap<String, Object> getUsers() {
		HashMap<String, Object> userDetails = new HashMap<String, Object>();
		List<RegistryUser> users = userService.getUsers();
		userDetails.put("service", "getUsers");
		userDetails.put("results", users);
		return userDetails;
	}

	/*@DeleteMapping(path = { "/{username}" })
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
	}*/

	@PostMapping
	public HashMap<String, Object> create(@RequestBody RegistryUser user) {
		HashMap<String, Object> userDetails = new HashMap<String, Object>();
		userDetails.put("service", "register");
		userService.addUser(user);
		userDetails.put("results", "registered");
		return userDetails;
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
	}
	
	@GetMapping(path = { "userExists/{username}/{email}" })
	public HashMap<String, Object> userExists(@PathVariable("username") String username, @PathVariable("email") String email) {
		HashMap<String, Object> userDetails = new HashMap<String, Object>();
		userDetails.put("service", "userExists");
		boolean exists = userService.userExists(username, email);
		userDetails.put("results", exists);
		return userDetails;
	}
}
