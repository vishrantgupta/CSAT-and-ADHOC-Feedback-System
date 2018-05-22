package com.vishrant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.vishrant.db.DBHelper;

public class LoginBO {

	public String validateLogin(String userType, String userName, String password) {
		
		try (Connection connection = DBHelper.getConnection()){
			
			Statement stmt = connection.createStatement();
			
			String sql =  " SELECT APP_USER_ID " +
						  "	   , USER_NAME " +
						  "	   , USER_TYPE " +
						  "	   , FIRST_NAME " +
						  "	   , LAST_NAME " +
						  "	   , getProjectList('" + userName + "', '" + userType + "') AS PROJECTS" +
						  "	   , DESIGNATION " +
						  "	   , EMAIL_ID " +
						  "	   , ORGANIZATION " +
						  "	   , APPLICABLE_FEEDBACKS " + 
						  "	FROM app_user " +
						  "WHERE UPPER(user_name) = UPPER('" + userName + "') " + 
						  "  AND password = '" + password + "'" + 
						  "  AND UPPER(USER_TYPE) = UPPER('" + userType + "')";
			
			
			System.err.println("login sql " + sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			boolean flag = false;
			StringBuffer sb = new StringBuffer();
			
			while (rs.next()) {
				sb.append(rs.getLong("APP_USER_ID") + "~");
				sb.append(rs.getString("USER_NAME") + "~");
				sb.append(rs.getString("USER_TYPE") + "~");
				sb.append(rs.getString("FIRST_NAME") + "~");
				sb.append(rs.getString("LAST_NAME") + "~");
				sb.append(rs.getString("PROJECTS") + "~");
				sb.append(rs.getString("DESIGNATION") + "~");
				sb.append(rs.getString("EMAIL_ID") + "~");
				sb.append(rs.getString("ORGANIZATION") + "~");
				sb.append(rs.getString("APPLICABLE_FEEDBACKS"));
				
				if (flag == true) {
					break;
				}
				flag = true;
			}
			
			return flag == true ? sb.toString() : "fail";
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "fail";
	}
}
