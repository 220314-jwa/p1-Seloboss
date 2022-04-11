package DAO;

import Models.User;

public interface UserDAO extends MainDAO<User> {
	
	public User getByUsername (String username);

}
