package com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.mappers.CompanyRowMapper;
import com.excilys.computerdatabase.mappers.DAOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

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
	public List<Company> getAll(){
		
		return getJdbcTemplate().query(QUERY_GET_ALL, new CompanyRowMapper());
		
		/*while (rs.next()) {
			Company fab = new Company(rs.getString("name"), rs.getInt("id"));
			liste.add(fab);
		}*/
	}
	
	//NOT USED
	/*public Company get(int id){
		mettreVariablesANull();
		Company company = null;
		
		try {
			cn = getConnexion();
			stmt = cn.createStatement();
			rs = stmt.executeQuery(REQUETE_GET_ALL+"WHERE company.id='"+id+"'");
			if(rs.next()){
				company = DAOMapper.map(rs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return company;
	}*/
	
	@Override
	public int insert(String nomFab){
		
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
