package com.excilys.computerdatabase.persistance.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.mappers.CompanyRowMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.persistance.ICompanyDAO;

@Repository
public class CompanyDAO extends JdbcDaoSupport implements ICompanyDAO {
	
	
	final String QUERY_GET_ALL = "SELECT * FROM company";
	final String QUERY_INSERT = "INSERT into company(name) VALUES(?)";
	final String QUERY_EXISTS = "SELECT * FROM company WHERE id=?";
	final String QUERY_DELETE = "DELETE FROM company WHERE id=?";
	
	@Autowired 
	public CompanyDAO(DataSource dataSource) {
	    super();
	    setDataSource(dataSource);
	}
	
	
	
	@Override
	public List<Company> findAll(){
		
		return getJdbcTemplate().query(QUERY_GET_ALL, new CompanyRowMapper());
	}
	
	@Override
	public int save(String nomFab){
		
		getJdbcTemplate().update(QUERY_INSERT, new Object[] {nomFab});
		
		return getJdbcTemplate().queryForObject( "SELECT LAST_INSERT_ID()", Integer.class);
	}
	
	@Override
	public int getIdIfNameExists(String nomFab){
		
		Integer res = getJdbcTemplate().queryForObject("SELECT company.id FROM company WHERE name=?", new Object[] {nomFab}, Integer.class);
		
		if (res != null) {
			return res;
		}
		else {
			return -1;
		}
	}
	
	@Override
	public boolean exists(int id){
		
		List<Company> c = getJdbcTemplate().query(QUERY_EXISTS, new Object[] {id}, new CompanyRowMapper());
		
		return c.size() >= 1;
	}
	
	@Override
	public boolean delete(int id){
		
		int res = getJdbcTemplate().update(QUERY_DELETE, new Object[] { id });
		
		if (res == 1) {
			return true;
		}
		return false;
	}
	
	
}