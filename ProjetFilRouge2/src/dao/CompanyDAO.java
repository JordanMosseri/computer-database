package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Computer;
import modele.Fabriquant;

public class CompanyDAO extends DAOAbstrait {
	
	public List<Fabriquant> getListCompanies(){
		
		final String REQUETE_GET_ALL = "SELECT * FROM company";
		
		ArrayList<Fabriquant> liste  = new ArrayList<Fabriquant>();
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = null;
		
		try {
			
			
			cn = getConnexion();
			
			stmt = cn.createStatement();
			rs = stmt.executeQuery(REQUETE_GET_ALL);
			while (rs.next()) {
				Fabriquant fab = new Fabriquant(rs.getString("name"));
				liste.add(fab);
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
	
	
	public int insererCompany(String nomFab){
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		
		int retour = -1;
		
		try{
			
			cn = getConnexion();
			
			stmt = cn.prepareStatement("INSERT into company(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nomFab);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()){
				retour=rs.getInt(1);
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
		
		return retour;
	}
	
	public int recupCompanyIdIfExists(String nomFab){
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		
		int retour = -1;
		
		try{
			
			cn = getConnexion();
			
			stmt = cn.prepareStatement("SELECT * FROM company WHERE name=?");
			stmt.setString(1, nomFab);
			rs = stmt.executeQuery();
			if(rs.next()){
				retour = rs.getInt("id");
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
		
		return retour;
	}
}
