package com.uppu.giftregistry.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
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
		return productId;
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
			byte[] blob  = (byte[])row.get("productImage");
			System.out.println(blob);
			if(blob == null) {
				product.setProductImage("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");
			}else {
				product.setProductImage(getProductImage(blob));
			}
			
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
			byte[] blob  = (byte[])row.get("productImage");
			product.setProductImage(getProductImage(blob));
		}
		return product;
	}
	public String getProductImage(byte[] blob) {
		String base64Image = "";
			InputStream inputStream;
			//inputStream = blob.getBinaryStream();
			inputStream = new ByteArrayInputStream(blob);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			try {
				while ((bytesRead = inputStream.read(buffer)) != -1) {
				    outputStream.write(buffer, 0, bytesRead);                  
				}
				byte[] imageBytes = outputStream.toByteArray();
			    base64Image = Base64.getEncoder().encodeToString(imageBytes);
			    inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//System.out.println(base64Image);
		return base64Image;
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
	public String uploadImage(String productId, byte[] fileBytes) {
		InputStream inputStream = new ByteArrayInputStream(fileBytes); 
		String updateSql = "UPDATE products set productImage = ? where productId = ?";
		getJdbcTemplate().update(updateSql, new Object[]{
				inputStream, productId
		});

 return null;
	}

}
