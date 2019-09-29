package com.uppu.giftregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppu.giftregistry.dao.AddUserDao;
import com.uppu.giftregistry.dao.UserDao;
import com.uppu.giftregistry.model.RegistryUser;
import com.uppu.giftregistry.service.RegistryUserService;

@Service
public class RegistryUserServiceImpl implements RegistryUserService{

	@Autowired
	AddUserDao addUserDao;
	
	@Autowired
	UserDao userDao;
	public String addUser(RegistryUser user) {
		return addUserDao.addUser(user);
	}
	public List<RegistryUser> getUsers() {
		return userDao.getUsers();
	}
	public boolean userExists(String username, String email) {
		// TODO Auto-generated method stub
		return userDao.userExists(username, email);
	}

}
