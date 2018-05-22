package com.vishrant.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.vishrant.bean.Question;
import com.vishrant.db.DBHelper;

public class FetchQuestionsService {

	public ArrayList<Question> fetchQuestions(String questionType, String date) {

		if ((questionType == null || date == null)) {
			return null;
		}
		
		ArrayList<Question> questions = null;
		Connection connection = null;
		
		try {

			connection = DBHelper.getConnection();
			Statement stmt = connection.createStatement();
			
			String sql = /*"SELECT * " +
						 "  FROM QUESTION " +
						 " WHERE UPPER(QUESTION_TYPE) = UPPER('" + questionType + "') "
						 + " AND " + (questionType.equalsIgnoreCase("CSAT") ?  
						   " to_char(MONTH, 'MON/YYYY') = TO_CHAR(TO_DATE(UPPER('" + date.toUpperCase() + "'), 'MON'), 'MON/YYYY')" 
						 : questionType.equalsIgnoreCase("ADHOC")  
						 ? " to_char(MONTH, 'YYYY') = '" + date + "'": null)
						 + " ORDER BY QUESTION_DISPLAY_ORDER";*/

						"SELECT * " + 
						"  FROM question " + 
						" WHERE upper(question_type) = upper('" + questionType + "') " + 
						" ORDER BY question_display_ORDER";

			System.err.println("SQL is " + sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			questions = new ArrayList<>();
			
			while (rs.next()) {
				Question question = new Question();
				
				question.setQuestionId(rs.getInt("QUESTION_ID"));
				question.setQuestionTxt(rs.getString("QUESTION_TXT"));
				question.setQuestionTypeOptions(rs.getString("QUESTION_TYPE_OPTIONS"));
				question.setQuestionTypeHint(rs.getString("QUESTION_TYPE_HINT"));
				question.setQuestionType(rs.getString("QUESTION_TYPE"));
				question.setMonth(rs.getDate("MONTH"));
				question.setQuestionDisplayType(rs.getString("QUESTION_DISPLAY_TYPE"));
				question.setRatingMaxValue(rs.getInt("RATING_MAX_LENGTH"));
				
				question.setQuestionCategory(rs.getString("QUESTION_CATEGORY"));
				question.setQuestionDisplayOrder(rs.getInt("QUESTION_DISPLAY_ORDER"));
				
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
