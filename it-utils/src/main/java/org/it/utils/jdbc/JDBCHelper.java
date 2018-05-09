package org.it.utils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JDBCHelper {
	private static Logger log = Logger.getLogger(JDBCHelper.class.getName());
	public static final String UPPERCASE = "UPPERCASE";
	public static final String LOWERCASE = "LOWERCASE";

	private JdbcTemplate jdbcTemplate;

	public JDBCHelper(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Map<String, Object>> getListMap(String sql, Object[] param) {
		long startTime = System.currentTimeMillis();

		List<Map<String, Object>> queryMap = null;

		try {

			queryMap = jdbcTemplate.query(sql, param, new RowMapper<Map<String, Object>>() {
				//@Override
				public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					HashMap<String, Object> resMap = new HashMap<String, Object>();
					int columnCount = rs.getMetaData().getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						String columnName = rs.getMetaData().getColumnName(i).toLowerCase();
						Object value = rs.getString(i);
						resMap.put(columnName, value);
					}
					return resMap;
				}
			});
		} catch (Exception e) {
			log.info("getListMap error:"+ExceptionUtil.getPrintStackTraceAsString(e));
		} finally {
			log.info(JDBCHelper.class.getName() + ".getListMap time is:" + (System.currentTimeMillis() - startTime)+" "+sql);
		}
		return queryMap;
	}
	
	public List<Map<String, Object>> getListMap(String sql, final String registr, Object[] param) {
		long startTime = System.currentTimeMillis();

		List<Map<String, Object>> queryMap = null;

		try {

			queryMap = jdbcTemplate.query(sql, param, new RowMapper<Map<String, Object>>() {
				//@Override
				public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					HashMap<String, Object> resMap = new HashMap<String, Object>();
					int columnCount = rs.getMetaData().getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						String columnName = null;
						if (LOWERCASE.equals(registr)){
							columnName = rs.getMetaData().getColumnName(i).toLowerCase();
						}else if (UPPERCASE.equals(registr)){
							columnName = rs.getMetaData().getColumnName(i).toUpperCase();
						}else{
							columnName = rs.getMetaData().getColumnName(i).toLowerCase();
						}
						Object value = rs.getString(i);
						resMap.put(columnName, value);
					}
					return resMap;
				}
			});
		} catch (Exception e) {
			log.info("getListMap error:"+ExceptionUtil.getPrintStackTraceAsString(e));
		} finally {
			log.info(JDBCHelper.class.getName() + ".getListMap time is:" + (System.currentTimeMillis() - startTime)+" "+sql);
		}
		return queryMap;
	}
	
	public Map<String, Object> getMap(String sql, Object[] param) {
		long startTime = System.currentTimeMillis();

		Map<String, Object> resMap = new HashMap<String, Object>();
		List<Map<String, Object>> queryListMap = null;

		try {

			queryListMap = jdbcTemplate.query(sql, param, new RowMapper<Map<String, Object>>() {
				//@Override
				public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					HashMap<String, Object> resMap = new HashMap<String, Object>();
					int columnCount = rs.getMetaData().getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						String columnName = rs.getMetaData().getColumnName(i).toLowerCase();
						Object value = rs.getString(i);
						resMap.put(columnName, value);
					}
					return resMap;
				}
			});
			if (!queryListMap.isEmpty()){
				resMap = queryListMap.get(0);
			}
			
		} catch (Exception e) {
			log.info("getListMap error:"+ExceptionUtil.getPrintStackTraceAsString(e));
		} finally {
			log.info(JDBCHelper.class.getName() + ".getListMap time is:" + (System.currentTimeMillis() - startTime)+" "+sql);
		}
		return resMap;
	}
	
	public int update(String sql, Object[] param) {
		long startTime = System.currentTimeMillis();
		int n = -1;
		try {
			n = jdbcTemplate.update(sql, param);
			if (n==0){
				log.info("Warning update result is 0:"+sql);
			}
		} catch (Exception e) {
			log.info(ExceptionUtil.getPrintStackTraceAsString(e));
		} finally {
			log.info(JDBCHelper.class.getName() + ".update time is:" + (System.currentTimeMillis() - startTime)+" "+sql);
		}
		return n;
	}
}
