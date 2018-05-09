package org.it.jdbc.context;

import java.util.logging.Logger;

import org.springframework.jdbc.core.JdbcTemplate;

public class InitDataSource {
	private static Logger log = Logger.getLogger(InitDataSource.class.getName());
	
	JdbcTemplate jdbcTemplate;
	
	public InitDataSource(JdbcTemplate jdbcTemplate) {
		try{
			jdbcTemplate.execute("SELECT current_date");
			log.info("-------------------------------");
			log.info("JDBC init success");
			log.info("-------------------------------");
		}catch(Exception e){
			log.info("-------------------------------");
			log.info("Warning: init dataSource error: "+e.getMessage());
			log.info("-------------------------------");
		}
	}

}
