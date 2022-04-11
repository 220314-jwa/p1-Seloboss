package Service;

import DAO.ReimbursementDAOImpl;
import Models.Reimbursement;

public class userviewPending {
private ReimbursementDAOImpl userview = new ReimbursementDAOImpl();
	
	
	
	public Reimbursement employeeViewPending(int id) {
		
		Reimbursement rb = userview.userViewPendingRequest(id);
		
			      return rb;
	}
	

}
