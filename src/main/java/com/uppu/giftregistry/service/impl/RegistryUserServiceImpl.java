package com.uppu.giftregistry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppu.giftregistry.dao.AddUserDao;
import com.uppu.giftregistry.model.RegistryUser;
import com.uppu.giftregistry.service.RegistryUserService;

@Service
public class RegistryUserServiceImpl implements RegistryUserService{

	@Autowired
	AddUserDao userDao;
	public String addUser(RegistryUser user) {
		return userDao.addUser(user);
	}

}
