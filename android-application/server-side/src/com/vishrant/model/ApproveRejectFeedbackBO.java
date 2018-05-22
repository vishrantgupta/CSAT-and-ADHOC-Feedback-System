package com.vishrant.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.vishrant.db.DBHelper;

public class ApproveRejectFeedbackBO {

	public Boolean finalCustomerComments(String feedbackString) {

		//projectId#userId#feedbackId#questionId#questionType#rating#openComments
		
		Connection connection = null;
		
		Date submitDate = null;
		// Integer projectId = null;
		
		try {
		
			// done#Approve#CSAT#vg261p#Nov14
			
			String[] feedbackArray = feedbackString.trim().split("#");
			
			String feedbackComments = feedbackArray[0];
			String status = feedbackArray[1];
			String feedbackType = feedbackArray[2];
			String userId = feedbackArray[3];
			String feedbackId = feedbackArray[4];
			Integer projectId = Integer.parseInt(feedbackArray[5]);

			connection = DBHelper.getConnection();
			
			String sql = 	"UPDATE SUBMITTED_FEEDBACKS " + 
							 "   SET STATUS = '"
							+ (status != null && status.equalsIgnoreCase("approve") 
									? "RESOLVED" : "OPEN")
							+ "', feedback_submit_date = sysdate " +
							" , client_final_comments = '" + feedbackComments + "'" +
							" WHERE PROJECT_ID = " + projectId + 
							"   AND FEEDBACK_ID = '" + feedbackId + "'" +
							"   AND FEEDBACK_TYPE = '" + feedbackType + "'" +
							"   AND upper(USER_NAME) = upper('" + userId + "')";
			
			PreparedStatement statement = connection.prepareStatement(sql);
		
			System.err.println("SQL " + sql);
			
			statement.executeUpdate();
			
			/*if (resultFlag) {*/
				connection.commit();
			/*} else {
				connection.rollback();
			}*/
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
					connection = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

}
