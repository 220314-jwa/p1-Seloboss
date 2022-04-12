package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.Connectioncode;
import Models.Login;

public class LoginDAOPostgres implements LoginDAO {
	private static Connectioncode conncode = Connectioncode.getConnectionFactory();
	
	Login user = new Login();

	@Override
	public Login logincheck(String login, String password) {
		String loginsql = "SELECT LOGIN_ID \r\n"
				+ "FROM USERS \r\n"
				+ "WHERE LOGIN_ID = ?";
		try {Connection connection = conncode.getConnection();
		try (PreparedStatement logInPStatement = connection.prepareStatement(loginsql)){
			
			logInPStatement.setString(1, login);
			ResultSet rs = logInPStatement.executeQuery();
			while (rs.next()) {
				user.setUsername(rs.getString("LOGIN_ID"));
			}
				user.setPassword(passWordVerify(password));		
				user.setUsertype(getUsertype(password));
				user.setUsernum(usernum(password));
				
		}	
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return user;
}

	public String passWordVerify(String password) {		
		String pswv = null;
		//SQL SELECT statement
		String passUrl = "SELECT  PASSWORD\r\n"
				+ "FROM USER\r\n"
				+ "WHERE PASSWORD = ?";
		try {Connection connection = conncode.getConnection();
		try (PreparedStatement passWordPStatement = connection.prepareStatement(passUrl)){
			
			passWordPStatement.setString(1, password);
			ResultSet rs = passWordPStatement.executeQuery();
			while (rs.next()) {	
				  pswv = rs.getString("PASSWORD");	
				};
		}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return pswv;
		
			       
}

	public String getUsertype(String password) {
		String typeuser = null;
		//SQL SELECT statement
		String usertypesql = "SELECT  USERTYPE\r\n"
				+ "FROM USER\r\n"
				+ "WHERE  PASSWORD = ?";

		try {Connection connection = conncode.getConnection();
			try (PreparedStatement jobTitlePStatement = connection.prepareStatement(usertypesql)){
				
				jobTitlePStatement.setString(1, password);
				ResultSet rs = jobTitlePStatement.executeQuery();
				while (rs.next()) {	
					  typeuser = rs.getString("USERTYPE");	
					};
			}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
				return typeuser;
			
				       
}
	public int usernum(String password) {		
		int num = 0;
		//SQL SELECT statement
		String usernumsql = "SELECT USERNUM\r\n"
				+ "FROM USER\r\n"
				+ "WHERE  PASSWORD = ?";

		try {Connection connection = conncode.getConnection();
			try (PreparedStatement employeeNumPStatement = connection.prepareStatement(usernumsql)){
				
				employeeNumPStatement.setString(1, password);
				ResultSet rs = employeeNumPStatement.executeQuery();
				while (rs.next()) {	
					  num = rs.getInt("USERNUM");	  
					};
			}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
				return num;
			
				       
}
	


	@Override
	public Login getemployeeNumberById(int id) {
		String empNumByIdsql = "SELECT  *\r\n"
				+ "FROM USER\r\n"
				+ "WHERE  USERNUM = ?";

		try {Connection connection = conncode.getConnection();
			try (PreparedStatement employeeNumByIdPStatement = connection.prepareStatement(empNumByIdsql)){
				
				employeeNumByIdPStatement.setInt(1,id);
				ResultSet rs = employeeNumByIdPStatement.executeQuery();
				while (rs.next()) {	
					user.setUsernum(rs.getInt("USERNUM"));
					user.setUsertype(rs.getString("USERTYPE"));
					user.setFirstName(rs.getString("FIRSTNAME"));
					user.setLastName(rs.getString("LASTNAME"));
					user.setUsername(rs.getString("LOGIN_ID"));
					user.setPassword(rs.getString("PASSWORD"));
					  	  
					};
			}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
				return user;
			
				       
}

}