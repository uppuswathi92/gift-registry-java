package com.uppu.giftregistry.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.uppu.giftregistry.dao.AddUserDao;
import com.uppu.giftregistry.dao.UserDao;
import com.uppu.giftregistry.model.RegistryUser;

@Repository
public class AddUserDaoImpl extends JdbcDaoSupport implements AddUserDao{
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserDao userDao;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}
	public String addUser(RegistryUser user) {
		String message = "";
		if(validateUserDetails(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber())) {
		//if(true) {	
		if(!userDao.userExists(user.getUsername(), user.getEmail())) {
			//if(true) {
				//get number of users, in order to insert the correct userID
				String getUserCountSql = "select * from registryuser";
				System.out.println(user.getFirstName());
				List<Map<String,Object>> number_of_users= jdbcTemplate.queryForList(getUserCountSql);
				
				//userId will be 1 more than the count of users in the system
				int userId = number_of_users.size()+1;
				//update the User table with the new user details
				int insertUser = jdbcTemplate.update("INSERT INTO registryuser VALUES(?,?,?,?,?,?)",user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
				if(insertUser >= 1) {
					message = "success";
				}
			}else {
				message = "user exists";
			}
		}
		
		/*if(userExits(email)) {
			if(validateOtherFields(password, firstname, lastname, phone, address) == true) {
				//get number of users, in order to insert the correct userID
				String getUserCountSql = "select * from User";
				List<Map<String,Object>> number_of_users= jdbcTemplate.queryForList(getUserCountSql);
				
				//userId will be 1 more than the count of users in the system
				int userId = number_of_users.size()+1;
				//update the User table with the new user details
				int insertUser = jdbcTemplate.update("INSERT INTO USER VALUES(?,?,?,?,?,?,?)",userId,emailId,password,firstname,lastname,phone,address);
				if(insertUser >= 1) {
					return true;
				}
		}			
		}*/
		return message;
	}
	private boolean validateUserDetails(String username, String password, String firstname, String lastname, String email, String phone) {
		if ((username == null) || (username.isEmpty()) || (password == null) || (password.isEmpty()) || (firstname == null) || (firstname.isEmpty())
				|| (lastname == null) || (lastname.isEmpty()) || (email == null) || (email.isEmpty())
				|| (phone == null) || (phone.isEmpty())) {
			return false;
		}
		return true;
	}
	/*private boolean userExists(String username, String email) {
		RegistryUser user = new RegistryUser();
			try {
			 user = userDao.getUserByEmail(emailId);
			}
			catch(EmptyResultDataAccessException e) {//do nothing
				}
			
			if(user.getUsername().equals(emailId)) {
				return true;
			}
		}
		}
		catch(Exception e) {
		return false;
	}*/
}
