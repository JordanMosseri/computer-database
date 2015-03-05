package com.excilys.computerdatabase.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.mappers.DAOMapper;
import com.excilys.computerdatabase.modele.Computer;

public class ComputerDAO extends AbstractDAO{
	
	/**
	 * Constructeur prive
	 */
	private ComputerDAO() {
	}
	
	/**
	 * Instance unique pré-initialisée
	 */
	private static ComputerDAO INSTANCE = null;
	 
	/**
	 * Point d'accès pour l'instance unique du singleton
	 * @return
	 */
	public static ComputerDAO getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new ComputerDAO();	
		}
		return INSTANCE;
	}
	
	//////////////////////
	
	
	final String QUERY_GET_ALL = "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id ";
	//final String REQUETE_GET_ALL = "SELECT * FROM computer";
	
	final String QUERY_INSERT = "INSERT into computer(name,introduced, discontinued,company_id) VALUES(?,?,?,?)";
	final String QUERY_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	final String QUERY_DELETE = "DELETE FROM computer WHERE id=?";
	final String QUERY_DELETE_WITH_COMPANYID = "DELETE FROM computer WHERE company_id=?";
	
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
	
	public List<Computer> getPart(int offset, int limit, String word, Connection cn){
		String whereLike = " ";
		if(word != null && !word.isEmpty()){
			whereLike = " WHERE computer.name LIKE '%" + word + "%' || company.name LIKE '%" + word + "%'";
		}
		return getAll(whereLike + "LIMIT " + offset + ", "+limit, cn);
	}
	
	
	
	
	
	/**
	 * Insert a computer, all checking is already done and comp.company.id is good, comp.company.name doesn't matter here
	 * @param comp
	 * @return
	 */
	public boolean insert(Computer computer, Connection cn) {
		
		mettreVariablesANull();
		
		boolean ok=false;
		
		try {
			System.out.println(computer.company.id);//if(true)return;
			
			pstmt = cn.prepareStatement(QUERY_INSERT);
			DAOMapper.map(pstmt, computer);
			pstmt.executeUpdate();
			
			ok=true;
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public boolean update(Computer computer, Connection cn){
		mettreVariablesANull();
		
		boolean ok=true;
		
		try{
			pstmt = cn.prepareStatement(QUERY_UPDATE);
			DAOMapper.map(pstmt, computer);
			pstmt.setInt(5, computer.id);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			tryCloseVariables();
		}
		return ok;
	}
	
	/**
	 * Supprime un ordinateur de la bdd
	 * @param id
	 * @return true si ok, false si pas ok
	 */
	public boolean delete(int id, Connection cn){
		mettreVariablesANull();
		
		boolean ok=true;
		
		try{
			pstmt = cn.prepareStatement(QUERY_DELETE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			tryCloseVariables();
		}
		return ok;
	}
	
	
	public boolean exists(int id, Connection cn){
		mettreVariablesANull();
		
		boolean ok=false;
		
		try{
			pstmt = cn.prepareStatement("SELECT * FROM computer WHERE id=?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ok=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			tryCloseVariables();
		}
		return ok;
	}
	
	public int getTotalCount(Connection cn){
		mettreVariablesANull();
		
		int size = 0;
		
		try{
			pstmt = cn.prepareStatement("SELECT count(*) FROM computer");
			rs = pstmt.executeQuery();
			if(rs.next()){
				size = rs.getInt("count(*)");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		return size;
	}
	
	
	public boolean deleteThoseFromCompany(int companyId, Connection cn){
		mettreVariablesANull();
		boolean ok = true;
		
		try{
			//cn = getConnexion();
			
			pstmt = cn.prepareStatement(QUERY_DELETE_WITH_COMPANYID);
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		return ok;
	}
	
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
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		return computers;
	}
	
	public List<Computer> search(String word){
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
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		return computers;
	}
	
	
}


