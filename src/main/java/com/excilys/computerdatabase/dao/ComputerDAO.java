package com.excilys.computerdatabase.dao;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.mappers.DAOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.ui.cli.View;
import com.excilys.computerdatabase.util.Logging;

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
	
	
	final String REQUETE_GET_ALL = "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id ";
	//final String REQUETE_GET_ALL = "SELECT * FROM computer";
	
	final String QUERY_INSERT = "INSERT into computer(name,introduced, discontinued,company_id) VALUES(?,?,?,?)";
	final String QUERY_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	final String QUERY_DELETE = "DELETE FROM computer WHERE id=?";
	
	public Computer getComputer(int idComputer){
		return getOneOrManyComputers("WHERE computer.id='"+idComputer+"'").get(0);
	}
	
	public List<Computer> getAll(){
		return getOneOrManyComputers("");
	}
	
	public Paging<Computer> getAll(int offset, int limit){
		if(offset < 0){
			throw new IllegalStateException("ComputerDAO getAll : Negative offset.");
		}
		return new Paging<Computer>(
				offset, 
				getOneOrManyComputers("LIMIT " + offset + ", "+limit), 
				(offset+1)/limit,
				getSize()
				);
	}
	
	private List<Computer> getOneOrManyComputers(String where) {
		
		ArrayList<Computer> liste  = new ArrayList<Computer>();
		mettreVariablesANull();
		
		try {
			
			cn = getConnexion();
			stmt = cn.createStatement();
			rs = stmt.executeQuery(REQUETE_GET_ALL+where);
			while(rs.next()){
				liste.add(DAOMapper.mapper(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return liste;
	}
	
	
	/**
	 * Insert a computer, all checking is already done and comp.company.id is good, comp.company.name doesn't matter here
	 * @param comp
	 * @return
	 */
	public boolean insertComputer(Computer computer) {
		
		mettreVariablesANull();
		
		boolean ok=false;
		
		try {
			
			cn = getConnexion();
			
			System.out.println(computer.company.id);//if(true)return;
			
			pstmt = cn.prepareStatement(QUERY_INSERT);
			DAOMapper.f(pstmt, computer);
			pstmt.executeUpdate();
			
			ok=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return ok;
		
	}
	
	/**
	 * Modifie le nom d'un ordinateur
	 * @param id
	 * @param nouveauNom
	 * @return true si ok, false si pas ok
	 */
	public boolean updateComputer(int id, String nouveauNom){
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
	
	/**
	 * Modifie la date d'ajout d'un ordinateur
	 * @param id
	 * @param nouvelleDate
	 * @return true si ok, false si pas ok
	 */
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
	}
	
	public boolean updateComputer(Computer computer){
		mettreVariablesANull();
		
		boolean ok=true;
		
		try{
			cn = getConnexion();
			
			pstmt = cn.prepareStatement(QUERY_UPDATE);
			DAOMapper.f(pstmt, computer);
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
	public boolean deleteComputer(int id){
		mettreVariablesANull();
		
		boolean ok=true;
		
		try{
			cn = getConnexion();
			
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
	
	
	public boolean computerExists(int id){
		mettreVariablesANull();
		
		boolean ok=false;
		
		try{
			cn = getConnexion();
			
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
	
	public int getSize(){
		mettreVariablesANull();
		
		int size = 0;
		
		try{
			cn = getConnexion();
			
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
	
	
	
}


