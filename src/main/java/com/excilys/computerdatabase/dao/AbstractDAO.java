package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class AbstractDAO {
	
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	public static final String URL_DOMAIN = "jdbc:mysql://localhost:3306/";
	public static final String URL_OPTIONS = "?zeroDateTimeBehavior=convertToNull";
	
	public static final String URL = URL_DOMAIN + "computer-database-db" + URL_OPTIONS;
	public static final String URL_TEST = URL_DOMAIN + "computer-database-db-tests" + URL_OPTIONS;
	
	public static final String USER = "admincdb";//root
	public static final String PASS = "qwerty1234";//root
	
	public String url = URL;
	
	protected ResultSet rs = null ;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	public Connection cn = null;
	static BoneCP connectionPool = null;
	
	static{
		try {
			Class.forName(DRIVER_NAME).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
		}
		
		
		try {
            /*
             * Création d'une configuration de pool de connexions via l'objet
             * BoneCPConfig et les différents setters associés.
             */
            BoneCPConfig config = new BoneCPConfig();
            
            /* Mise en place de l'URL, du nom et du mot de passe */
            config.setJdbcUrl( URL );
            config.setUsername( USER );
            config.setPassword( PASS );
            
            /* Paramétrage de la taille du pool */
            config.setMinConnectionsPerPartition( 5 );
            config.setMaxConnectionsPerPartition( 10 );
            config.setPartitionCount( 2 );
            
            /* Création du pool à partir de la configuration, via l'objet BoneCP */
            connectionPool = new BoneCP( config );
            
        } catch ( SQLException e ) {
            e.printStackTrace();
            //throw new DAOConfigurationException( "Erreur de configuration du pool de connexions.", e );
        }
	}
	
	public static Connection getConnexion() {

		Connection cn = null;
		try {
			
			//cn = DriverManager.getConnection(this.url,USER,PASS)
			cn = connectionPool.getConnection();

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
