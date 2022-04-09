package Connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connectioncode {
	 private static Connectioncode connectioncode = null;
		private static Properties properties;

		private Connectioncode() {
			InputStream stream = Connectioncode.class.getClassLoader().getResourceAsStream("database.properties");
			try {
				properties = new Properties();
				properties.load(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static Connectioncode getConnectionFactory() {
			if (connectioncode==null) connectioncode = new Connectioncode();
			return connectioncode;
		}
		
	    // return our connection to the database:
	    public Connection getConnection() {
	    	Connection connection = null;
	    	String url = properties.getProperty("url");
	        String username = properties.getProperty("username");
	        String password = properties.getProperty("password");

	        // try connecting to the database:
	        try {
	            // get connection
	            connection = DriverManager.getConnection(url, username, password);
	        }
	        catch (SQLException e) {
	            // if something goes wrong, view the stack trace
	            e.printStackTrace();
	        }
	        return connection;
	    }

}
