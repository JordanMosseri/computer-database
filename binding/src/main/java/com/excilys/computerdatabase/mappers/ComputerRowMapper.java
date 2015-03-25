package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.util.Utils;

public class ComputerRowMapper implements RowMapper<Computer> {
	
	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Company company;
		
		//if (rs.getString("company.name") != null) {
			company = (new Company(rs.getString("company.name"), rs.getInt("company.id")));
		//}
		
		return new Computer(rs.getInt("id"), rs.getString("name"), Utils.getLocalDateTime(rs.getTimestamp("introduced")), Utils.getLocalDateTime(rs.getTimestamp("discontinued")), company);
	}

}
