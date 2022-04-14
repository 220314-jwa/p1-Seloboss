package DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import Models.User;

@TestMethodOrder(OrderAnnotation.class)
public class UserDAOTest {
	private static UserDAO userDao = DAOFactory.getUserDAO();
	private static User testUser = new User();
	private static User testNewUser = new User();
	
	@BeforeAll
	public static void setUp() {
		testUser.setUsername("test");
		Random rand = new Random();
		testNewUser.setUsername("test_" + rand.nextLong());
		testUser.setId(userDao.create(testUser));
	}
	
	@AfterAll
	public static void cleanUp() {
		try {
			userDao.delete(testUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getByUsernameExists() {
		User user = userDao.getByUsername("selome");
		assertEquals(testUser, user);
	}
	
	@Test
	public void getByUsernameDoesNotExist() {
		User user = userDao.getByUsername("qwertyuiop");
		assertNull(user);
	}
	
	@Test
	@Order(1)
	public void createUserSuccessfully() {
		int id = userDao.create(testNewUser);
		testNewUser.setId(id);
		
		assertNotEquals(0, id);
	}
	
	@Test
	public void createUserDuplicateUsername() {
		int id = userDao.create(testUser);
		
		assertEquals(0, id);
	}
	
	@Test
	public void getByIdExists() {
		int id = testUser.getId();
		
		User user = userDao.getById(id);
		
		assertEquals(testUser, user);
	}
	
	@Test
	public void getByIdDoesNotExist() {
		User user = userDao.getById(0);
		assertNull(user);
	}
	
	@Test
	public void getAll() {
		assertNotNull(userDao.getAll());
	}
	
	@Test
	public void updateUserExists() {
		assertDoesNotThrow(() -> {
			userDao.update(testUser);
		});
	}
	
	@Test
	public void updateUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			userDao.update(new User());
		});
	}
	
	@Test
	@Order(2)
	public void deleteUserExists() {
		assertDoesNotThrow(() -> {
			userDao.delete(testNewUser);
		});
	}
	
	@Test
	public void deleteUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			userDao.delete(new User());
		});
	}
}
