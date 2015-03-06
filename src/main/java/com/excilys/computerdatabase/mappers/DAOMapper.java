package com.excilys.computerdatabase.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

public class DAOMapper {
	
	
	public static Computer map(ResultSet rs) throws SQLException {
		
		//rs.getTimestamp("introduced").getTime()
		
		LocalDateTime dateIntroduced=null, dateRemoved=null;
		
		if(rs.getTimestamp("introduced") != null){
			dateIntroduced = rs.getTimestamp("introduced").toLocalDateTime();//new Date(introduced);
		}
		if(rs.getTimestamp("discontinued") != null){
			dateRemoved = rs.getTimestamp("discontinued").toLocalDateTime();
		}
		Computer c =  new Computer(rs.getInt("id"), rs.getString("name"), dateIntroduced, dateRemoved, new Company(rs.getString("company.name"), rs.getInt("company.id")));//company.name company_id
		return c;
	}
	
	
	public static void map(PreparedStatement pstmt, Computer computer) throws SQLException{
		
		Timestamp dateAddedTimestamp = null;
		Timestamp dateRemovedTimestamp = null;
		
		if(computer.dateAdded != null) {
			dateAddedTimestamp = Timestamp.valueOf(computer.dateAdded.withHour(0).withMinute(0).withSecond(0));
		}
		if(computer.dateRemoved != null) {
			dateRemovedTimestamp = Timestamp.valueOf(computer.dateRemoved.withHour(0).withMinute(0).withSecond(0));
		}
		
		//pstmt.setTimestamp(2, computer.getDateAddedLong()==0 ? null : new Timestamp(computer.getDateAddedLong()));
		//pstmt.setTimestamp(3, computer.getDateRemovedLong()==0 ? null : new Timestamp(computer.getDateRemovedLong()));
		
		pstmt.setString(1,computer.name);
		pstmt.setTimestamp(2, dateAddedTimestamp);
		pstmt.setTimestamp(3, dateRemovedTimestamp);
		pstmt.setInt(4, computer.company.id);
	}
}
