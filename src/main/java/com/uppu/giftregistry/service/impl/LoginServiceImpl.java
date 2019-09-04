package com.uppu.giftregistry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppu.giftregistry.dao.LoginDao;
import com.uppu.giftregistry.model.RegistryUser;
import com.uppu.giftregistry.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;
	public String login(String username, String password) {
		return loginDao.login(username, password);
	}

}
