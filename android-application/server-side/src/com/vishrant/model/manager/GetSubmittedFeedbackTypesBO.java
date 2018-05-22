package com.vishrant.model.manager;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import com.vishrant.db.DBHelper;

public class GetSubmittedFeedbackTypesBO {

	public String getSubmittedFeedbackTypesBO(String userName, String projectName) {
		
		try (Connection connection = DBHelper.getConnection()){
			
			Statement stmt = connection.createStatement();

			SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
			
			System.err.println("userName " + userName);
			System.err.println("projectName " + projectName);
			
			String sql = /*"SELECT * FROM SUBMITTED_FEEDBACKS WHERE "
							+ " UPPER(user_name) = UPPER('"	+ userName + "' ) AND UPPER(STATUS) = 'OPEN'";*/   
			
			"SELECT DISTINCT FEEDBACK_TYPE " +
			"  FROM SUBMITTED_FEEDBACKS " +
			" WHERE UPPER(USER_NAME) in ( " +
			"                            select UPPER(CLIENT_ID) " +
			"                              from PROJECTS " +
			"                             where UPPER(MANAGER_ID) = UPPER('" + userName + "')" +
			"                           ) " +
			"	AND upper(project_id) in (" +
			"							 select project_id " +
			"							  from projects " +
			"							 where upper(project_name) like upper('%" + projectName + "%') " +
			"							 )" +
			"   AND UPPER(STATUS) = UPPER('OPEN') " +
			"   AND to_char(FEEDBACK_SUBMIT_DATE, 'MMYYYY') = to_char(to_date('" + sdf.format(new Date()) + "', 'MMDDYYYY'), 'MMYYYY') ";
			
			System.err.println("sql " + sql);
			
			/*" SELECT DISTINCT FEEDBACK_TYPE " +
			"   FROM FEEDBACK F " +
			"  WHERE APP_USER_ID in (" +
			                         " select CLIENT_ID " + 
			                         "   from PROJECTS " +
			                         "  where UPPER(MANAGER_ID) = UPPER('" + userName + "')" +
			                        ")" +
			   " AND TO_CHAR(F.MONTH) = TO_DATE(:month, 'MMDDYYYY') " +
			 " ORDER BY 1";*/
			
			ResultSet rs = stmt.executeQuery(sql);
			
			StringBuffer sb = new StringBuffer();
			
			while (rs.next()) {
				sb.append(rs.getString("FEEDBACK_TYPE") + "~");
			}
			
			return sb.length() > 0 ? sb.toString() : "noRecordFound";
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "fail";
	}
	
}
