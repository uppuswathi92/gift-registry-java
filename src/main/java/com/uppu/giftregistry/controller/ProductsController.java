package com.uppu.giftregistry.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uppu.giftregistry.model.Events;
import com.uppu.giftregistry.model.Product;
import com.uppu.giftregistry.service.ProductsService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Controller
@RequestMapping({ "/products" })
public class ProductsController {
	@Autowired
	ProductsService productsService;
	
	@PostMapping
	public HashMap<String, Object> addProduct(@RequestBody Product product) {
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		productDetails.put("service", "addProduct");
		String productId = productsService.addProduct(product);
		productDetails.put("results", productId);
		return productDetails;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "uploadImage/{productId}")
	public HashMap<String, Object> uploadImage(@PathVariable String productId, @RequestParam("myFile") MultipartFile file) {
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		productDetails.put("service", "uploadImage");
		try {
			System.out.println(productId);
			//System.out.println(file.getBytes());
			//System.out.println(file.getOriginalFilename());
			productsService.uploadImage(productId, file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productDetails.put("results", productId);
		//
		return productDetails;
	}
	@GetMapping(path = { "/getProducts/{eventId}" })
	public HashMap<String, Object> getInvitees(@PathVariable("eventId") String eventId) {
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		List<Product> products = productsService.getProducts(eventId);
		productDetails.put("service", "getProducts");
		productDetails.put("results", products);
		return productDetails;
	}
	@GetMapping(path = { "/getProductsById/{productId}" })
	public HashMap<String, Object> getProductById(@PathVariable("productId") String productId) {
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		Product products = productsService.getProductById(productId);
		productDetails.put("service", "getProductsById");
		productDetails.put("results", products);
		return productDetails;
	}
	@RequestMapping(value="/updateProduct",method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateProduct(@RequestBody Product product) {
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		productsService.updateProduct(product);
		productDetails.put("service", "updateProduct");
		productDetails.put("results", "updated");
		return productDetails;
	}
	@PostMapping(value="/purchaseProduct")
	public HashMap<String, Object> purchaseProduct(@RequestBody Product product) {
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		productDetails.put("service", "purchaseProduct");
		productDetails.put("results", product);
		productsService.purchaseProduct(product.getProductId(), product.getPurchasedBy(), product.getEventId());
		//System.out.println(username);
		
		return productDetails;
	}
	@DeleteMapping(path = { "deleteProduct/{productId}" })
	public HashMap<String, Object> deleteProduct(@PathVariable("productId") String productId) {
		HashMap<String, Object> deleteProd = new HashMap<String, Object>();
		deleteProd.put("service", "deleteProduct");
		deleteProd.put("results", productId);
		productsService.deleteProductById(productId);
		//System.out.println(username);
		
		return deleteProd;
	}
}
