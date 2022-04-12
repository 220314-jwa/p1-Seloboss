package DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Random;

import java.sql.SQLException;

import Models.User;
import org.junit.jupiter.api.Test;
@TestMethodOrder(OrderAnnotation.class)
public class LoginDAOPostgresTest {





		private static LoginDAO loginDAO = DAOFactory.getLoginDAO();
		private static User testUser = new User();
		private static User testNewUser = new User();

		@BeforeAll
		public static void setUp() {

			testUser.setUsername("test");


			Random rand = new Random();
			testNewUser.setUsername("test_" + rand.nextLong());



			testUser.setId(loginDAO.create(testUser));
		}

		@AfterAll
		public static void cleanUp() {
			try {
				loginDAO.delete(testUser);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Test
		public void getByUsernameExists() {
			User user = loginDAO.getByUsername("test");
			assertEquals(testUser, user);
		}

		@Test
		public void getByUsernameDoesNotExist() {
			User user = loginDAO.getByUsername("qwertyuiop");
			assertNull(user);
		}

		@Test
		@Order(1)
		public void createUserSuccessfully() {
			int id = loginDAO.create(testNewUser);
			testNewUser.setId(id);

			assertNotEquals(0, id);
		}

		@Test
		public void createUserDuplicateUsername() {
			int id = loginDAO.create(testUser);

			assertEquals(0, id);
		}

		@Test
		public void getByIdExists() {
			int id = testUser.getId();

			User user = loginDAO.getById(id);

			assertEquals(testUser, user);
		}

		@Test
		public void getByIdDoesNotExist() {
			User user = loginDAO.getById(0);
			assertNull(user);
		}

		@Test
		public void getAll() {
			assertNotNull(loginDAO.getAll());
		}

		@Test
		public void updateUserExists() {
			assertDoesNotThrow(() -> {
				loginDAO.update(testUser);
			});
		}

		@Test
		public void updateUserDoesNotExist() {
			assertThrows(SQLException.class, () -> {
				loginDAO.update(new User());
			});
		}

		@Test
		@Order(2)
		public void deleteUserExists() {
			assertDoesNotThrow(() -> {
				loginDAO.delete(testNewUser);
			});
		}

		@Test
		public void deleteUserDoesNotExist() {
			assertThrows(SQLException.class, () -> {
				loginDAO.delete(new User());
			});
		}
	}

