package com.excilys.computerdatabase.mappers.DBMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.mappers.DateMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

/**
 * To map Computer objects from database.
 * @author Jordan Mosseri
 *
 */
public class ComputerRowMapper implements RowMapper<Computer> {
	
	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Company company = null;
		
		
		try {
			company = (new Company(rs.getString("company.name"), rs.getInt("company.id")));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new Computer(rs.getInt("id"), rs.getString("name"), DateMapper.getLocalDateTime(rs.getTimestamp("introduced")), DateMapper.getLocalDateTime(rs.getTimestamp("discontinued")), company);
	}

}
