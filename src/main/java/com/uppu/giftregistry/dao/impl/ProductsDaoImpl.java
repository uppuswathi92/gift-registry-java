package com.uppu.giftregistry.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.uppu.giftregistry.dao.ProductsDao;
import com.uppu.giftregistry.model.Product;

@Repository
public class ProductsDaoImpl extends JdbcDaoSupport implements ProductsDao{

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}
	public String addProduct(Product product) {
		String productId = getProductId();
		String addProductQuery = "INSERT INTO products " +
				"(eventId, productId, productColor, productLink, productName, isPurchased, purchasedBy) VALUES (?,?,?,?,?,?,?)" ;
		getJdbcTemplate().update(addProductQuery, new Object[]{
				product.getEventId(), productId, product.getProductColor(), product.getProductLink(), product.getProductName(), product.isPurchased(), product.getPurchasedBy()
		});
		return null;
	}
	public String getProductId() {
		String productId = "" + Math.round((Math.random()) *100000);
		while(!isValidProductId(productId)) {
			productId = "" + Math.round((Math.random()) *100000);
		}
		return productId;
	}
	public boolean isValidProductId(String productId) {
		List<String> productdIds = getProductIds();
		for(String eId: productdIds) {
			if(eId.equals(productId)) {
				return false;
			}
		}
		return true;
	}
	
	public List<String> getProductIds(){
		List<String> eventIds = new ArrayList<String>();
		String sql = "SELECT eventId FROM events";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for(Map<String, Object> row:rows){
			eventIds.add((String)row.get("eventId"));
		}
		return eventIds;
	}
	public List<Product> getProducts(String eventId) {
		List<Product> products = new ArrayList<Product>();
		String sql = "SELECT * FROM products where eventId='"+eventId+"'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for(Map<String, Object> row:rows){
			boolean isPurchased = (row.get("isPurchased").equals(0))?false:true;
			Product product = new Product((String)row.get("productId"), (String)row.get("productColor"), (String)row.get("productLink"), (String)row.get("productName"), isPurchased, (String)row.get("purchasedBy"), eventId);
			//Product product = new Product(, , , eventId);
			products.add(product);
		}
		return products;
	}
	public Product getProductById(String productId) {
		Product product = new Product();
		String sql = "SELECT * FROM products where productId = "+productId;
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for(Map<String, Object> row:rows){
			product.setEventId((String) row.get("eventId"));
			product.setProductId((String)row.get("productId"));
			product.setProductColor((String)row.get("productColor"));
			product.setProductLink((String)row.get("productLink"));
			product.setProductName((String)row.get("productName"));
			boolean isPurchased = (row.get("isPurchased").equals(0))?false:true;
			product.setPurchased(isPurchased);
			product.setPurchasedBy((String)row.get("purchasedBy"));
		}
		return product;
	}
	public String updateProduct(Product product) {
		String updateSql = "UPDATE products SET productName = ?, productLink = ?, productColor = ?  WHERE productId = ?";
		Object[] params = { product.getProductName(), product.getProductLink(), product.getProductColor(), product.getProductId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
		int rows = getJdbcTemplate().update(updateSql, params, types);
		//System.out.println(rows + " row(s) updated.");
		return null;
	}
	public String purchaseProduct(String productId, String username, String eventId) {
		List<Product> products =getProducts(eventId);
		//System.out.println("username"+username);
		for(Product product: products) {
			//System.out.println(product.getPurchasedBy() + " " + product.isPurchased());
			if(product.getPurchasedBy() != null && product.getPurchasedBy().equals(username) && product.isPurchased()) {
				updateProductPurchase(product.getProductId(), null, 0);
			}
		}
		String updateSql = "UPDATE products SET isPurchased = 1, purchasedBy = ? WHERE productId = ?";
		Object[] params = { username, productId};
		int[] types = {Types.VARCHAR, Types.VARCHAR};
		int rows = getJdbcTemplate().update(updateSql, params, types);
		return null;
	}
	
	public void updateProductPurchase(String productId, String username, int isPurchased) {
		//System.out.println("here" + productId);
		String updateProduct = "UPDATE products SET isPurchased = 0, purchasedBy = ? WHERE productId = ?";
		Object[] params = { null, productId};
		int[] types = {Types.VARCHAR, Types.VARCHAR};
		int rows = getJdbcTemplate().update(updateProduct, params, types);
		return;
	}
	public String deleteProductByEvent(String eventId) {
		String deleteQuery = "delete from products where eventId = ?";
		getJdbcTemplate().update(deleteQuery, new Object[]{
				 eventId
		});
		return null;
	}
	public boolean deleteProductById(String productId) {
		String deleteQuery = "delete from products where productId = ?";
		getJdbcTemplate().update(deleteQuery, new Object[]{
				productId
		});
		return true;
	}

}
