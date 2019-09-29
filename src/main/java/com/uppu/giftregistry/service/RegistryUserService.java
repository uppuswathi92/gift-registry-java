package com.uppu.giftregistry.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uppu.giftregistry.model.RegistryUser;

public interface RegistryUserService {
	String addUser(RegistryUser user);
	List<RegistryUser> getUsers();
	boolean userExists(String username, String email);
}
