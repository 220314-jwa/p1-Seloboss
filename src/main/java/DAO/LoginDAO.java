package DAO;

import Models.Login;

public interface LoginDAO {
  public Login logincheck (String login, String password);
  public Login getemployeeNumberById(int id);
}
