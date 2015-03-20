package com.excilys.computerdatabase.persistance;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import atej_unused.DAOMapper;

import com.excilys.computerdatabase.mappers.ComputerRowMapper;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.util.Utils;

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
	public Computer get(int idComputer){
		
		return (Computer) getJdbcTemplate().queryForObject(
			QUERY_GET_ALL+"WHERE computer.id=?", new Object[] { idComputer }, 
			//new BeanPropertyRowMapper(Computer.class)
			new ComputerRowMapper()
		);
	}
	
	@Override
	public List<Computer> getAll(String endOfQuery){
		
		return getJdbcTemplate().query(QUERY_GET_ALL+endOfQuery, new ComputerRowMapper());
	}
	
	@Override
	public List<Computer> getPart(int offset, int limit, String word){
		String whereLike = " ";
		if(word != null && !word.isEmpty()){
			whereLike = " WHERE computer.name LIKE '%" + word + "%' || company.name LIKE '%" + word + "%'";
		}
		return getAll(whereLike + "LIMIT " + offset + ", "+limit);
	}
	
	
	
	@Override
	public boolean insert(Computer computer) {
		
		Integer computerId = computer.getCompany().getId() > -1 ? computer.getCompany().getId() : null;
		
		int res = getJdbcTemplate().update(QUERY_INSERT, new Object[] { computer.getName(), Utils.getTimestamp(computer.getDateAdded()), Utils.getTimestamp(computer.getDateRemoved()), computerId});
		
		if (res != 1){
			return false;
		}
		else {
			return true;
		}
	}
	
	
	
	@Override
	public boolean update(Computer computer){
		
		int res = getJdbcTemplate().update(QUERY_UPDATE, new Object[] { computer.getName(), Utils.getTimestamp(computer.getDateAdded()), Utils.getTimestamp(computer.getDateRemoved()), computer.getCompany().getId(), computer.getId()});
		
		if (res != 1){
			return false;
		}
		else {
			return true;
		}
	}
	
	@Override
	public boolean delete(int id){
		
		int res = getJdbcTemplate().update(QUERY_DELETE, new Object[] { id });
		
		if(res == 0) {
			return false;
		}
		else if (res == 1) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public boolean exists(int id){
		
		List<Computer> c = getJdbcTemplate().query(QUERY_EXISTS, new Object[] {id}, new ComputerRowMapper());
		
		return c.size() >= 1;
	}
	
	@Override
	public int getTotalCount(){
		
		return getJdbcTemplate().queryForObject(QUERY_TOTAL, Integer.class);
	}
	
	
	@Override
	public boolean deleteThoseFromCompany(int companyId){
		
		getJdbcTemplate().update(QUERY_DELETE_WITH_COMPANYID, new Object[] { companyId });
		
		//TODO suppr type retour
		return true;
	}
	
	@Override
	public List<Computer> getThoseFromCompany(int companyId){
		
		return getJdbcTemplate().query("SELECT * FROM computer WHERE company_id=?", new Object[] {companyId}, new ComputerRowMapper());
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
	
	
	
	
}


