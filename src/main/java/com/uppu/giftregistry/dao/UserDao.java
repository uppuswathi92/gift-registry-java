package com.uppu.giftregistry.dao;

import java.util.List;

import com.uppu.giftregistry.model.RegistryUser;

public interface UserDao {
	List<RegistryUser> getAllUsers();
	boolean userExists(String username, String email);
	RegistryUser getUserByUsername(String username);
	RegistryUser getUserByEmail(String email);
}
