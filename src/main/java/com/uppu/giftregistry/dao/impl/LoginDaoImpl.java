package com.uppu.giftregistry.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.uppu.giftregistry.dao.LoginDao;
import com.uppu.giftregistry.model.RegistryUser;

@Repository
public class LoginDaoImpl extends JdbcDaoSupport implements LoginDao{
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}
	public boolean login(String username, String password) {
		System.out.println(username);
		String loginSql = "select * from registryuser where username='"+username + "' and password= '" + password+"'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(loginSql);
		if(rows.size() == 1) {
			return true;
		}
		return false;
	}
}
