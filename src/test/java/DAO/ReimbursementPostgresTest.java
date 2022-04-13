package DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import Models.Reimbursement;
@TestMethodOrder(OrderAnnotation.class)
public class ReimbursementPostgresTest {
	private static ReimbursementDAO reimbursementDao = DAOFactory.getReimbursementDAO();
	private static Reimbursement testReimbursement = new Reimbursement();
	private static Reimbursement testNewReimbursement = new Reimbursement();


	@BeforeAll
	static void setUp(){
		testReimbursement.setUserid(10);
		
		Random rand  = new Random();
		testNewReimbursement.setUserid(12 + rand.nextInt());
		testReimbursement.setRequestid(reimbursementDao.create(testReimbursement));
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws SQLException {
		reimbursementDao.delete(testReimbursement);
	}

	@Test
	@Order(1)
	void testCreate() {
		int id = reimbursementDao.create(testNewReimbursement);
		testNewReimbursement.setUserid(id);
		
		assertNotEquals(0, id);

	}

	@Test
	public void testGetById() {
       int id = testReimbursement.getRequestid();
		
		Reimbursement reimbursement = reimbursementDao.getById(id);
		
		assertEquals(testReimbursement, reimbursement);
	}


	@Test
	public void testGetAll() {
		assertNotNull(reimbursementDao.getAll());
	}

	@Test
	public void testUpdate() {
		assertDoesNotThrow(() -> {
			reimbursementDao.update(testReimbursement);
		});

		
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByStatus() {
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
	void testGetByOwner() {
		fail("Not yet implemented");
	}

}


