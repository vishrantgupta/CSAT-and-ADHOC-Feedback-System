package com.vishrant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vishrant.db.DBHelper;

public class IsFeedbackSubmittedBO {

	public String isFeedbackSubmitted(String userName, String projectName) {
		
		try (Connection connection = DBHelper.getConnection()){
			
			Statement stmt = connection.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
			
			String sql = " SELECT DISTINCT FEEDBACK_TYPE " +
						 "      , STATUS " +
						 "   FROM SUBMITTED_FEEDBACKS " +
						 "  WHERE upper(USER_NAME) = upper('"	+ userName + "') " +
						 "    AND to_char(APPLICABLE_MONTH, 'MMYY') = to_char(to_date('" + sdf.format(new Date()) + "', 'MMDDYY'), 'MMYY')" +
						 "	  AND upper(FEEDBACK_TYPE) IN (SELECT DISTINCT upper(QUESTION_TYPE) FROM QUESTION)" +
						 "	  AND project_id = (SELECT DISTINCT(PROJECT_ID) FROM PROJECTS WHERE UPPER(PROJECT_NAME) LIKE UPPER('%" + projectName + "%'))";
			
			/*"SELECT * FROM SUBMITTED_FEEDBACKS WHERE "
			+ " UPPER(user_name) = UPPER('"	+ userName + "' ) AND UPPER(STATUS) = 'CONFORM'";*/
			
			System.err.println("SQL " + sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			StringBuffer sb = new StringBuffer();
			
			while (rs.next()) {
				sb.append(rs.getString("FEEDBACK_TYPE") + "~");
				sb.append(rs.getString("STATUS") + "#");
			}
			
			return sb.length() > 0 ? sb.toString() : "noRecordFound";
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "fail";
	}

}
