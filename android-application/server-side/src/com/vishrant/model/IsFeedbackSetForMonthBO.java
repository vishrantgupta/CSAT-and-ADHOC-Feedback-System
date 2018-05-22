package com.vishrant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.vishrant.db.DBHelper;

public class IsFeedbackSetForMonthBO {

	public Boolean isFeedbackSetForMonth(String userName, String applicableMonth, String status) {
		
		try (Connection connection = DBHelper.getConnection()){
			
			Statement stmt = connection.createStatement();
			
			String sql = "SELECT * FROM submitted_feedbacks " +
						 " WHERE upper(user_name) = upper('" + userName + "') " +
						 " AND upper(status) = upper('" + status + "')" +
						 " AND to_char(APPLICABLE_MONTH, 'MM/DD/YY') = TO_CHAR(TO_DATE(" + applicableMonth + ", 'MMDDYY'), MM/DD/YY)";
			
			System.err.println("set for month sql " + sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			int count = 0;
			while (rs.next()) {
				count++;
			}
			
			System.err.println("count " + count);
			
			return count > 0 ? true : false;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
