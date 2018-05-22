package com.vishrant.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.vishrant.bean.FeedbackStatus;
import com.vishrant.db.DBHelper;

public class FetchFeedbackStatusService {

	public ArrayList<FeedbackStatus> fetchFeedbackStatus(String userId, String feedbackType, String month) {

		ArrayList<FeedbackStatus> questions = null;
		Connection connection = null;
		
		try {

			connection = DBHelper.getConnection();
			Statement stmt = connection.createStatement();
			
			String sql = "SELECT f.feedback_id as feedback_id " +
						 "     , f.question_id as question_id " +
						 "     ,  case " +
						 "           when f.STAR_RATING is not null " + 
						 "             then 'Rating: ' || f.STAR_RATING || chr(10) || 'Comments: ' || f.feedback_txt " + 
						 "           else f.feedback_txt  " +
						 "        end as feedback_txt  " +
						 "     , f.feedback_type as feedback_type " + 
						 "     , q.question_txt as question_txt  " +
						 "     , f.month as month  " +
						 "     , q.question_display_order as displayOrder " + 
						 "     , q.question_category as questionCategory  " +
						 "     , ai.action_item_mngr_txt as action_item_mngr_txt " +
						 "	   , sf.project_id " +
						 " FROM FEEDBACK F  " +
						 "    , QUESTION Q  " +
						 "    , ACTION_ITEM AI  " +
						 "    , SUBMITTED_FEEDBACKS sf " +
						" WHERE upper(F.USER_NAME) LIKE upper('" + userId + "')" +
						 "  AND to_char(F.MONTH, 'MM/YYYY') = to_char(to_date('" + month + "', 'MMDDYYYY'), 'MM/YYYY') " + 
						 "  AND UPPER(F.FEEDBACK_TYPE) = UPPER('" + feedbackType + "')  " +
						 "  AND upper(sf.status) = upper('CONFORM') " +
						 "  AND F.QUESTION_ID = Q.QUESTION_ID " + 
						 "  AND F.QUESTION_ID = AI.QUESTION_ID " +
						 "  AND F.FEEDBACK_ID = SF.FEEDBACK_ID " +
						 "  AND F.FEEDBACK_TYPE = SF.FEEDBACK_TYPE " +
						" ORDER BY q.question_display_order";
			
			System.err.println("Check feedback status is " + sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			questions = new ArrayList<>();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
			
			while (rs.next()) {
				FeedbackStatus question = new FeedbackStatus();
				
				question.setFeedback_id(rs.getString("feedback_id"));
				question.setQuestion_id("" + rs.getInt("QUESTION_ID"));
				question.setFeedback_txt(rs.getString("feedback_txt"));
				question.setFeedback_type(rs.getString("feedback_type"));
				question.setQuestion_txt(rs.getString("question_txt"));
				question.setMonth(sdf.format(rs.getDate("month")));
				question.setDisplayOrder(rs.getString("displayOrder"));
				question.setQuestionCategory(rs.getString("questionCategory"));
				question.setAction_item_mngr_txt(rs.getString("action_item_mngr_txt"));
				question.setProjectId(rs.getInt("project_id"));
				
				questions.add(question);
			}
			
			System.err.println("questions size " + questions.size());
			
		} catch (Exception e) {
			questions = null;
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
		
		return questions;
	}

	/*private boolean isValidQuestionType(String questionType) {

		return (questionType.equalsIgnoreCase("CSAT")
				|| questionType.equalsIgnoreCase("ADHOC")) ? true : false;

	}*/
}
