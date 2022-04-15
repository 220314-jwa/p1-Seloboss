package Application;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import Connection.Connectioncode;
import Exceptions.IncorrectInfoException;
import Exceptions.UsernameUnavailableException;
import Models.User;
import Services.UserServices;
import Services.UserServicesImpl;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;

public class testapp {
	private static UserServices userServ = new UserServicesImpl();

	public static void main (String[] args) {
		Javalin app = Javalin.create(config -> {
			config.enableCorsForAllOrigins();
		});
		app.start(1010);
	
	app.get("/Reimbursement", ctx -> {
	});
	
	
	app.post("/users", ctx -> {
		User newUser = ctx.bodyAsClass(User.class);
		
		try {
			newUser = userServ.register(newUser);
			ctx.json(newUser);
		} catch (UsernameUnavailableException e) {
			ctx.status(HttpCode.CONFLICT); // 409 conflict
		}
	});
	app.post("/auth", ctx -> {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		String username = credentials.get("username");
		String password = credentials.get("password");
		
		try {
			User user = userServ.login(username, password);
			ctx.json(user);
		} catch (IncorrectInfoException e) {
			ctx.status(HttpCode.UNAUTHORIZED); // 401 unauthorized
		}
	});
	
}
}