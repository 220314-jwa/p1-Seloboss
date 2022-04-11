package Service;

import DAO.ReimbursementDAOImpl;
import Models.Reimbursement;

public class adminapprovedenyservice {
	 ReimbursementDAOImpl rDeo = new ReimbursementDAOImpl();
		
		public Reimbursement submitApproveDenyRequest(int requestid, String status) {
			
			
			Reimbursement rb = rDeo.AdminApprovedDenyRequest(requestid, status);
			
		      return rb;
			
		}


}
