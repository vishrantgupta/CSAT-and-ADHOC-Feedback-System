package com.vishrant.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.vishrant.db.DBHelper;

public class AddNewFeedbackBO {

	public Boolean addNewFeedback(String userId, String feedbackType,
			String projectName, String applicableMonth, String status) {

		Connection connection = null;
		
		try {
			connection = DBHelper.getConnection();
			
			String sql = "INSERT INTO SUBMITTED_FEEDBACKS (USER_NAME, FEEDBACK_TYPE, STATUS, PROJECT_ID, FEEDBACK_ID, APPLICABLE_MONTH) " +
					   "  VALUES ('" + userId + "', '" + feedbackType + "', '" + status + "'," + "(SELECT PROJECT_ID FROM PROJECTS WHERE PROJECT_NAME LIKE '%" + projectName + "%'), to_char(TO_DATE('" + applicableMonth + "', 'MM/DD/YY'), 'MonYY') , to_date('" + applicableMonth + "', 'MM/DD/YY'))";
					
			System.err.println("sql " + sql);
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();

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
