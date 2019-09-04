package com.uppu.giftregistry.dao.impl;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.uppu.giftregistry.dao.FileTestDao;
import com.uppu.giftregistry.model.Product;
@Repository
public class FileUploadDaoImpl extends JdbcDaoSupport implements FileTestDao{
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}
	public String fileUpload(byte[] bytes) {
		// try (Connection conn = DriverManager.getConnection(jdbcUrl, "", "");) {
	         File image = new File("C:\\Users\\Swathi Uppu\\Desktop\\Sem2\\Java\\Project\\SellerManagement\\seller_management\\src\\main\\resources\\static\\images\\bank.jpg");
	         //FileInputStream inputStream = new FileInputStream(image);
			 InputStream inputStream = new ByteArrayInputStream(bytes); 
					// FileInputStream(image);
			 String sql = "INSERT INTO files_upload(upload_id,file_name, file_data) VALUES(?,?,?)";
			 getJdbcTemplate().update(sql, new Object[]{
					"1234", "sample", inputStream
			});
	        
	         return null;
	}
	  // }
	public String getImage() {
		String base64Image = "";
		String imageQuery = "SELECT * FROM files_upload WHERE upload_id =1234";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(imageQuery);
		System.out.println(rows.size());
		for(Map<String, Object> row:rows){
			byte[] blob  = (byte[])row.get("file_data");
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
			
            
            
		}
		//System.out.println(base64Image);
		return base64Image;
	}

}
