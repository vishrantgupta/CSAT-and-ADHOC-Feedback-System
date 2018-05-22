package com.vishrant.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.vishrant.bean.ClientFeedback;
import com.vishrant.db.DBHelper;

public class SaveClientFeedback {

	public synchronized boolean saveClientFeedback(String feedbackString) {

		//projectId#userId#feedbackId#questionId#questionType#rating#openComments
		
		Connection connection = null;
		
		String userId = null;
		String feedbackType = null;
		Date submitDate = null;
		// Integer projectId = null;
		String projectName = null;
		String feedbackId = null;
		
		try {
		
			String[] feedbackArray = feedbackString.trim().split("~");
			
			ArrayList<ClientFeedback> feedbackList = new ArrayList<ClientFeedback>();
			
			for (String feedbacks : feedbackArray) {
				String[] feedback = feedbacks.split("#");
				
				projectName = feedback[0];
				
				feedbackType = feedback[1];
				userId = feedback[2];
				feedbackId = feedback[3];
				Integer questionId = Integer.parseInt(feedback[4]);
				String questionType = feedback[5];
				String rating = feedback[6];
				String feedbackTxt = feedback[7];
				
				System.err.println("feedbackType " + feedbackType + " user id "
						+ userId + " feedbackid " + feedbackId + " questionId "
						+ questionId + " questionType " + questionType
						+ " rating " + rating + " feedbackTxt " + feedbackTxt);
				
				ClientFeedback cf = new ClientFeedback();
				
				cf.setUserId(userId);
				cf.setFeedbackId(feedbackId);
				cf.setQuestionId(questionId);
				cf.setQuestionType(questionType);
				cf.setFeedbackType(feedbackType);
				
				if (!questionType.equalsIgnoreCase("rating")) {
					cf.setRating(0.0F);
					cf.setFeedbackTxt(/*rating + " " + */feedbackTxt);
				} else {
					cf.setRating(Float.parseFloat(rating));
					cf.setFeedbackTxt(feedbackTxt);
				}
			
				feedbackList.add(cf);
				
			}

			connection = DBHelper.getConnection();
			
			String sql = "INSERT INTO feedback (feedback_id, question_id, feedback_txt, feedback_type, month, user_name, star_rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
		
			// boolean resultFlag = true;
			
			int count = 0;
			
			for (ClientFeedback clientFeedback : feedbackList) {
				
				System.err.println("feedbackId " + clientFeedback.getFeedbackId() + " questionId " + clientFeedback.getQuestionId()
						+ " feedbackTxt " + clientFeedback.getFeedbackTxt() + " feedbackType " + clientFeedback.getFeedbackType()
						+ " user id " + clientFeedback.getUserId() + " rating " + clientFeedback.getRating()
						);
				
				statement.setString(1, clientFeedback.getFeedbackId());
				statement.setInt(2, clientFeedback.getQuestionId());
				statement.setString(3, clientFeedback.getFeedbackTxt());
				statement.setString(4, clientFeedback.getFeedbackType());
				statement.setDate(5, new java.sql.Date(new Date().getTime()));
				statement.setString(6, clientFeedback.getUserId());
				statement.setFloat(7, clientFeedback.getRating());
				
				// int result = statement.executeUpdate();
				
				statement.addBatch();
				
				/*if (resultFlag != false && result < 1) {
					resultFlag = false;
					break;
				}*/
				
			}
			
			int[] updateCount = statement.executeBatch();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
			
			sql = /*"update submitted_feedbacks (user_name, feedback_type, feedback_submit_date, status, project_id, feedback_id) " +
					"VALUES ('" + userId + "', '" + feedbackType + "', to_date('" + sdf.format(new Date()) + "', 'MM-DD-yy') , 'OPEN' , " + projectId + " , '" + feedbackId + "') ";*/

					"UPDATE SUBMITTED_FEEDBACKS " + 
					"   SET STATUS = 'OPEN', feedback_submit_date = sysdate " +
					" WHERE PROJECT_ID = (SELECT distinct PROJECT_ID FROM PROJECTS WHERE PROJECT_NAME LIKE '%" + projectName + "%') " +
					"   AND FEEDBACK_ID = '" + feedbackId + "'" +
					"   AND FEEDBACK_TYPE = '" + feedbackType + "'" +
					"   AND upper(USER_NAME) = upper('" + userId + "')";
					
			System.err.println("SQL " + sql);
			
			statement = connection.prepareStatement(sql);

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
		
		
		//vg261p#Nov141#RAITING#0.5#test1~
		//vg261p#Nov143#RAITING#1.0#test2~
		//vg261p#Nov142#RAITING#2.0#test3~
		//vg261p#Nov146#RAITING#2.0#test4~
		//vg261p#Nov144#RAITING#3.0#test5~
		//vg261p#Nov145#RAITING#3.5#test6~
		//vg261p#Nov147#RAITING#4.0#test7~
		//vg261p#Nov148#RAITING#4.0#test8~
		//vg261p#Nov149#RAITING#4.5#test9~
		
		return false;
	}

}
