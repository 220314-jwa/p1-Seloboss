package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.Connectioncode;
import Models.Login;
import Models.User;
import org.postgresql.util.PSQLException;

public class LoginDAOPostgres implements LoginDAO {
	private static Connectioncode conncode = Connectioncode.getConnectionFactory();

	Login user = new Login();

	@Override
	public Login logincheck(String login, String password) {
		String loginsql = "SELECT LOGIN_ID \r\n"
				+ "FROM USERS \r\n"
				+ "WHERE LOGIN_ID = ?";
		try {
			Connection connection = conncode.getConnection();
			try (PreparedStatement logInPStatement = connection.prepareStatement(loginsql)) {

				logInPStatement.setString(1, login);
				ResultSet rs = logInPStatement.executeQuery();
				while (rs.next()) {
					user.setUsername(rs.getString("LOGIN_ID"));
				}
				user.setPassword(passWordVerify(password));
				user.setUsertype(getUsertype(password));
				user.setUsernum(usernum(password));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public String passWordVerify(String password) {
		String pswv = null;
		//SQL SELECT statement
		String passUrl = "SELECT  PASSWORD\r\n"
				+ "FROM USER\r\n"
				+ "WHERE PASSWORD = ?";
		try {
			Connection connection = conncode.getConnection();
			try (PreparedStatement passWordPStatement = connection.prepareStatement(passUrl)) {

				passWordPStatement.setString(1, password);
				ResultSet rs = passWordPStatement.executeQuery();
				while (rs.next()) {
					pswv = rs.getString("PASSWORD");
				}
				;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pswv;


	}

	public String getUsertype(String password) {
		String typeuser = null;
		//SQL SELECT statement
		String usertypesql = "SELECT  USERTYPE\r\n"
				+ "FROM USER\r\n"
				+ "WHERE  PASSWORD = ?";

		try {
			Connection connection = conncode.getConnection();
			try (PreparedStatement jobTitlePStatement = connection.prepareStatement(usertypesql)) {

				jobTitlePStatement.setString(1, password);
				ResultSet rs = jobTitlePStatement.executeQuery();
				while (rs.next()) {
					typeuser = rs.getString("USERTYPE");
				}
				;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return typeuser;


	}

	public int usernum(String password) {
		int num = 0;
		//SQL SELECT statement
		String usernumsql = "SELECT USERNUM\r\n"
				+ "FROM USER\r\n"
				+ "WHERE  PASSWORD = ?";

		try {
			Connection connection = conncode.getConnection();
			try (PreparedStatement employeeNumPStatement = connection.prepareStatement(usernumsql)) {

				employeeNumPStatement.setString(1, password);
				ResultSet rs = employeeNumPStatement.executeQuery();
				while (rs.next()) {
					num = rs.getInt("USERNUM");
				}
				;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;


	}
	public int create(User newObj) {
		public int create(User newObj) {
			int generatedId = 0;
			Connection conn = conncode.getConnection();
			try {
				String sql = "insert into person (full_name, username, passwd, role_id)"
						+ " values (?,?,?,?)";
				PreparedStatement pStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				pStmt.setString(1, newObj.getFirstName() + " " + newObj.getLastName());
				pStmt.setString(2, newObj.getUsername());
				pStmt.setString(3, newObj.getPassword());
				pStmt.setInt(4, 1);

				conn.setAutoCommit(false);
				pStmt.executeUpdate();
				ResultSet resultSet = pStmt.getGeneratedKeys();

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

	}

	public User getById(int id) {
		public User getById(int id) {
			User user = null;
			try (Connection conn = conncode.getConnection()) {
				String sql = "select * from person left join pet_owner on person.id=pet_owner.owner_id"
						+ " where person.id = ?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setInt(1, id);

				ResultSet resultSet = pStmt.executeQuery();
				if (resultSet.next()) {
					user = new User();
					user.setId(id);
					String fullName = resultSet.getString("full_name");
					user.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
					user.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("passwd"));

					PetDAO petDao = DAOFactory.getPetDAO();
					user.setPets(petDao.getByOwner(user));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return user;
		}


		public List<T> getAll() {
			List<User> users = new LinkedList<>();
			try (Connection conn = conncode.getConnection()) {
				// left join because we want ALL the people even if they don't have any pets.
				// a full join would be fine too since everything in the pet_owner table
				// will have a user associated with it, but a left join makes more sense logically
				String sql = "select * from person left join pet_owner on person.id=pet_owner.owner_id";
				Statement stmt = conn.createStatement();

				ResultSet resultSet = stmt.executeQuery(sql);
				while (resultSet.next()) {
					User user = new User();
					user.setId(resultSet.getInt("id"));
					String fullName = resultSet.getString("full_name");
					user.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
					user.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("passwd"));

					PetDAO petDao = DAOFactory.getPetDAO();
					user.setPets(petDao.getByOwner(user));

					users.add(user);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return users;
		}


		public void update(User updateObj) throws SQLException {
			Connection conn = conncode.getConnection();
			try {
				String sql = "update person set full_name=?, username=?, passwd=?, role_id=? "
						+ "where id=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, updatedObj.getFirstName() + " " + updatedObj.getLastName());
				pStmt.setString(2, updatedObj.getUsername());
				pStmt.setString(3, updatedObj.getPassword());
				pStmt.setInt(4, 1);
				pStmt.setInt(5, updatedObj.getId());

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


	}

	public void delete(User objToDelete) throws SQLException {
			Connection conn = conncode.getConnection();
			try {
				String sql = "delete from person where id=?";
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


	}
}
