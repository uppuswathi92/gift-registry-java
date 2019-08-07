package com.uppu.giftregistry.service;

import org.springframework.stereotype.Service;

import com.uppu.giftregistry.model.RegistryUser;

public interface RegistryUserService {
	String addUser(RegistryUser user);
}
