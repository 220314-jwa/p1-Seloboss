package DAO;

import java.util.List;

import Models.Reimbursement;
import Models.User;

public interface ReimbursementDAO extends MainDAO<Reimbursement> {
	
	public List<Reimbursement> getByStatus(String status);
	public List<Reimbursement> getByOwner(User owner);
	

}
