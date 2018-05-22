package com.vishrant.service.iduhead;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.vishrant.db.DBHelper;

public class SaveIduHeadActionItems {

	public synchronized boolean saveClosureComments(String feedbackString) {

		//feedbackId#questionId#questionType#rating#openComments#projectId#userId
		
		Connection connection = null;
		
		String userId = null;
		String feedbackType = null;
		String projectName = null;
		String feedbackId = null;
		String iduHeadComments = null;
		String status = null;
		
		System.err.println(">>>>> feedbackString " + feedbackString);
		
		try {
		
			String[] feedbackArray = feedbackString.trim().split("#");

			iduHeadComments = feedbackArray[0];
			status = feedbackArray[1];
			feedbackType = feedbackArray[2];
			userId = feedbackArray[3];
			projectName = feedbackArray[4];
			feedbackId = feedbackArray[5];
			
			connection = DBHelper.getConnection();
		
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
			
			// comments#approval#feedbackType#userid#projectId#feedbackId
			// idu head comments#Reject#CSAT#idu001#MMM#Nov14
			
			String sql = 	" UPDATE SUBMITTED_FEEDBACKS " + 
							"  SET IDU_HEAD_COMMENTS = '" + iduHeadComments + "'" + 
							"      , STATUS = (CASE WHEN upper('" + status + "') = upper('Approve') " + 
							"                       THEN 'CONFORM' " + 
							"                       ELSE 'CLOSE' " + 
							"                   END) " + 
							"  WHERE upper(FEEDBACK_TYPE) = upper('" + feedbackType + "') " + 
							"    AND upper(user_name) = (select DISTINCT UPPER(CLIENT_ID) from PROJECTS where UPPER(IDU_HEAD_ID) = UPPER('" + userId + "')) " + 
							"    AND project_id = (select project_id from projects where upper(project_name) like upper('%" + projectName + "%')) " + 
							"    AND upper(feedback_id) = upper('" + feedbackId + "')";

			PreparedStatement statement = connection.prepareStatement(sql);
			
			System.err.println("SQL " + sql);
			
			statement = connection.prepareStatement(sql);

			int updatedRows = statement.executeUpdate();
			
			if (updatedRows == 1) {
				connection.commit();
				
				return true;
				
			} else {
				connection.rollback();
				
				return false;
			}
			
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
