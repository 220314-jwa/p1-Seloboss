package Service;

import DAO.ReimbursementDAOImpl;
import Models.Reimbursement;

public class adminviewallpendingrequestsv {
private ReimbursementDAOImpl rRs = new ReimbursementDAOImpl();
	
	// finds the user by the token id 
		public Reimbursement managerViewAllPendingRequest(int id) {
			
			Reimbursement allPending = rRs.adminViewAllPendingRequest(id);
			
			if (allPending != null) {
				
				return allPending;		
			}
			return null;
	
	
	
		}


}
