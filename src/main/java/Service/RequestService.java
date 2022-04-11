package Service;

import DAO.ReimbursementDAOImpl;
import Models.Reimbursement;

public class RequestService {
ReimbursementDAOImpl requestservice = new ReimbursementDAOImpl();
	
	public Reimbursement submitReimbursemenRequest(int usernum, String description, double cost) {
		
		
		Reimbursement rb = requestservice.submitReimbursementRequest(usernum, description, cost);
		
	      return rb;
		

}
}
