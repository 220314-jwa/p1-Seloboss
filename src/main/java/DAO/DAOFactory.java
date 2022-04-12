package DAO;

public class DAOFactory {
    private static ReimbursementDAO reimbursementDAO = null;
    private static LoginDAO loginDAO = null;

    private DAOFactory() { }
    public static LoginDAO getLoginDAO() {
        // make sure we're not recreating the dao if it already exists:
        if (loginDAO == null) {
            loginDAO = new LoginDAOPostgres();
        }
        return loginDAO;
    }
    public static ReimbursementDAO getReimbursementDAO() {
        if (reimbursementDAO == null)
            reimbursementDAO = new ReimbursementDAOImpl();
        return reimbursementDAO;
    }
}




