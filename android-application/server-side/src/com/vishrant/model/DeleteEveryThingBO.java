package com.vishrant.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vishrant.db.DBHelper;

public class DeleteEveryThingBO {

	public String deleteEntries() {

		try {
			Connection connection = DBHelper.getConnection();

			String sql = "DELETE FROM FEEDBACK";
			PreparedStatement statementDeleteFeedback = connection.prepareStatement(sql);
			statementDeleteFeedback.executeUpdate();

			sql = "DELETE FROM ACTION_ITEM";
			
			PreparedStatement statementDeleteActionItem = connection.prepareStatement(sql);
			statementDeleteActionItem = connection.prepareStatement(sql);
			statementDeleteActionItem.executeUpdate();

			sql = "DELETE FROM SUBMITTED_FEEDBACKS";
			PreparedStatement statementDeleteSubmittedFeedback = connection.prepareStatement(sql);
			statementDeleteSubmittedFeedback.executeUpdate();

			connection.commit();

			return "Success";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
