package com.vishrant.model;

import java.util.ArrayList;

import com.vishrant.bean.FeedbackStatus;
import com.vishrant.service.FetchFeedbackStatusService;

public class FetchFeedbackStatusBO {

	public String getFeedbackStatus(String userId, String feedbackType, String month) {

		try {
			FetchFeedbackStatusService questionsService = new FetchFeedbackStatusService();
			ArrayList<FeedbackStatus> questions = questionsService.fetchFeedbackStatus(userId, feedbackType, month);

			StringBuffer sb = new StringBuffer();

			for (FeedbackStatus question : questions) {

				sb.append(question.getQuestion_id() + "~");
				sb.append(question.getFeedback_txt() + "~");
				sb.append(question.getFeedback_type() + "~");
				sb.append(question.getQuestion_txt() + "~");
				sb.append(question.getMonth() + "~");
				sb.append(question.getDisplayOrder() + "~");
				sb.append(question.getQuestionCategory() + "~");
				sb.append(question.getAction_item_mngr_txt() + "~");
				sb.append(question.getFeedback_id() + "~");
				sb.append(question.getProjectId() + "#");
			}

			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
