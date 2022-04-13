package Services;

import DAO.DAOFactory;
import DAO.ReimbursementDAO;
import DAO.UserDAO;
import Exceptions.AlreadySubmittedException;
import Exceptions.IncorrectInfoException;
import Exceptions.UsernameUnavailableException;
import Models.Reimbursement;
import Models.User;

public class UserServicesImpl implements UserServices {
	
	private UserDAO userDao = DAOFactory.getUserDAO();
	private ReimbursementDAO reimbursementDao = DAOFactory.getReimbursementDAO();

	@Override
	public User login(String username, String password) throws IncorrectInfoException {
		User user = userDao.getByUsername(username);
		if(user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			throw new IncorrectInfoException();
		}
	}

	@Override
	public User register(User newUser) throws UsernameUnavailableException {
		int id = userDao.create(newUser);
		if(id != 0) {
			newUser.setId(id);
			return newUser;
		} else {
			throw new UsernameUnavailableException();
		}
	}
	
	@Override
	public Reimbursement submitReimbursement(User user, Reimbursement reimbursementsubmitted)
			throws AlreadySubmittedException, Exception {
		int requestid = reimbursementDao.create(reimbursementsubmitted);
		if(requestid !=0) {
			reimbursementsubmitted.setRequestid(requestid);
			return reimbursementsubmitted;
		} else {
			throw new AlreadySubmittedException();
		}
	}

	@Override
	public User getUserById(int id) {
	return userDao.getById(id);
	}

}
