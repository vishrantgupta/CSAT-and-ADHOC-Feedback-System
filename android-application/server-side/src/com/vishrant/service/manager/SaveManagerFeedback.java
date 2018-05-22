package com.vishrant.service.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.vishrant.bean.ManagerFeedback;
import com.vishrant.db.DBHelper;

public class SaveManagerFeedback {

	public synchronized boolean saveManagerFeedback(String feedbackString) {

		//feedbackId#questionId#questionType#rating#openComments#projectId#userId
		
		Connection connection = null;
		
		String userId = null;
		String feedbackType = null;
		Date submitDate = null;
		String projectName = null;
		String feedbackId = null;
		
		try {
		
			String[] feedbackArray = feedbackString.trim().split("~");
			
			ArrayList<ManagerFeedback> actionItemList = new ArrayList<ManagerFeedback>();
			
			for (String feedbacks : feedbackArray) {
				String[] feedback = feedbacks.split("#");
				
				// Nov14#3#CSAT#11162014#ac1#open~
				
				feedbackId = feedback[0];
				Integer questionId = Integer.parseInt(feedback[1]);
				feedbackType = feedback[2];
				String month = feedback[3];
				String actionItem = feedback[4];
				String isClosed = feedback[5];
				
				projectName = feedback[6];
				userId = feedback[7];
				
				System.err.println("feedbackType " + feedbackType + " user id "
						+ userId + " feedbackid " + feedbackId + " questionId "
						+ questionId + " actionItem " + actionItem
						+ " month " + month + " projectName " + projectName);
				
				ManagerFeedback mf = new ManagerFeedback();
				
				mf.setFeedbackId(feedbackId);
				mf.setQuestionId(questionId);
				mf.setFeedbackType(feedbackType);
				mf.setMonth(month);
				mf.setActionItem(actionItem);
				mf.setIsClosed(isClosed);
				mf.setProjectName(projectName);
				mf.setUserId(userId);
				
				actionItemList.add(mf);
				
			}

			connection = DBHelper.getConnection();
			
			String sql = "INSERT INTO ACTION_ITEM (ACTION_ITEM_ID, QUESTION_ID, ACTION_ITEM_MNGR_TXT, ACTION_ITEM_STATUS, MONTH, FEEDBACK_ID, USER_NAME, FEEDBACK_TYPE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			// dateFormat.applyPattern("MMddyyyy");
			
			PreparedStatement statement = connection.prepareStatement(sql);
		
			// boolean resultFlag = true;
			
			int count = 0;
			
			for (ManagerFeedback actionItem : actionItemList) {
				
				System.err.println("feedbackId " + actionItem.getFeedbackId() + " questionId " + actionItem.getQuestionId()
						+ " ActionItem " + actionItem.getActionItem() + " feedbackType " + actionItem.getFeedbackType()
						+ " user id " + actionItem.getUserId()
						);
				
				statement.setString(1, actionItem.getFeedbackId()); // action_item_id
				statement.setInt(2, actionItem.getQuestionId());
				statement.setString(3, actionItem.getActionItem());
				statement.setString(4, actionItem.getIsClosed());
				statement.setDate(5, new java.sql.Date(new Date().getTime()));
				
				statement.setString(6, actionItem.getFeedbackId());
				statement.setString(7, actionItem.getUserId());
				statement.setString(8, actionItem.getFeedbackType());
				
				// int result = statement.executeUpdate();
				
				statement.addBatch();
				
				/*if (resultFlag != false && result < 1) {
					resultFlag = false;
					break;
				}*/
				
			}
			
			int[] updateCount = statement.executeBatch();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
			
			sql = "UPDATE submitted_feedbacks SET status = 'CLOSE' " +
				   "WHERE upper(feedback_type) = upper('" + feedbackType + "') " +
					 "AND upper(user_name) = (select DISTINCT UPPER(CLIENT_ID) from PROJECTS where UPPER(MANAGER_ID) = UPPER('" + userId + "')) " +
					 "AND project_id = (select project_id from projects where upper(project_name) like upper('%" + projectName + "%')) " +
					 "AND upper(feedback_id) = upper('" + feedbackId + "')";
					
					/*"UPDATE submitted_feedbacks (user_name, feedback_type, feedback_submit_date, status, project_id, feedback_id) " +
					"VALUES ('" + userId + "', '" + feedbackType + "', to_date('" + sdf.format(new Date()) + "', 'MM-DD-yy') , 'OPEN' , " + projectName + " , '" + feedbackId + "') ";*/

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
