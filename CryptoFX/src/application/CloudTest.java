package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CloudTest {

	static final String JDBC_URL = "jdbc:mysql://34.129.113.178:3306/LogInInfo";
	static final String USERNAME = "griffi10_VUWadmin";
	static final String PASSWORD = "HB%VLy7wFA[Z";

	
	public CloudTest () {
		try {
			//register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//open connection
			System.out.println("Connecting to database ...");
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			
			//execute query
//			System.out.println("Creating statement ...");
//			Statement statement = connection.createStatement();
//			String sql = "SELECT * FROM LogInInfo";
//			ResultSet resultSet = statement.executeQuery(sql);
//			
//			//go through resultSet to print it
//			while (resultSet.next()) {
//				String userName = resultSet.getString("username");
//				String passWord = resultSet.getString("password");
//				
//				System.out.println("Username: " + userName + ", Passowrd: " + passWord);
//			}
//			
//			//close external resources
//			resultSet.close();
//			statement.close();
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	} 
	
	public static void main(String[] args) {
		new CloudTest();
	}
	
}