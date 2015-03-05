package com.excilys.computerdatabase.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class AbstractDAO {
	
	private static final String FICHIER_PROPERTIES       = "dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "user";
    private static final String PROPERTY_MOT_DE_PASSE    = "pass";
	
	//
	public static final String URL_DOMAIN = "jdbc:mysql://localhost:3306/";
	public static final String URL_OPTIONS = "?zeroDateTimeBehavior=convertToNull";
	
	public static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static String URL = URL_DOMAIN + "computer-database-db" + URL_OPTIONS;
	public static String URL_TEST = URL_DOMAIN + "computer-database-db-tests" + URL_OPTIONS;
	public static String USER = "admincdb";//root
	public static String PASS = "qwerty1234";//root
	//
	
	protected ResultSet rs = null ;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	public Connection cn = null;
	static BoneCP connectionPool = null;
	
	
	public static void loadProperties(){
		Properties properties = new Properties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
        
        if ( fichierProperties == null ) {
            throw new RuntimeException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        }

        try {
            properties.load( fichierProperties );
            URL = properties.getProperty( PROPERTY_URL );
            DRIVER_NAME = properties.getProperty( PROPERTY_DRIVER );
            USER = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            PASS = properties.getProperty( PROPERTY_MOT_DE_PASSE );
            
        } catch ( FileNotFoundException e ) {
            throw new RuntimeException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.", e );
        } catch ( IOException e ) {
            throw new RuntimeException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        }
	}
	
	public static void loadDriver(){
		try {
			Class.forName(DRIVER_NAME).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException( "Le driver est introuvable dans le classpath.", e );
		}
	}
	
	
	
	static{
		loadProperties();
		
		loadDriver();
		
		try {
            //Creates the config
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl( URL );
            config.setUsername( USER );
            config.setPassword( PASS );
            
            //Sets the pool size
            config.setMinConnectionsPerPartition( 5 );
            config.setMaxConnectionsPerPartition( 10 );
            config.setPartitionCount( 2 );
            
            //Creates the pool
            connectionPool = new BoneCP( config );
            
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new RuntimeException( "Erreur de configuration du pool de connexions.", e );
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
