package com.uppu.giftregistry.model;

public class ImageModel {
	String id;
	String name;
	String type;
	byte[] pic;
	public ImageModel(String name, String type, byte[] pic) {
		super();
		this.name = name;
		this.type = type;
		this.pic = pic;
	}
	
}
