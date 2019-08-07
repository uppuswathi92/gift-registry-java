package com.uppu.giftregistry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uppu.giftregistry.model.RegistryUser;
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
	private List<RegistryUser> users = createList();

	@GetMapping(produces = "application/json")
	public List<RegistryUser> firstPage() {
		return users;
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
	public boolean login(@PathVariable("username") String username, @PathVariable("password") String password) {
		return loginService.login(username, password);
	}
	private static List<RegistryUser> createList() {
		System.out.println("here");
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
