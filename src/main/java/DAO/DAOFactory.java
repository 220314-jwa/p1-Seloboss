package DAO;

public class DAOFactory {
    private static ReimbursementDAO reimbursementDAO = null;
    private static UserDAO userDAO = null;

    private DAOFactory() { }
    public static UserDAO getUserDAO() {
        // make sure we're not recreating the dao if it already exists:
        if (userDAO == null) {
            userDAO = new UserDAOpostgres();
        }
        return userDAO;
    }
    public static ReimbursementDAO getReimbursementDAO() {
        if (reimbursementDAO == null)
            reimbursementDAO = new ReimbursementPostgres();
        return reimbursementDAO;
    }
}




