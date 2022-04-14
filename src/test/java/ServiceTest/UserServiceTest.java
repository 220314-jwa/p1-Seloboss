package ServiceTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import DAO.ReimbursementDAO;
import DAO.UserDAO;
import Exceptions.IncorrectInfoException;
import Exceptions.UsernameUnavailableException;
import Models.User;
import Services.UserServices;
import Services.UserServicesImpl;



@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
		@Mock 
		private UserDAO userDao;
		@Mock
		private ReimbursementDAO reimbursementDao;	
		@InjectMocks 
		private UserServices userServ = new UserServicesImpl();
		@Test
		public void exampleTest() {
			assertTrue(true);
		}
		
		@Test
		public void logInSuccessfully() throws IncorrectInfoException {
			String username = "selome";
			String password = "password";
			User mockUser = new User();
			mockUser.setUsername(username);
			mockUser.setPassword(password);
			when(userDao.getByUsername(username)).thenReturn(mockUser);
			User result = userServ.login(username, password);
			assertEquals(username, result.getUsername());
		}
		
		@Test
		public void logInWrongUsername() {
			String username = "Selo123";
			String password = "19980502";
			when(userDao.getByUsername(username)).thenReturn(null);
			
			assertThrows(IncorrectInfoException.class, () -> {
				userServ.login(username, password);
			});
		}
		
		@Test
		public void logInWrongPassword() {
			String username = "selome";
			String password = "1234";
			
			User mockUser = new User();
			mockUser.setUsername(username);
			mockUser.setPassword("fake_password");
			when(userDao.getByUsername(username)).thenReturn(mockUser);
			
			assertThrows(IncorrectInfoException.class, () -> {
				userServ.login(username, password);
			});
		}
		
		@Test
		public void registerSuccessfully() throws UsernameUnavailableException {
			User newUser = new User();
			when(userDao.create(newUser)).thenReturn(1);
			
			User result = userServ.register(newUser);
			assertNotEquals(0, result.getId());
		}
		
		@Test
		public void registerUsernameTaken() {
			User newUser = new User();
			newUser.setUsername("selome");
			when(userDao.create(newUser)).thenReturn(0);
			
			assertThrows(UsernameUnavailableException.class, () -> {
				userServ.register(newUser);
			});
		}
		


}
