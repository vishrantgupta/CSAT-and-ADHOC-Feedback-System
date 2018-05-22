package com.vishrant.service.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.vishrant.bean.ClientFeedback;
import com.vishrant.db.DBHelper;

public class FetchFeedbackService {

	public ArrayList<ClientFeedback> fetchClientFeedback(String managerId, String projectName, String questionType) {

		if ((questionType == null || managerId == null || projectName == null)) {
			return null;
		}
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		
		ArrayList<ClientFeedback> clientFeedbacks = null;
		Connection connection = null;
		
		try {

			connection = DBHelper.getConnection();
			Statement stmt = connection.createStatement();
			
			String sql = 	"	SELECT distinct f.feedback_id as feedback_id " +
							"	     , f.question_id as question_id " +
							"		 ,  case " + 
							"		 		when f.STAR_RATING is not null " +
							"					then 'Rating: ' || f.STAR_RATING || chr(10) || 'Comments: ' || f.feedback_txt " +
							"		 		else f.feedback_txt " +
							"		   	end as feedback_txt " +
							"	     , f.feedback_type as feedback_type " +
							"	     , q.question_txt as question_txt " +
							"	     , f.month as month " +
							"	     , q.question_display_order as displayOrder " +
							"		 , q.question_category as questionCategory " +
							"	  FROM FEEDBACK F " +
							"	     , QUESTION Q " +
							"	 WHERE upper(F.USER_NAME) in ( " +
							"	                        select distinct upper(CLIENT_ID) " + 
							"	                          from PROJECTS " +
							"	                         where upper(MANAGER_ID) = upper('" + managerId + "') " +
							"	                           and upper(project_name) like upper('" + projectName + "') " +
							"	                       ) " +
							"	   AND to_char(F.MONTH, 'MM/YYYY') = to_char(to_date('" + sdf.format(d) + "', 'MMDDYYYY'), 'MM/YYYY') " +
							"	   AND UPPER(F.FEEDBACK_TYPE) = UPPER('" + questionType + "') " +
							"	   AND Q.QUESTION_ID = F.QUESTION_ID " +
							"	 ORDER BY q.question_display_order ";
			
			System.err.println("SQL is " + sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			clientFeedbacks = new ArrayList<>();
			
			while (rs.next()) {
				ClientFeedback clientFeedback = new ClientFeedback();
				
				clientFeedback.setFeedbackId(rs.getString("feedback_id"));
				clientFeedback.setQuestionId(rs.getInt("question_id"));
				clientFeedback.setFeedbackTxt(rs.getString("feedback_txt"));
				clientFeedback.setFeedbackType(rs.getString("feedback_type"));
				clientFeedback.setQuestionTxt(rs.getString("question_txt"));
				clientFeedback.setMonth(rs.getDate("month"));
				
				clientFeedback.setDisplayOrder(rs.getInt("displayOrder"));
				clientFeedback.setQuestionCategory(rs.getString("questionCategory"));
				clientFeedbacks.add(clientFeedback);
			}
			
			System.err.println("questions size " + clientFeedbacks.size());
			
		} catch (Exception e) {
			clientFeedbacks = null;
			e.printStackTrace();
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
		
		return clientFeedbacks;
	}

}
