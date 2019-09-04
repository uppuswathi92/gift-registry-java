package com.uppu.giftregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppu.giftregistry.dao.ProductsDao;
import com.uppu.giftregistry.model.Product;
import com.uppu.giftregistry.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService{
	@Autowired
	ProductsDao productsDao;

	public String addProduct(Product product) {
		return productsDao.addProduct(product);
	}

	public List<Product> getProducts(String eventId) {
		return productsDao.getProducts(eventId);
	}

	public Product getProductById(String productId) {
		// TODO Auto-generated method stub
		return productsDao.getProductById(productId);
	}

	public String updateProduct(Product product) {
		// TODO Auto-generated method stub
		return productsDao.updateProduct(product);
	}

	public String purchaseProduct(String productId, String username, String eventId) {
		// TODO Auto-generated method stub
		return productsDao.purchaseProduct(productId, username, eventId);
	}

	public String deleteProductByEvent(String eventId) {
		// TODO Auto-generated method stub
		return productsDao.deleteProductByEvent(eventId);
	}

	public boolean deleteProductById(String productId) {
		// TODO Auto-generated method stub
		return productsDao.deleteProductById(productId);
	}
}
