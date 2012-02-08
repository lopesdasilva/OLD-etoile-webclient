/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

	private String  connectionURL;
	private String user;
	private String password;
	private Connection connection = null; 


	/*
	 * 
	 * @param connectionURL		String where the database is. e.g. = jdbc:mysql://localhost:3306/DATABASE
	 * @param user		String Database Username
	 * @param password		String Database Password
	 * Database Connector Constructor 
	 */
	public DBConnect(String connectionURL, String user, String password){
		this.connectionURL=connectionURL;
		this.user=user;
		this.password=password;
	}

	public void loadDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

		// declare a connection by using Connection interface 


		// Load JBBC driver "com.mysql.jdbc.Driver" 
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 

		/* Create a connection by using getConnection() method that takes parameters of 
		        	string type connection url, user name and password to connect to database. */ 
		connection = DriverManager.getConnection(connectionURL, user, password);

	}

	public void updateDB(String sqlStatement) throws SQLException{
		Statement state=connection.createStatement();
		int updateCount = state.executeUpdate(sqlStatement);



	}

	public ResultSet queryDB(String sqlStatement) throws SQLException{
		ResultSet resultset = null;
		PreparedStatement prepState=null;

		prepState=connection.prepareStatement(sqlStatement);

		resultset=prepState.executeQuery();

		return resultset;
	}

	public void closeDB() throws SQLException{
		connection.close();
	}



}
