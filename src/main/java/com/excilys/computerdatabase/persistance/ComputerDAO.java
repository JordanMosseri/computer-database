package com.excilys.computerdatabase.persistance;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.mappers.DAOMapper;
import com.excilys.computerdatabase.modele.Computer;

@Repository
public class ComputerDAO implements IComputerDAO{
	
	
	final String QUERY_GET_ALL = "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id ";
	//final String REQUETE_GET_ALL = "SELECT * FROM computer";
	
	final String QUERY_INSERT = "INSERT into computer(name,introduced, discontinued,company_id) VALUES(?,?,?,?)";
	final String QUERY_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	final String QUERY_DELETE = "DELETE FROM computer WHERE id=?";
	final String QUERY_EXISTS = "SELECT * FROM computer WHERE id=?";
	final String QUERY_TOTAL = "SELECT count(*) FROM computer";
	final String QUERY_DELETE_WITH_COMPANYID = "DELETE FROM computer WHERE company_id=?";
	
	@Override
	public Computer get(int idComputer, Connection cn){
		
		Computer computer = null;
		mettreVariablesANull();
		
		try {
			stmt = cn.createStatement();
			rs = stmt.executeQuery(QUERY_GET_ALL+"WHERE computer.id='"+idComputer+"'");
			if(rs.next()){
				computer = DAOMapper.map(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return computer;
	}
	
	@Override
	public List<Computer> getAll(String endOfQuery, Connection cn){
		
		ArrayList<Computer> liste  = new ArrayList<Computer>();
		mettreVariablesANull();
		
		try {
			stmt = cn.createStatement();
			rs = stmt.executeQuery(QUERY_GET_ALL+endOfQuery);
			while(rs.next()){
				liste.add(DAOMapper.map(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return liste;
	}
	
	@Override
	public List<Computer> getPart(int offset, int limit, String word, Connection cn){
		String whereLike = " ";
		if(word != null && !word.isEmpty()){
			whereLike = " WHERE computer.name LIKE '%" + word + "%' || company.name LIKE '%" + word + "%'";
		}
		return getAll(whereLike + "LIMIT " + offset + ", "+limit, cn);
	}
	
	
	
	
	
	@Override
	public boolean insert(Computer computer, Connection cn) {
		
		mettreVariablesANull();
		
		boolean ok=false;
		
		try {
			pstmt = cn.prepareStatement(QUERY_INSERT);
			DAOMapper.map(pstmt, computer);
			pstmt.executeUpdate();
			
			ok=true;
		} catch (SQLException e) {
			e.printStackTrace();
			//throw new RuntimeException("exxxcc");
		} finally {
			tryCloseVariables();
		}
		
		return ok;
		
	}
	
	/*public boolean updateComputer(int id, String nouveauNom){
		mettreVariablesANull();
		
		boolean ok=true;
		
		try{
			cn = getConnexion();
			
			pstmt = cn.prepareStatement("UPDATE computer SET name=? WHERE id=?");
			pstmt.setString(1, nouveauNom);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			tryCloseVariables();
		}
		return ok;
	}
	
	public boolean updateComputer(int id, Date nouvelleDate){
		mettreVariablesANull();
		
		boolean ok=true;
		
		try{
			cn = getConnexion();
			
			pstmt = cn.prepareStatement("UPDATE computer SET introduced=? WHERE id=?");
			pstmt.setTimestamp(1, new Timestamp(nouvelleDate.getTime()));
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			tryCloseVariables();
		}
		return ok;
	}*/
	
	@Override
	public boolean update(Computer computer, Connection cn){
		mettreVariablesANull();
		
		boolean ok=true;
		
		try{
			pstmt = cn.prepareStatement(QUERY_UPDATE);
			DAOMapper.map(pstmt, computer);
			pstmt.setInt(5, computer.id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		} finally {
			tryCloseVariables();
		}
		return ok;
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
}


