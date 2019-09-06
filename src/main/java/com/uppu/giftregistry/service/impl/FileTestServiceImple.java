package com.uppu.giftregistry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppu.giftregistry.dao.FileTestDao;
import com.uppu.giftregistry.service.FileTestService;

@Service
public class FileTestServiceImple implements FileTestService{
	@Autowired
	FileTestDao dao;
	public String fileUpload(byte[] bytes) {
		// TODO Auto-generated method stub
		return dao.fileUpload(bytes);
	}
	public String getImage() {
		// TODO Auto-generated method stub
		return dao.getImage();
	}
	public String sendEmail() {
		return dao.sendEmail();
		
	}

}
