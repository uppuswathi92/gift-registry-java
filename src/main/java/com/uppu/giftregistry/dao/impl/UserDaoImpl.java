package com.uppu.giftregistry.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.uppu.giftregistry.dao.UserDao;
import com.uppu.giftregistry.model.RegistryUser;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
	
	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	public List<RegistryUser> getAllUsers() {
		String sql = "SELECT * FROM registryUser";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		
		List<RegistryUser> result = new ArrayList<RegistryUser>();
		for(Map<String, Object> row:rows){
			RegistryUser user = new RegistryUser();
			user.setUsername((String)row.get("username"));
			result.add(user);
		}
		
		return result;
	}


	public boolean userExists(String username, String emailId) {
		boolean exists = false;
		String sql = "SELECT * FROM registryuser WHERE username = '" + username + "' OR email = '" + emailId + "'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		if(rows.size() > 0) {
			exists = true;
		}
		/*return result;
		return (RegistryUser)getJdbcTemplate().queryForObject(sql, new Object[]{username}, new RowMapper<RegistryUser>(){
			public RegistryUser mapRow(ResultSet rs, int rwNumber) throws SQLException {
				RegistryUser user = new RegistryUser();
				user.setUsername(rs.getString("username"));
				return user;
			}
		});*/
		return exists;
	}


	public RegistryUser getUserByEmail(String email) {
		String sql = "SELECT * FROM user WHERE username = ?";
		return (RegistryUser)getJdbcTemplate().queryForObject(sql, new Object[]{email}, new RowMapper<RegistryUser>(){
			public RegistryUser mapRow(ResultSet rs, int rwNumber) throws SQLException {
				RegistryUser user = new RegistryUser();
				user.setUsername(rs.getString("username"));
				return user;
			}
		});
	}






	public RegistryUser getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RegistryUser> getUsers() {
		String sql = "SELECT * FROM registryUser";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		
		List<RegistryUser> result = new ArrayList<RegistryUser>();
		for(Map<String, Object> row:rows){
			RegistryUser user = new RegistryUser();
			user.setUsername((String)row.get("username"));
			user.setEmail((String)row.get("email"));
			user.setFirstName((String)row.get("firstName"));
			user.setLastName((String)row.get("lastName"));
			result.add(user);
		}
		
		return result;
	}

}

