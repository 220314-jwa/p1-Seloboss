package DAO;

import java.sql.SQLException;

import Models.User;

public interface UserDAO extends MainDAO<User> {
	public User getByUsername(String username);
	public void updateReimbursement(int requestid, int userId) throws SQLException;

}
