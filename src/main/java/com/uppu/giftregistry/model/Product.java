package com.uppu.giftregistry.model;

public class Product {
	private String productId;
	private String productColor;
	private String productLink;
	private String productName;
	private boolean isPurchased;
	private String purchasedBy;
	private String eventId;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(String productName, String productLink, String productColor, String eventId) {
		super();
		this.productColor = productColor;
		this.productLink = productLink;
		this.productName = productName;
		this.eventId = eventId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public Product(String productId, String productColor, String productLink, String productName, boolean isPurchased,
			String purchasedBy, String eventId) {
		super();
		this.productId = productId;
		this.productColor = productColor;
		this.productLink = productLink;
		this.productName = productName;
		this.isPurchased = isPurchased;
		this.purchasedBy = purchasedBy;
		this.eventId = eventId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductColor() {
		return productColor;
	}
	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}
	public String getProductLink() {
		return productLink;
	}
	public void setProductLink(String productLink) {
		this.productLink = productLink;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public boolean isPurchased() {
		return isPurchased;
	}
	public void setPurchased(boolean isPurchased) {
		this.isPurchased = isPurchased;
	}
	public String getPurchasedBy() {
		return purchasedBy;
	}
	public void setPurchasedBy(String purchasedBy) {
		this.purchasedBy = purchasedBy;
	}
	
}
