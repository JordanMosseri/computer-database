package dao;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modele.Computer;
import modele.Company;

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
	
	
	public Computer getComputer(int idComputer){
		return getOneOrManyComputers("WHERE computer.id='"+idComputer+"'").get(0);
	}
	
	public List<Computer> getListComputers(){
		return getOneOrManyComputers("");
	}
	
	private List<Computer> getOneOrManyComputers(String where) {
		
		final String REQUETE_GET_ALL = "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id "+where;
		//final String REQUETE_GET_ALL = "SELECT * FROM computer";
		
		ArrayList<Computer> liste  = null;//new ArrayList<Computer>();
		mettreVariablesANull();
		
		try {
			
			
			cn = getConnexion();
			stmt = cn.createStatement();
			rs = stmt.executeQuery(REQUETE_GET_ALL);
			liste = Mapper.mapper(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
		return liste;
	}
	
	
	public void insereComputer(Computer comp) {
		
		mettreVariablesANull();
		int company_id=-1;
		
		try {
			
			cn = getConnexion();
			
			company_id = CompanyDAO.getInstance().recupCompanyIdIfExists(comp.company.name);
			
			if(company_id<0){
				company_id = CompanyDAO.getInstance().insererCompany(comp.company.name);
			}
			//System.out.println(company_id);if(true)return;
			
			
			pstmt = cn.prepareStatement("INSERT into computer(name,introduced,company_id) VALUES(?,?,?)");
			pstmt.setString(1,comp.name);
			pstmt.setTimestamp(2,new Timestamp(comp.dateAdded.getTime()));
			pstmt.setInt(3, company_id);
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryCloseVariables();
		}
		
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
			
			pstmt = cn.prepareStatement("DELETE FROM computer WHERE id=?");
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
	
	
	
}


