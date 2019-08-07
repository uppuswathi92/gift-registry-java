package com.uppu.giftregistry.dao;

import com.uppu.giftregistry.model.RegistryUser;

public interface LoginDao {
	boolean login(String username, String password);
}
