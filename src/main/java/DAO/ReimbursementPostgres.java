package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Connection.Connectioncode;
import Models.Reimbursement;
import Models.User;
import Models.Usertype;

public class ReimbursementPostgres implements ReimbursementDAO {
 private static Connectioncode conncode = Connectioncode.getConnectionFactory();

 
 @Override	
	public int create(Reimbursement newObj) {
		Connection connection = conncode.getConnection();
		
		String sql = "insert into reimbursement( requestid, userid, description, cost, statusid)" + "values(default,?,?,?,?)";
		
		try {
			
			PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		pstmt.setInt(1, newObj.getUserid());
		pstmt.setString(2, newObj.getDescription());
		pstmt.setDouble(3, newObj.getCost());
		 int statusid;
		 if(newObj.getStatus().equals("")) {
			 statusid =1;
		 }
		 else if(newObj.getStatus().equals("")) {
			 statusid=2;
		 }
		 else {
			 statusid=3;
		 }
		 pstmt.setInt(4,statusid);
		 
		 connection.setAutoCommit(false);
		 int count = pstmt.executeUpdate();
		 ResultSet resultSet =pstmt.getGeneratedKeys();
		 if (count>0) {
			 System.out.println("Reimbursement Requested");
			 resultSet.next();
			 int requestid=resultSet.getInt(1);
			 newObj.setRequestid(requestid);
			 connection.commit();
		 }
		 else {
			 System.out.println("Something went wrong when trying to make request!");
			 connection.rollback();
		 }
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				}
		} finally {
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return newObj.getRequestid();
		}

	@Override
	public Reimbursement getById(int id) {
		Reimbursement reimbursement = null;
		
		String sql ="SELECT * FROM reimbursement WHERE requestid = ?";
		
		try(Connection connection = conncode.getConnection()){
			 PreparedStatement pstmt = connection.prepareStatement(sql);
			 pstmt.setInt(1, id);
			 ResultSet resultSet = pstmt.executeQuery();
			 
			 if(resultSet.next()) {
				 reimbursement = parseResultSet(resultSet);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reimbursement;
	}

	@Override
	public List<Reimbursement> getAll() {
		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		String sql = "SELECT * FROM reimbursement";
		try(Connection connection = conncode.getConnection()){
			PreparedStatement pstmt = connection.prepareStatement(sql);
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				Reimbursement reimbursement = parseResultSet(resultSet);
				reimbursements.add(reimbursement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reimbursements;
	}
	
	private Reimbursement parseResultSet(ResultSet resultSet) throws SQLException{
		Reimbursement reimbursement = new Reimbursement();
		
		reimbursement.setRequestid(resultSet.getInt(1));
		reimbursement.setUserid(resultSet.getInt(2));
		reimbursement.setDescription(resultSet.getString(3));
		reimbursement.setCost(resultSet.getDouble(4));
		int statusid = resultSet.getInt(5);
		
		 String status = (statusid == 1) ? "" : "";
		
		reimbursement.setStatus(status);
		return reimbursement;
		
	}

	@Override
	public void update(Reimbursement updateObj) throws SQLException {
		Connection connection = conncode.getConnection();
		
		String sql = "update reimbursement set userid?, description =?, cost =?, statusid=? where requestid =?;";
		try {
	PreparedStatement pstmt = connection.prepareStatement(sql);
	
	pstmt.setInt(1, updateObj.getUserid());
	pstmt.setString(2, updateObj.getDescription());
	pstmt.setDouble(3, updateObj.getCost());
	int statusid =(updateObj.getStatus().equals("")) ? 1|2:3;
	pstmt.setInt(4, statusid);
	pstmt.setInt(5, updateObj.getRequestid());
	
	connection.setAutoCommit(false);
	int count = pstmt.executeUpdate();
	if(count != 1) {
		connection.rollback();
		throw new SQLException("ERROR: no object found to update");
	} else connection.commit();

	} catch(SQLException e) {
		e.printStackTrace();
		try {
			connection.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		throw e;
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}



	@Override
	public void delete(Reimbursement objToDelete) throws SQLException {
		Connection connection = conncode.getConnection();
		String sql = "delete from reimbursement where requestid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1,objToDelete.getRequestid());
			connection.setAutoCommit(false);
			int count = pstmt.executeUpdate();
			if(count != 1) {
				connection.rollback();
				throw new SQLException("ERROR: no object found to update");
			}else connection.commit();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    		throw e;
    	} finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}

		
	}

	@Override
	public List<Reimbursement> getByStatus(String status) {
		List<Reimbursement> reimbursements = new LinkedList<>();
		try (Connection conn = conncode.getConnection()){
			String sql = "select *from reimbursement where statusid =?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			int statusId=(status.equals("")?1|2:3);
			pStmt.setInt(1, statusId);
			
			ResultSet resultSet = pStmt.executeQuery();
			while(resultSet.next()) {
				Reimbursement reimbursement = parseResultSet(resultSet);
				reimbursements.add(reimbursement);
			}
		}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	

		return reimbursements;
	}

	@Override
	public List<Reimbursement> getByOwner(User owner) {
		List<Reimbursement> reimbursements = new LinkedList<>();
		try (Connection conn = conncode.getConnection()){
			String sql = "select * from reimbursement join manager on reimbursement.id = manager.reimbursementid" + "where manager.userid =?";
		PreparedStatement pStmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
