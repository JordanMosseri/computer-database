package com.excilys.computerdatabase.mappers.DBMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.modele.Company;

/**
 * To map Company objects from database.
 * @author Jordan Mosseri
 *
 */
public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new Company(rs.getString("name"), rs.getInt("id"));
	}
	
}
