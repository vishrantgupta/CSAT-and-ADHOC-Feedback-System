package com.vishrant.service.iduhead;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.vishrant.bean.ActionItems;
import com.vishrant.bean.ClientFeedback;
import com.vishrant.db.DBHelper;

public class FetchActionItemsService {

	public ArrayList<ActionItems> fetchActionItems(String iduHeadId, String projectName, String questionType) {

		if ((questionType == null || iduHeadId == null || projectName == null)) {
			return null;
		}
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		
		ArrayList<ActionItems> actionItems = null;
		Connection connection = null;
		
		try {

			connection = DBHelper.getConnection();
			Statement stmt = connection.createStatement();
			
			String sql = 	"	SELECT f.feedback_id as feedback_id " +
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
							"		 , ai.action_item_mngr_txt as actionItemMgrTxt " +
							"	  FROM FEEDBACK F " +
							"	     , QUESTION Q " +
							"		 , ACTION_ITEM AI " +
							"	 WHERE upper(F.USER_NAME) in ( " +
							"	                        select distinct upper(CLIENT_ID) " + 
							"	                          from PROJECTS " +
							"	                         where upper(IDU_HEAD_ID) = upper('" + iduHeadId + "') " +
							"	                           and upper(project_name) like upper('" + projectName + "') " +
							"	                       ) " +
							"	   AND to_char(F.MONTH, 'MM/YYYY') = to_char(to_date('" + sdf.format(d) + "', 'MMDDYYYY'), 'MM/YYYY') " +
							"	   AND UPPER(F.FEEDBACK_TYPE) = UPPER('" + questionType + "') " +
							"	   AND Q.QUESTION_ID = F.QUESTION_ID " +
							"	   AND ai.QUESTION_ID = F.QUESTION_ID " + 
							"	   AND ai.feedback_type = f.feedback_type " +
							"	 ORDER BY q.question_display_order ";
			
			System.err.println("SQL is " + sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			actionItems = new ArrayList<>();
			
			while (rs.next()) {
				ActionItems actionItem = new ActionItems();
				
				actionItem.setFeedbackId(rs.getString("feedback_id"));
				actionItem.setQuestionId(rs.getInt("question_id"));
				actionItem.setFeedbackTxt(rs.getString("feedback_txt"));
				actionItem.setFeedbackType(rs.getString("feedback_type"));
				actionItem.setQuestionTxt(rs.getString("question_txt"));
				actionItem.setMonth(rs.getDate("month"));
				
				actionItem.setDisplayOrder(rs.getInt("displayOrder"));
				actionItem.setQuestionCategory(rs.getString("questionCategory"));
				actionItem.setActionItemMgrTxt(rs.getString("actionItemMgrTxt"));
				
				actionItems.add(actionItem);
			}
			
			System.err.println("questions size " + actionItems.size());
			
		} catch (Exception e) {
			actionItems = null;
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
		
		return actionItems;
	}

}
