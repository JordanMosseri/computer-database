package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modele.Company;
import modele.Computer;

public class Mapper {
	
	
	public static ArrayList<Computer> mapper(ResultSet rs) throws SQLException {
		
		ArrayList<Computer> liste  = new ArrayList<Computer>();
		
		while (rs.next()) {
			//System.out.println(rs.getTimestamp("introduced"));if(true)return null;
			long introduced=0, removed=0;
			if(rs.getTimestamp("introduced") != null){
				introduced=rs.getTimestamp("introduced").getTime();
			}
			if(rs.getTimestamp("discontinued") != null){
				removed=rs.getTimestamp("discontinued").getTime();
			}
			Computer p =  new Computer(rs.getString("name"), new Date(introduced), new Date(removed), new Company(rs.getString("company.name")));//company.name company_id
			liste.add(p);
		}
		return liste;
	}
}
