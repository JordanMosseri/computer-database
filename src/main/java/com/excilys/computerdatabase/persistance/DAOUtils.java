package com.excilys.computerdatabase.persistance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOUtils {
	public static boolean UNIT_TEST = false;
	
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
	
	public static void createConnectionPool(){
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
	
	static{
		loadProperties();
		
		loadDriver();
		
		createConnectionPool();
	}
	
	public static Connection getConnexion() {
        if(UNIT_TEST){
        	return getDriverManagerConnexion(true);
        }
        
		//return getDataSourceConnexion();
		return getPoolConnexion();
	}
	
	/*@Autowired
	@Qualifier(value="dataSource")
	DataSource dataSource;*/
	
	public static Connection getDataSourceConnexion() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		
		Connection cn = null;
		
		try {
			cn = dataSource.getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cn;
	}
	
	public static Connection getPoolConnexion() {
		
		Connection cn = null;
		
		try {
			cn = connectionPool.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cn;
	}
	
	public static Connection getDriverManagerConnexion(boolean test) {
		Connection cn = null;
		
		try {
			if(test){
				cn = DriverManager.getConnection(URL_TEST,USER,PASS);
			}
			else{
				cn = DriverManager.getConnection(URL,USER,PASS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cn;
	}
	
	
}
