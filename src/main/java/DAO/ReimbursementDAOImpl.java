package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Connection.Connectioncode;
import Models.Reimbursement;

public class ReimbursementDAOImpl implements ReimbursementDAO {
	private static Connectioncode conncode = Connectioncode.getConnectionFactory();


	@Override
	public Reimbursement submitReimbursementRequest(int usernum, String description, double cost) {
		
		Reimbursement rb = new Reimbursement();
		
		String str ="PENDING";
		int num = requestNumberCheck();
		
		System.out.println(num + "" +usernum);
		
		String reimbursementsql = "INSERT INTO REIMBURSEMENT \r\n" +"VALUES(?,?,?,?,?,)";
		
		String requestsql ="SELECT *\r\n" + "WHERE REQUEST_NUMBER =?";
		
		try { Connection conn = conncode.getConnection();
				try (PreparedStatement reimbursementPStatement = conn.prepareStatement(reimbursementsql);
						 PreparedStatement reqestCheckPStatement = conn.prepareStatement(requestsql)){
					
					 reimbursementPStatement.setInt(1, num);
					 reimbursementPStatement.setInt(2, usernum);
					 reimbursementPStatement.setString(3, description);
					 reimbursementPStatement.setDouble(4, cost);
					 reimbursementPStatement.setString(5, str);
					 reimbursementPStatement.executeQuery();
					 reqestCheckPStatement.setInt(1, num);
					 ResultSet rs = reqestCheckPStatement.executeQuery();
					 
					 while(rs.next()) {
						 rb.setRequestid(rs.getInt("REQUESTID"));
							rb.setUserid(rs.getInt("USERID"));
							rb.setDescription(rs.getString("DESCRIPTION"));
							rb.setCost(rs.getDouble("COST"));
							rb.setStatus(rs.getString("STATUS"));
					 }
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return rb;
	}

	private int requestNumberCheck() {
		int requestId = 0; 
		String requestNumberSql = "SELECT MAX(REQUESTID)\r\n"
				+ "FROM REIMBURSEMENT ";
		try { Connection conn = conncode.getConnection();
		try(PreparedStatement requestNumberPStatement = conn.prepareStatement(requestNumberSql)){
		   ResultSet rs =  requestNumberPStatement.executeQuery();
		   while (rs.next()) {
	        	 requestId = rs.getInt(1);
	        	 requestId = requestId + 1;
	}
		   rs.close();
		} } catch (SQLException e) {
			 e.printStackTrace();
		 }
	        return requestId;
	}

		

	@Override
	public Reimbursement AdminApprovedDenyRequest(int requestid, String status) {
		Reimbursement rb = new Reimbursement();
		
		String statusUpdatesql = "UPDATE REIMBURSEMENT\r\n"
				+ "SET STATUS =  ?\r\n"
				+ "WHERE REQUESTID = ?";
		try { Connection conn = conncode.getConnection();
		try(PreparedStatement statusUpdatePStatement = conn.prepareStatement(statusUpdatesql)){
			 statusUpdatePStatement.setString(1, status);
			 statusUpdatePStatement.setInt(2, requestid);
			 ResultSet rs = statusUpdatePStatement.executeQuery();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 
	 return rb;     

}


	@Override
	public Reimbursement adminViewAllPendingRequest(int id) {
Reimbursement rB = new Reimbursement();
	    
		String allPendingStatussql = "SELECT  *\r\n"
				+ "FROM REIMBURSEMENT \r\n"
				+ "WHERE  STATUS = 'PENDING'";

		try { Connection conn = conncode.getConnection();
			try (PreparedStatement allPendingStatusPStatement = conn.prepareStatement(allPendingStatussql)){
				
				
				ResultSet rs = allPendingStatusPStatement.executeQuery();
				while (rs.next()) {	
					rB.setRequestid(rs.getInt("REQUESTID"));
					rB.setUserid(rs.getInt("USERID"));
					rB.setDescription(rs.getString("DESCRIPTION"));
					rB.setCost(rs.getDouble("COSTREQUESTED"));
					rB.setStatus(rs.getString("STATUS"));
					
					 
					};
					
				

					
			}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		   
				return rB;
			
				       
}


	@Override
	public Reimbursement userViewPendingRequest(int id) {
		 Reimbursement rB = new Reimbursement();
		    
			//SQL SELECT statement
			String pendingReqsql = "SELECT  *\r\n"
					+ "FROM REIMBURSEMENT\r\n"
					+ "WHERE (USERID = ?)\r\n"
					+ "AND  STATUS = 'PENDING'";

			try {Connection connection = conncode.getConnection();
				try (PreparedStatement pendingReqPStatement = connection.prepareStatement(pendingReqsql)){
					
					pendingReqPStatement.setInt(1,id);
					ResultSet rs = pendingReqPStatement.executeQuery();
					while (rs.next()) {	
						rB.setRequestid(rs.getInt("REQUESTID"));
						rB.setUserid(rs.getInt("USERID"));
						rB.setDescription(rs.getString("DESCRIPTION"));
						rB.setCost(rs.getDouble("COSTREQUESTED"));
						rB.setStatus(rs.getString("STATUS"));
		
						  	  
						};
				}	
				} catch (SQLException e) {
					e.printStackTrace();
				}
			    
			  
					return rB;
					
}

	@Override
	public Reimbursement userViewResolvedRequest(int id) {
		 Reimbursement rB = new Reimbursement();
		    
			//SQL SELECT statement
			String allResolvedRequestssql = "SELECT  *\r\n"
					+ "FROM REIMBURSEMENT\r\n"
					+ "WHERE (USERID = ?)\r\n"
					+ "AND  STATUS != 'PENDING'";

			try {Connection connection = conncode.getConnection();
				try (PreparedStatement allResolvedRequestPStatement = connection.prepareStatement(allResolvedRequestssql)){
					
					allResolvedRequestPStatement.setInt(1,id);
					ResultSet rs = allResolvedRequestPStatement.executeQuery();
					while (rs.next()) {	
						rB.setRequestid(rs.getInt("REQUESTID"));
						rB.setUserid(rs.getInt("USERID"));
						rB.setDescription(rs.getString("DESCRIPTION"));
						rB.setCost(rs.getDouble("COSTREQUESTED"));
						rB.setStatus(rs.getString("STATUS"));
						
						
						
					
						  	  
						};
				}	
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
	
		

			return rB;
			
}

}