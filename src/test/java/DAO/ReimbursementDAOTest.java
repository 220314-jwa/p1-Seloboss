package DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import Models.Reimbursement;

@TestMethodOrder(OrderAnnotation.class)
public class ReimbursementDAOTest {
	private static ReimbursementDAO reimbursementDao = DAOFactory.getReimbursementDAO();
	private static Reimbursement testReimbursement = new Reimbursement();
	private static Reimbursement testNewReimbursement = new Reimbursement();


	@BeforeAll
	public static void setUp() {
testReimbursement.setUserid(10);
		
		Random rand  = new Random();
		testNewReimbursement.setUserid(12 + rand.nextInt());
		testReimbursement.setRequestid(reimbursementDao.create(testReimbursement));
		
	}

	@AfterAll
		public static void cleanup() throws SQLException {
			reimbursementDao.delete(testReimbursement);
	}

	@Test
	public void getByStatus() {
		String testStatus = "Pending";
		List<Reimbursement> reimbursements = reimbursementDao.getByStatus(testStatus);
		
		boolean onlyCorrectStatus = true;
		for (Reimbursement reimbursement : reimbursements) {
			if (!(reimbursement.getStatus().equals(testStatus))) {
				onlyCorrectStatus = false;
				break;
			}
		}
		assertTrue(onlyCorrectStatus);
	}

	@Test
	@Disabled
	void testGetByOwner() {
		fail("Not yet implemented");
	}

	@Test
	@Order(1)
	public void createReimbursementSuccessfully() {
		int id = reimbursementDao.create(testNewReimbursement);
		testNewReimbursement.setUserid(id);
		
		assertNotEquals(0, id);
	}

	@Test
	public void getById() {
		 int id = testReimbursement.getRequestid();
			
			Reimbursement reimbursement = reimbursementDao.getById(id);
			
			assertEquals(testReimbursement, reimbursement);
	}

	@Test
	public void getAll() {
		assertNotNull(reimbursementDao.getAll());
	}

	@Test
	public void updateReimbursementExists() {
		assertDoesNotThrow(() -> {
			reimbursementDao.update(testReimbursement);
		});
;
	}
	@Test
	public void updateReimbursementDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			reimbursementDao.update(new Reimbursement());
		});
	}

	@Test
	@Order(2)
	public void deleteReimbursementExists() {
		assertDoesNotThrow(() -> {
			reimbursementDao.delete(testNewReimbursement);
		});
	}
	
	@Test
	public void deleteReimbursementDoesNotExist() {
		assertThrows(SQLException.class,() -> {
			reimbursementDao.delete(new Reimbursement());
		});
	}

}
