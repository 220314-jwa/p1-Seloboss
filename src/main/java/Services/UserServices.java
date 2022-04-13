package Services;

import java.util.List;

import Exceptions.AlreadySubmittedException;
import Exceptions.IncorrectInfoException;
import Exceptions.UsernameUnavailableException;
import Models.Reimbursement;
import Models.User;

public interface UserServices {
	public User login(String username, String password) throws IncorrectInfoException;
	public User register(User newUser) throws UsernameUnavailableException;
	public Reimbursement submitReimbursement(User user, Reimbursement reimbursementsubmitted) throws AlreadySubmittedException, Exception;
	public User getUserById(int id);

}
