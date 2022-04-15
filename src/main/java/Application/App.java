package Application;

import java.util.Map;

import Controllers.ReimbursementController;
import Controllers.UserController;
import Exceptions.IncorrectInfoException;
import Exceptions.UsernameUnavailableException;
import Models.User;
import Services.UserServices;
import Services.UserServicesImpl;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;
import static io.javalin.apibuilder.ApiBuilder.*;

public class App {
	private static UserServices userServ = new UserServicesImpl();

	public static void main (String[] args) {
		Javalin app = Javalin.create(config -> {
			config.enableCorsForAllOrigins();
		});
		app.start(7070);

	
		app.routes(() -> {
				path("{id}", () -> {
				});
			});
			path("users", () -> {
				post(UserController::registerUser);
				path("{id}", () -> {
					get(UserController::getUserById);
				});
			});
			path("/auth", () -> {
				post(UserController::logIn);
			});
		
	}
	}
