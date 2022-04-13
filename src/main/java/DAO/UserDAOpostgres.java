package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import Connection.Connectioncode;
import Models.User;

public class UserDAOpostgres implements UserDAO {
	private static Connectioncode conncode = Connectioncode.getConnectionFactory();
	

	@Override
	public int create(User newObj) {
		int generatedId = 0;
		Connection conn =conncode.getConnection();
		
		try {
			String sql = "insert into users( fullname, username, passwd, roleid)" + "values(?,?,?,?)";
			 PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			 pstmt.setString(1, newObj.getFirstname() + "" + newObj.getLastname());
			 pstmt.setString(2, newObj.getUsername());
			 pstmt.setString(3,  newObj.getPassword());
			 pstmt.setInt(4, 1);
			 
			 conn.setAutoCommit(false);
			 pstmt.executeUpdate();
			 ResultSet resultSet = pstmt.getGeneratedKeys();
				
				if (resultSet.next()) {
					generatedId = resultSet.getInt(1);
					conn.commit();
				} else {
					conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			return generatedId;

		}

	@Override
	public User getById(int id) {
		User user = null;
		try (Connection conn = conncode.getConnection()) {
			String sql = "select * from users left join manager on users.id=manager.owner_id"
					+ " where users.id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(id);
				String fullName = resultSet.getString("fullname");
				user.setFirstname(fullName.substring(0, fullName.indexOf(' ')));
				user.setLastname(fullName.substring(fullName.indexOf(' ') + 1));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("passwd"));
				
				ReimbursementDAO reimbursementDao = DAOFactory.getReimbursementDAO();
				user.setReimbursements(reimbursementDao.getByOwner(user));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}



	@Override
	public List<User> getAll() {
		List<User> users = new LinkedList<>();
		try (Connection conn = conncode.getConnection()) {
			String sql = "select * from users left join manager on users.id=manager.owner_id";
			Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			User user = new User();
			user.setId(resultSet.getInt("id"));
			String fullName = resultSet.getString("fullname");
			user.setFirstname(fullName.substring(0, fullName.indexOf(' ')));
			user.setLastname(fullName.substring(fullName.indexOf(' ') + 1));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("passwd"));
			
			ReimbursementDAO reimbursementDao = DAOFactory.getReimbursementDAO();
			user.setReimbursements(reimbursementDao.getByOwner(user));
			
			users.add(user);
			
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return users;
}


	@Override
	public void update(User updateObj) throws SQLException {
		Connection conn = conncode.getConnection();
		try {
			String sql = "update users set fullname=?, username=?, passwd=?, roleid=? "
					+ "where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, updateObj.getFirstname() + " " + updateObj.getLastname());
			pStmt.setString(2, updateObj.getUsername());
			pStmt.setString(3, updateObj.getPassword());
			pStmt.setInt(4, 1);
			pStmt.setInt(5, updateObj.getId());
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			int rowsUpdated = pStmt.executeUpdate();
			
			if (rowsUpdated==1) {
				conn.commit();
			} else {
				conn.rollback();
				throw new SQLException("ERROR: no object found to update");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void delete(User objToDelete) throws SQLException {
		Connection conn = conncode.getConnection();
		try {
			String sql = "delete from users where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, objToDelete.getId());
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			int rowsUpdated = pStmt.executeUpdate();
			
			if (rowsUpdated==1) {
				conn.commit();
			} else {
				conn.rollback();
				throw new SQLException("ERROR: no object found to delete");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public User getByUsername(String username) {
		User user = null;
		try(Connection conn = conncode.getConnection()){
			String sql = "select * from users left join manager on users.id=manager.owner_id"
					+ " where users.username = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, username);
			
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				String fullName = resultSet.getString("fullname");
				user.setFirstname(fullName.substring(0, fullName.indexOf(' ')));
				user.setLastname(fullName.substring(fullName.indexOf(' ') + 1));
				user.setUsername(username);
				user.setPassword(resultSet.getString("passwd"));
				
				ReimbursementDAO reimbursementDao = DAOFactory.getReimbursementDAO();
				user.setReimbursements(reimbursementDao.getByOwner(user));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;

		}
	
	@Override
	public void updateReimbursement(int requestid, int userId) throws SQLException {
		Connection conn = conncode.getConnection();
		try {
			String sql = "insert into manager (requestid, owner_id) values (?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, requestid);
			pStmt.setInt(2, userId);
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			int rowsUpdated = pStmt.executeUpdate();
			
			if (rowsUpdated==1) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
