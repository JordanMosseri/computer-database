package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAOAbstrait {
	
	protected Connection getConnexion() {

		Connection cn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull","root","root");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cn;
	}
}
