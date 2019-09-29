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
				product.setProductImage("R0lGODlh/QC+ANUAAO3t7bKysvf399HR0d3d3djY2PDw8M7OzsDAwLu7u9PT08XFxevr6+bm5vT09MrKyvj4+NDQ0Le3t+jo6NbW1vHx8fLy8uHh4fb29urq6t7e3tXV1e7u7vX19dTU1ODg4Nvb29LS0uPj4+np6eTk5Ozs7OXl5eLi4ufn59/f3+/v7/Pz89fX19zc3NnZ2c/Pz/n5+QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH/C1hNUCBEYXRhWE1QPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS4wLWMwNjAgNjEuMTM0Nzc3LCAyMDEwLzAyLzEyLTE3OjMyOjAwICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1wTU06T3JpZ2luYWxEb2N1bWVudElEPSJ4bXAuZGlkOjg3MUQ1QzZBOTQ1MUUzMTFCNzVGOTM1RTEyMDE2RjA5IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjA2MzlGQkQ2Q0Y3NzExRTM5Njc1QzkwNjFGMzk2MDQ5IiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjA2MzlGQkQ1Q0Y3NzExRTM5Njc1QzkwNjFGMzk2MDQ5IiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzUgV2luZG93cyI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4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");
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
			if(blob == null) {
				product.setProductImage("R0lGODlh/QC+ANUAAO3t7bKysvf399HR0d3d3djY2PDw8M7OzsDAwLu7u9PT08XFxevr6+bm5vT09MrKyvj4+NDQ0Le3t+jo6NbW1vHx8fLy8uHh4fb29urq6t7e3tXV1e7u7vX19dTU1ODg4Nvb29LS0uPj4+np6eTk5Ozs7OXl5eLi4ufn59/f3+/v7/Pz89fX19zc3NnZ2c/Pz/n5+QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH/C1hNUCBEYXRhWE1QPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS4wLWMwNjAgNjEuMTM0Nzc3LCAyMDEwLzAyLzEyLTE3OjMyOjAwICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1wTU06T3JpZ2luYWxEb2N1bWVudElEPSJ4bXAuZGlkOjg3MUQ1QzZBOTQ1MUUzMTFCNzVGOTM1RTEyMDE2RjA5IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjA2MzlGQkQ2Q0Y3NzExRTM5Njc1QzkwNjFGMzk2MDQ5IiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjA2MzlGQkQ1Q0Y3NzExRTM5Njc1QzkwNjFGMzk2MDQ5IiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzUgV2luZG93cyI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4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");
			}else {
				product.setProductImage(getProductImage(blob));
			}
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
