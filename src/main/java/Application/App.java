package Application;

import java.sql.Connection;

import Connection.Connectioncode;
import Controllers.loginController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class App {
	private static Connectioncode conncode = Connectioncode.getConnectionFactory();
	public static void main (String[] args) {
		try(Connection connection = conncode.getConnection()){
			Javalin app = Javalin.create(config -> {
				config.enableCorsForAllOrigins();
			});
			app.start();
			
			app.routes(() -> {
				path("login", () ->{
					get(loginController::getlogin);
					
					path ("{id}",() ->{
						get(loginController::getloginbyid);
						
						put("requested", loginController:: requested);
					});
				});
				
				path("users", () ->{
					
				})
			}
			
		
	}

}
