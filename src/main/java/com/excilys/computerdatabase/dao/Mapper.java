package com.excilys.computerdatabase.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

public class Mapper {
	
	
	public static Computer mapper(ResultSet rs) throws SQLException {
		
		//System.out.println(rs.getTimestamp("introduced"));if(true)return null;
		long introduced=0, removed=0;
		Date dateIntroduced=null, dateRemoved=null;
		if(rs.getTimestamp("introduced") != null){
			introduced=rs.getTimestamp("introduced").getTime();
			dateIntroduced = new Date(introduced);
		}
		if(rs.getTimestamp("discontinued") != null){
			removed=rs.getTimestamp("discontinued").getTime();
			dateRemoved = new Date(removed);
		}
		Computer c =  new Computer(rs.getInt("id"), rs.getString("name"), dateIntroduced, dateRemoved, new Company(rs.getString("company.name"), rs.getInt("company.id")));//company.name company_id
		return c;
	}
}
