package DAO;

import Models.Reimbursement;

public interface ReimbursementDAO {
     
	public Reimbursement submitReimbursementRequest(int usernum, String description, double amount);
	public Reimbursement submitAdminApprovedDenyRequest(int requestid, String status);
	public Reimbursement adminViewAllPendingRequest(int id);
	
	public Reimbursement userViewPendingRequest(int id);
	public Reimbursement userViewResolvedRequest(int id);
}
