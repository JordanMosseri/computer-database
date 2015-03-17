package com.excilys.computerdatabase.persistance;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.mappers.DAOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

import org.springframework.jdbc.core.support.*;

@Repository
public class ComputerDAO extends JdbcDaoSupport implements IComputerDAO {
	
	
	final String QUERY_GET_ALL = "SELECT *, computer.name AS computer_name, company.name AS company_name, company.id AS company_id"
			+ " FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id ";
	//final String REQUETE_GET_ALL = "SELECT * FROM computer";
	
	final String QUERY_INSERT = "INSERT into computer(name,introduced, discontinued,company_id) VALUES(?,?,?,?)";
	final String QUERY_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	final String QUERY_DELETE = "DELETE FROM computer WHERE id=?";
	final String QUERY_EXISTS = "SELECT * FROM computer WHERE id=?";
	final String QUERY_TOTAL = "SELECT count(*) FROM computer";
	final String QUERY_DELETE_WITH_COMPANYID = "DELETE FROM computer WHERE company_id=?";
	
	/*private DataSource dataSource;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/
	
	@Autowired 
	public ComputerDAO(DataSource dataSource) {
	    super();
	    setDataSource(dataSource);
	}
	
	@Override
	public Computer get(int idComputer, Connection cn){
		
		return (Computer) getJdbcTemplate().queryForObject(
			QUERY_GET_ALL+"WHERE computer.id=?", new Object[] { idComputer }, 
			//new BeanPropertyRowMapper(Computer.class)
			new CustomerRowMapper()
		);
	}
	
	@Override
	public List<Computer> getAll(String endOfQuery, Connection cn){
		
		return getJdbcTemplate().query(QUERY_GET_ALL+endOfQuery, new CustomerRowMapper());
	}
	
	@Override
	public List<Computer> getPart(int offset, int limit, String word, Connection cn){
		String whereLike = " ";
		if(word != null && !word.isEmpty()){
			whereLike = " WHERE computer.name LIKE '%" + word + "%' || company.name LIKE '%" + word + "%'";
		}
		return getAll(whereLike + "LIMIT " + offset + ", "+limit, cn);
	}
	
	
	
	public Timestamp getTimestamp(LocalDateTime date){
		if(date != null) {
			return Timestamp.valueOf(date.withHour(0).withMinute(0).withSecond(0));
		}
		else {
			return null;
		}
	}
	
	@Override
	public boolean insert(Computer computer, Connection cn) {
				
		int res = getJdbcTemplate().update(QUERY_INSERT, new Object[] { computer.name, getTimestamp(computer.dateAdded), getTimestamp(computer.dateRemoved), computer.company.id});
		
		if (res != 1){
			return false;
		}
		else {
			return true;
		}
	}
	
	
	
	@Override
	public boolean update(Computer computer, Connection cn){
		
		int res = getJdbcTemplate().update(QUERY_UPDATE, new Object[] { computer.name, getTimestamp(computer.dateAdded), getTimestamp(computer.dateRemoved), computer.company.id, computer.id});
		
		if (res != 1){
			return false;
		}
		else {
			return true;
		}
	}
	
	@Override
	public boolean delete(int id, Connection cn){
		mettreVariablesANull();
		
		boolean ok=true;
		
		try{
			pstmt = cn.prepareStatement(QUERY_DELETE);
			pstmt.setInt(1, id);
			int res = pstmt.executeUpdate();
			if(res == 0) {
				ok = false;
			}
			else if (res == 1) {
				ok = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		} finally {
			tryCloseVariables();
		}
		return ok;
	}
	
	
	@Override
	public boolean exists(int id, Connection cn){
		mettreVariablesANull();
		
		boolean ok=false;
		
		try{
			pstmt = cn.prepareStatement(QUERY_EXISTS);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ok=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		} finally {
			tryCloseVariables();
		}
		return ok;
	}
	
	@Override
	public int getTotalCount(Connection cn){
		mettreVariablesANull();
		
		int size = 0;
		
		try{
			pstmt = cn.prepareStatement(QUERY_TOTAL);
			rs = pstmt.executeQuery();
			if(rs.next()){
				size = rs.getInt("count(*)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		return size;
	}
	
	
	@Override
	public boolean deleteThoseFromCompany(int companyId, Connection cn){
		mettreVariablesANull();
		boolean ok = true;
		
		try{
			//cn = getConnexion();
			
			pstmt = cn.prepareStatement(QUERY_DELETE_WITH_COMPANYID);
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			ok = false;
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		return ok;
	}
	
	@Override
	public List<Computer> getThoseFromCompany(int companyId, Connection cn){
		mettreVariablesANull();
		List<Computer> computers = new ArrayList<Computer>();
		
		try{
			pstmt = cn.prepareStatement("SELECT * FROM computer WHERE company_id=?");
			pstmt.setInt(1, companyId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				computers.add(DAOMapper.map(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		return computers;
	}
	
	/*public List<Computer> search(String word){
		mettreVariablesANull();
		List<Computer> computers = new ArrayList<Computer>();
		
		try{
			cn = getConnexion();
			
			pstmt = cn.prepareStatement("SELECT * FROM computer WHERE computer.name LIKE '%?%'");
			pstmt.setString(1, word);
			rs = pstmt.executeQuery();
			while(rs.next()){
				computers.add(DAOMapper.map(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		return computers;
	}*/
	
	public ResultSet rs = null ;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public Connection cn = null;
	
	protected void mettreVariablesANull(){
		rs = null ;
		stmt = null;
		pstmt = null;
		cn = null;
	}
	
	protected void tryCloseVariables(){
		try {
			if (rs != null)
				rs.close();
			
			if (stmt != null)
				stmt.close();
			
			if (pstmt != null)
				pstmt.close();
			
			if (cn != null) 
				cn.close();
		} catch (SQLException e) {}
	}
	
	class CustomerRowMapper implements RowMapper<Computer> {
		
		public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Computer computer = new Computer();
			
			computer.id = (rs.getInt("id"));
			
			computer.name = (rs.getString("name"));
			
			if(rs.getTimestamp("introduced") != null)
				computer.dateAdded = (rs.getTimestamp("introduced").toLocalDateTime());
			
			if(rs.getTimestamp("discontinued")!=null)
				computer.dateRemoved = (rs.getTimestamp("discontinued").toLocalDateTime());
			
			computer.company = new Company(rs.getString("company.name"), rs.getInt("company.id"));
			
			return computer;
		}
		
		/*List<Map<String, Object>> rows = jdbcTemplate.queryForList(QUERY_GET_ALL+endOfQuery);
		for (Map<String, Object> row : rows) {
			
			int cid = NumberUtils.toInt("" + (Long) row.get("id"));
			
			String cname = (String) (row.get("computer_name"));
			
			LocalDateTime dadded = null;
			
			LocalDateTime dremoved = null;
			
			if (row.get("introduced") != null){
				dadded = (((Timestamp) row.get("introduced")).toLocalDateTime());
			}
			if (row.get("discontinued") != null){
				dremoved = (((Timestamp) row.get("discontinued")).toLocalDateTime());
			}
			
			String companyName = "";
			int companyId = -1;
			
			if (row.get("company_name") != null) {
				companyName = (String) row.get("company_name");
			}
			
			if (row.get("company_id") != null) {
				companyId = Integer.parseInt(String.valueOf(row.get("company_id")));
			}
			
			Computer computer = new Computer(cid, cname, dadded, dremoved, new Company(companyName, companyId));
			//System.out.println(computer.toString());
			
			liste.add(computer);
		}*/
	 
	}
	
}


