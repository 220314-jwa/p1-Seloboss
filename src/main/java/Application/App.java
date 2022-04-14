package Application;

import java.util.Map;

import Exceptions.IncorrectInfoException;
import Exceptions.UsernameUnavailableException;
import Models.User;
import Services.UserServices;
import Services.UserServicesImpl;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;

public class App {
	private static UserServices userServ = new UserServicesImpl();

	public static void main (String[] args) {
		Javalin app = Javalin.create(config -> {
			config.enableCorsForAllOrigins();
		});
		app.start();

		// cleaning up my main method by switching to app.routes and moving logic to controllers
		app.routes(() -> {
			// all paths starting with /pets
			path("reimbursement", () -> {
				get(ReimbursementController::getReimbursements);
				// /pets/4
				path("{id}", () -> {
					get(ReimbursementController::getReimbursementById);
					// /pets/4/adopt
					put("submit", ReimbursementController::submitReimbursement);
				});
			});
			// all paths starting with /users
			path("users", () -> {
				post(UsersController::registerUser);
				path("{id}", () -> {
					get(UsersController::getUserById);
				});
			});
			// all paths starting with /auth
			path("auth", () -> {
				post(UsersController::logIn);
			});
		});

	}
