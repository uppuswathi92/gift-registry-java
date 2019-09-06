package com.uppu.giftregistry.dao.impl;

import java.io.*;
import java.nio.file.Files;
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
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
					"1235", "sample", inputStream
			});
	        
	         return null;
	}
	  // }
	public String getImage() {
		String base64Image = "";
		String imageQuery = "SELECT * FROM files_upload WHERE upload_id =1235";
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
	@Autowired
    private JavaMailSender mailSender;
	@Autowired 
	 
	public String sendEmail() {
		/*System.out.println("sending email");
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("uppuswathi92@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
		 String result =null;
		    MimeMessage message =mailSender.createMimeMessage();
		    try {

		        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
		        /*String htmlMsg = "<body style='border:2px solid black'>"
		                    +"Your onetime password for registration is  " 
		                        + "Please use this OTP to complete your new user registration."+
		                          "OTP is confidential, do not share this  with anyone.</body>";
		        
		        
				
		        //ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		        
		       // File file = new File(classLoader.getResource(fileName).getFile());
		        String header = "";
		        String footer = "";
		        String htmlMsg = "";
		        String content = "<div align=\"center\" style=\"line-height: 24px;\">\r\n" + 
		        		"                                                        <a href=\"#\" target=\"_blank\" class=\"btn btn-danger block-center\">\r\n" + 
		        		"                                                            click\r\n" + 
		        		"                                                        </a>\r\n" + 
		        		"                                                    </div>\r\n" + 
		        		"                                                    <div style=\"height: 60px; line-height: 60px; font-size: 10px;\"></div>";
				try {
					File file = new ClassPathResource("templates/email/header-template.html").getFile();
					header = new String(Files.readAllBytes(file.toPath()));
					file = new ClassPathResource("templates/email/footer-template.html").getFile();
					footer = new String(Files.readAllBytes(file.toPath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				htmlMsg = header + content + footer; 
				//System.out.println(htmlMsg);
		        message.setContent(htmlMsg, "text/html");
		        //message.setContent("email/template1", "text/html");
		        helper.setTo("uppuswathi92@gmail.com");
		        helper.setSubject("subject");
		        result="success";
		       mailSender.send(message);
		    } catch (MessagingException e) {
		        throw new MailParseException(e);
		    }finally {
		        if(result !="success"){
		            result="fail";
		        }
		    }*/

        return null;
		
	}
	
	

}
