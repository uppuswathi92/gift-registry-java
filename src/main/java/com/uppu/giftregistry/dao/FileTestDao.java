package com.uppu.giftregistry.dao;

public interface FileTestDao {
	String fileUpload(byte[] bytes);
	String getImage();
	String sendEmail();
}
