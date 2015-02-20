package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.DAOAbstrait;
import modele.Computer;
import modele.Fabriquant;

public class ComputerDAO extends DAOAbstrait{
	
	public CompanyDAO companyDAO = new CompanyDAO();
	
	
	public Computer getComputer(int idComputer){
		return getOneOrManyComputers("WHERE computer.id='"+idComputer+"'").get(0);
	}
	public List<Computer> getListComputers(){
		return getOneOrManyComputers("");
	}
	
	private List<Computer> getOneOrManyComputers(String where) {
		
		final String REQUETE_GET_ALL = "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id "+where;
		//final String REQUETE_GET_ALL = "SELECT * FROM computer";
		
		ArrayList<Computer> liste  = new ArrayList<Computer>();
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = null;
		
		try {
			
			
			cn = getConnexion();
			stmt = cn.createStatement();
			rs = stmt.executeQuery(REQUETE_GET_ALL);
			while (rs.next()) {
				//System.out.println(rs.getTimestamp("introduced"));if(true)return null;
				long introduced=0;
				if(rs.getTimestamp("introduced") != null){
					introduced=rs.getTimestamp("introduced").getTime();
				}
				Computer p =  new Computer(rs.getString("name"), new Date(introduced), new Fabriquant(rs.getString("company.name")));//company.name company_id
				liste.add(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				
				if (stmt != null)
					stmt.close();
				
				if (cn != null) 
					cn.close();
			} catch (SQLException e) {}
		}
		
		return liste;
	}
	
	
	public void insereComputer(Computer comp) {
		
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		int company_id=-1;
		
		try {
			
			cn = getConnexion();
			
			company_id = companyDAO.recupCompanyIdIfExists(comp.fabriquant.nomFabricant);
			
			if(company_id<0){
				companyDAO.insererCompany(comp.fabriquant.nomFabricant);
			}
			//System.out.println(company_id);
			
			stmt = cn.prepareStatement("INSERT into computer(name,introduced,company_id) VALUES(?,?,?)");
			stmt.setString(1,comp.nom);
			stmt.setTimestamp(2,new Timestamp(comp.dateAjout.getTime()));
			stmt.setInt(3, company_id);
			stmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				
				if (stmt != null)
					stmt.close();
				
				if (cn != null) 
					cn.close();
			} catch (SQLException e) {}
		}
		
	}
	
	
	public boolean updateComputer(int id, String nouveauNom){
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		
		boolean ok=true;
		
		try{
			cn = getConnexion();
			
			stmt = cn.prepareStatement("UPDATE computer SET name=? WHERE id=?");
			stmt.setString(1, nouveauNom);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				
				if (stmt != null)
					stmt.close();
				
				if (cn != null) 
					cn.close();
			} catch (SQLException e) {}
		}
		return ok;
	}
	
	public boolean updateComputer(int id, Date nouvelleDate){
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		
		boolean ok=true;
		
		try{
			cn = getConnexion();
			
			stmt = cn.prepareStatement("UPDATE computer SET introduced=? WHERE id=?");
			stmt.setTimestamp(1, new Timestamp(nouvelleDate.getTime()));
			stmt.setInt(2, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				
				if (stmt != null)
					stmt.close();
				
				if (cn != null) 
					cn.close();
			} catch (SQLException e) {}
		}
		return ok;
	}
	
	
	public boolean deleteComputer(int id){
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		
		boolean ok=true;
		
		try{
			cn = getConnexion();
			
			stmt = cn.prepareStatement("DELETE FROM computer WHERE id=?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				
				if (stmt != null)
					stmt.close();
				
				if (cn != null) 
					cn.close();
			} catch (SQLException e) {}
		}
		return ok;
	}
	
	
	
}


