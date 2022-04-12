package DAO;

import Models.Login;
import Models.User;

public interface LoginDAO extends MainDAO<User> {
  public Login logincheck (String login, String password);

}
