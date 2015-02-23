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
	
	/**
	 * Constructeur prive
	 */
	private CompanyDAO() {
	}
	
	/**
	 * Instance unique pré-initialisée
	 */
	private static CompanyDAO INSTANCE = new CompanyDAO();
	 
	/**
	 * Point d'accès pour l'instance unique du singleton
	 * @return
	 */
	public static CompanyDAO getInstance(){
		return INSTANCE;
	}
	
	//////////////////////
	
	
	public List<Fabriquant> getListCompanies(){
		
		final String REQUETE_GET_ALL = "SELECT * FROM company";
		
		ArrayList<Fabriquant> liste  = new ArrayList<Fabriquant>();
		mettreVariablesANull();
		
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
			tryCloseVariables();
		}
		
		return liste;
	}
	
	/**
	 * Insere une company dans la bdd
	 * @param nomFab
	 * @return Id de la company nouvellement cree
	 */
	public int insererCompany(String nomFab){
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
	public int recupCompanyIdIfExists(String nomFab){
		mettreVariablesANull();
		
		int retour = -1;
		
		try{
			
			cn = getConnexion();
			
			pstmt = cn.prepareStatement("SELECT * FROM company WHERE name=?");
			pstmt.setString(1, nomFab);
			rs = pstmt.executeQuery();
			if(rs.next()){
				retour = rs.getInt("id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		//System.out.println("retour recupCompanyIdIfExists="+retour);
		return retour;
	}
}
