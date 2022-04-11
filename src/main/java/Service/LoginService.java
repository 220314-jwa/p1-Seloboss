package Service;

import DAO.LoginDAOPostgres;
import Models.Login;

public class LoginService {
      LoginDAOPostgres log = new LoginDAOPostgres();
      
      public Login  findUserByUsernameAndPassword(String username, String password) {
    	  Login user = log.logincheck(username, password);
    	  
    	  if(user!=null) {
    		  if(user.getUsername()!=null && user.getUsername().equals(username)) {
    			  if(user.getPassword()!=null && user.getPassword().equals(password)) {
    				  return user;
    			  }
    		  }
    	  }
    	  return null;
      }

public Login findUserById(int id) {
	
	Login user = log.getemployeeNumberById(id);
	if (user != null && user.getUsernum()==id) {
		
		return user;		
	}
	return null;
	
}
 



}
