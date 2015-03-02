package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDAO {
	
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	public static final String URL_DOMAIN = "jdbc:mysql://localhost:3306/";
	public static final String URL_OPTIONS = "?zeroDateTimeBehavior=convertToNull";
	
	public static final String URL = URL_DOMAIN + "computer-database-db" + URL_OPTIONS;
	public static final String URL_TEST = URL_DOMAIN + "computer-database-db-tests" + URL_OPTIONS;
	
	public String url = URL;
	
	protected ResultSet rs = null ;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected Connection cn = null;
	
	static{
		try {
			Class.forName(DRIVER_NAME).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected Connection getConnexion() {

		Connection cn = null;
		try {
			
			cn = DriverManager.getConnection(this.url,"root","root");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cn;
	}
	
	protected void mettreVariablesANull(){
		rs = null ;
		stmt = null;
		pstmt = null;
		cn = null;
	}
	
	protected void tryCloseVariables(){
		try {
			if (rs != null)
				rs.close();
			
			if (stmt != null)
				stmt.close();
			
			if (pstmt != null)
				pstmt.close();
			
			if (cn != null) 
				cn.close();
		} catch (SQLException e) {}
	}
}
