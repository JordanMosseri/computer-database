package com.excilys.computerdatabase.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

public class DAOMapper implements RowMapper<Computer> {
	
	
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
		
		if(computer.getDateAdded() != null) {
			dateAddedTimestamp = Timestamp.valueOf(computer.getDateAdded().withHour(0).withMinute(0).withSecond(0));
		}
		if(computer.getDateRemoved() != null) {
			dateRemovedTimestamp = Timestamp.valueOf(computer.getDateRemoved().withHour(0).withMinute(0).withSecond(0));
		}
		
		//pstmt.setTimestamp(2, computer.getDateAddedLong()==0 ? null : new Timestamp(computer.getDateAddedLong()));
		//pstmt.setTimestamp(3, computer.getDateRemovedLong()==0 ? null : new Timestamp(computer.getDateRemovedLong()));
		
		pstmt.setString(1,computer.getName());
		pstmt.setTimestamp(2, dateAddedTimestamp);
		pstmt.setTimestamp(3, dateRemovedTimestamp);
		pstmt.setInt(4, computer.getCompany().getId());
	}


	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Computer computer = new Computer();
		
		computer.setId(rs.getInt("id"));
		
		computer.setName(rs.getString("name"));
		
		if(rs.getTimestamp("introduced") != null)
			computer.setDateAdded(rs.getTimestamp("introduced").toLocalDateTime());
		
		if(rs.getTimestamp("discontinued")!=null)
			computer.setDateRemoved(rs.getTimestamp("discontinued").toLocalDateTime());
		
		//if (rs.getString("company.name") != null) {
			computer.setCompany(new Company(rs.getString("company.name"), rs.getInt("company.id")));
		//}
		
		return computer;
	}
	
	/*List<Map<String, Object>> rows = jdbcTemplate.queryForList(QUERY_GET_ALL+endOfQuery);
	for (Map<String, Object> row : rows) {
		
		int cid = NumberUtils.toInt("" + (Long) row.get("id"));
		
		String cname = (String) (row.get("computer_name"));
		
		LocalDateTime dadded = null;
		
		LocalDateTime dremoved = null;
		
		if (row.get("introduced") != null){
			dadded = (((Timestamp) row.get("introduced")).toLocalDateTime());
		}
		if (row.get("discontinued") != null){
			dremoved = (((Timestamp) row.get("discontinued")).toLocalDateTime());
		}
		
		String companyName = "";
		int companyId = -1;
		
		if (row.get("company_name") != null) {
			companyName = (String) row.get("company_name");
		}
		
		if (row.get("company_id") != null) {
			companyId = Integer.parseInt(String.valueOf(row.get("company_id")));
		}
		
		Computer computer = new Computer(cid, cname, dadded, dremoved, new Company(companyName, companyId));
		//System.out.println(computer.toString());
		
		liste.add(computer);
	}*/
	
	
	
}


