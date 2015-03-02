package com.excilys.computerdatabase.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.modele.Company;

public class CompanyDAO extends AbstractDAO {
	
	/**
	 * Constructeur prive
	 */
	private CompanyDAO() {
	}
	
	/**
	 * Instance unique pré-initialisée
	 */
	private static CompanyDAO INSTANCE = null;
	 
	/**
	 * Point d'accès pour l'instance unique du singleton
	 * @return
	 */
	public static CompanyDAO getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new CompanyDAO();	
		}
		return INSTANCE;
	}
	
	//////////////////////
	
	
	public List<Company> getListCompanies(){
		
		final String REQUETE_GET_ALL = "SELECT * FROM company";
		
		ArrayList<Company> liste  = new ArrayList<Company>();
		mettreVariablesANull();
		
		try {
			
			
			cn = getConnexion();
			
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
	
	/**
	 * Insere une company dans la bdd
	 * @param nomFab
	 * @return Id de la company nouvellement cree
	 */
	public int insertCompany(String nomFab){
		mettreVariablesANull();
		
		int retour = -1;
		
		try{
			
			cn = getConnexion();
			
			pstmt = cn.prepareStatement("INSERT into company(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
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
	
	/**
	 * Verifie si une company existe.
	 * @param nomFab nom de la company/fabriquant dont l'existance est a verifier
	 * @return Id de la company dans la bdd si celle-ci existe, -1 si celle-ci n'existe pas
	 */
	public int getCompanyIdIfNameExists(String nomFab){
		mettreVariablesANull();
		
		int retour = -1;
		
		try{
			
			cn = getConnexion();
			
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
	
	public boolean companyExists(int id){
		mettreVariablesANull();
		
		boolean retour = false;
		
		try{
			
			cn = getConnexion();
			
			pstmt = cn.prepareStatement("SELECT * FROM company WHERE id=?");
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
}
