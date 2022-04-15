package Controllers;

import java.util.Map;

import Exceptions.IncorrectInfoException;
import Exceptions.UsernameUnavailableException;
import Models.User;
import Services.UserServices;
import Services.UserServicesImpl;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

public class UserController {
    private static UserServices userServ = new UserServicesImpl();

    // POST to /users
    public static void registerUser(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);

        try {
            newUser = userServ.register(newUser);
            ctx.json(newUser);
        } catch (UsernameUnavailableException e) {
            ctx.status(HttpCode.CONFLICT); // 409 conflict
        }
    }

    // POST to /auth
    public static void logIn(Context ctx) {
        Map<String,String> credentials = ctx.bodyAsClass(Map.class);
        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            User user = userServ.login(username, password);
            ctx.json(user);
        } catch (IncorrectInfoException e) {
            ctx.status(HttpCode.UNAUTHORIZED); // 401 unauthorized
        }
    }

    // GET to /users/{id} where {id} is the user's id
    public static void getUserById(Context ctx) {
        String pathParam = ctx.pathParam("id");
        if (pathParam != null && !pathParam.equals("undefined") && !pathParam.equals("null")) {
            int userId = Integer.parseInt(pathParam);

            User user = userServ.getUserById(userId);
            if (user != null)
                ctx.json(user);
            else
                ctx.status(HttpCode.NOT_FOUND); // 404 not found
        } else {
            ctx.status(HttpCode.BAD_REQUEST); // 400 bad request
        }
    }
}

