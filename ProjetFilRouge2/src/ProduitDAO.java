

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

import modele.Computer;
import modele.Fabriquant;

public class ProduitDAO {
	
	
	private Connection getConnexion() {
		
		Connection cn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull","root","root");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return cn;
	}
	
	/*
	 * List computers
List companies
Show computer details (the detailed information of only one computer)
Create a computer
Update a computer
Delete a computer
	 */
	
	public static int i_test=0;
	public static final String REQUETE_GET_ALL = "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id";
	//public static final String REQUETE_GET_ALL = "SELECT * FROM computer";
	
	public List<Computer> getListComputers() {
		
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
					i_test++;
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
				
				if (cn != null) cn.close();
			} catch (SQLException e) {}
		}
		
		return liste;
	}
	
	
	public void insereComputer(Computer comp) {
		
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		int i=0;
		int company_id=-1;
		
		try {
			
			cn = getConnexion();
			
			stmt = cn.prepareStatement("SELECT * FROM company WHERE name=?");
			stmt.setString(1, comp.fabriquant.nomFabricant);
			rs = stmt.executeQuery();
			while(rs.next()){
				company_id = rs.getInt("id");
				i++;
			}
			rs.close();
			stmt.close();
			
			if(i<=0){
				stmt = cn.prepareStatement("INSERT into company(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, comp.fabriquant.nomFabricant);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()){
					company_id=rs.getInt(1);
				}
				stmt.close();
			}
			
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
				
				if (cn != null) cn.close();
			} catch (SQLException e) {}
		}
		
	}
}
