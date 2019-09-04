package com.uppu.giftregistry.dao;

import java.util.List;

import com.uppu.giftregistry.model.Product;

public interface ProductsDao {
	String addProduct(Product product);
	List<Product> getProducts(String eventId);
	Product getProductById(String productId);
	String updateProduct(Product product);
	String purchaseProduct(String productId, String username, String eventId);
	String deleteProductByEvent(String eventId);
	boolean deleteProductById(String productId);
}
