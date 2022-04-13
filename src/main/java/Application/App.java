package Application;

import Exceptions.UsernameUnavailableException;
import Models.User;
import Services.UserServices;
import Services.UserServicesImpl;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;

public class App {
 private static UserServices userServ = new UserServicesImpl();
 
 public static void main(String[] args) {
	 Javalin app = Javalin.create(config ->{
	});
	 app.start(7070);
	 
	 app.post("/users", ctx -> {
			// context bodyAsClass method will transform JSON data into
			// a Java object as long as the JSON keys match the Java fields
			User newUser = ctx.bodyAsClass(User.class);
			
			try {
				newUser = userServ.register(newUser);
				ctx.json(newUser);
			} catch (UsernameUnavailableException e) {
				ctx.status(HttpCode.CONFLICT); // 409 conflict
			}
		});

 }
}
