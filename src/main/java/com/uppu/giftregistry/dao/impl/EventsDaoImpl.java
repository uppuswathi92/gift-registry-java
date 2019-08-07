package com.uppu.giftregistry.dao.impl;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.uppu.giftregistry.dao.EventsDao;
import com.uppu.giftregistry.model.Events;

@Repository
public class EventsDaoImpl extends JdbcDaoSupport implements EventsDao {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}
	public String addEvent(Events events, String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
