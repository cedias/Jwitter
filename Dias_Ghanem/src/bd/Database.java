package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {

	private static Database database;
	private DataSource dataSource;
	
	public Database(String jndiname) throws SQLException {
		try{
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
		}catch(NamingException e){
			throw new SQLException(jndiname + "is missing in JNDI! : "+e.getMessage());
		}
	}

	public static Connection getMySQLConnection() throws SQLException{
		if(!BDStatic.mysql_pooling){
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			return (DriverManager.getConnection("jdbc:mysql://"+BDStatic.mysql_host+"/"+BDStatic.mysql_db,BDStatic.mysql_username,BDStatic.mysql_password));
		}else{
			if(database==null){
				database = new Database("jdbc/db");
			}
			return(database.getConnection());
		}
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
