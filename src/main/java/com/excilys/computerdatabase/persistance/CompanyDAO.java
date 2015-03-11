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

import com.excilys.computerdatabase.modele.Company;

@Repository
public class CompanyDAO implements ICompanyDAO {
	
	
	final String REQUETE_GET_ALL = "SELECT * FROM company";
	final String QUERY_INSERT = "INSERT into company(name) VALUES(?)";
	final String QUERY_EXISTS = "SELECT * FROM company WHERE id=?";
	final String QUERY_DELETE = "DELETE FROM company WHERE id=?";
	
	@Override
	public List<Company> getAll(Connection cn){
		
		ArrayList<Company> liste  = new ArrayList<Company>();
		mettreVariablesANull();
		
		try {
			stmt = cn.createStatement();
			rs = stmt.executeQuery(REQUETE_GET_ALL);
			while (rs.next()) {
				Company fab = new Company(rs.getString("name"), rs.getInt("id"));
				liste.add(fab);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return liste;
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
	public int insert(String nomFab, Connection cn){
		mettreVariablesANull();
		
		int retour = -1;
		
		try{
			pstmt = cn.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, nomFab);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			while (rs.next()){
				retour=rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		//System.out.println("retour insererCompany="+retour);
		return retour;
	}
	
	@Override
	public int getIdIfNameExists(String nomFab, Connection cn){
		mettreVariablesANull();
		
		int retour = -1;
		
		try{
			pstmt = cn.prepareStatement("SELECT * FROM company WHERE name=?");
			pstmt.setString(1, nomFab);
			rs = pstmt.executeQuery();
			while(rs.next()){
				retour = rs.getInt("company.id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		//System.out.println("retour recupCompanyIdIfExists="+retour);
		return retour;
	}
	
	@Override
	public boolean exists(int id, Connection cn){
		mettreVariablesANull();
		
		boolean retour = false;
		
		try{
			pstmt = cn.prepareStatement(QUERY_EXISTS);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				retour = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return retour;
	}
	
	@Override
	public boolean delete(int id, Connection cn){
		mettreVariablesANull();
		
		boolean retour = true;
		
		try{
			pstmt = cn.prepareStatement(QUERY_DELETE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			retour = false;
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return retour;
	}
	
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
