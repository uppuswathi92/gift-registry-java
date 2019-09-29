package com.uppu.giftregistry.dao.impl;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
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
		if(!userDao.userExists(user.getUsername(), user.getEmail())) {
				//update the User table with the new user details
				int insertUser = jdbcTemplate.update("INSERT INTO registryuser VALUES(?,?,?,?,?,?)",user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
				if(insertUser >= 1) {
					message = "success";
				}
			}else {
				message = "user exists";
			}
		}
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
}
