package com.vishrant.model.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.vishrant.bean.ClientFeedback;
import com.vishrant.service.manager.FetchFeedbackService;

public class FetchClientFeedbackBO {

	public String fetchClientFeedback(String managerId, String projectName, String questionType) {

		try {
			FetchFeedbackService feedbackService = new FetchFeedbackService();
			ArrayList<ClientFeedback> clientFeedbacks = feedbackService.fetchClientFeedback(managerId, projectName,	questionType);

			StringBuffer sb = new StringBuffer();

			SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
			
			for (ClientFeedback clientFeedback : clientFeedbacks) {
				sb.append(clientFeedback.getFeedbackId() + ";");
				sb.append(clientFeedback.getQuestionId() + ";");
				sb.append(clientFeedback.getFeedbackTxt() + ";");
				sb.append(clientFeedback.getFeedbackType() + ";");
				sb.append(clientFeedback.getQuestionTxt() + ";");
				sb.append(format.format(clientFeedback.getMonth()) + ";");
				sb.append(clientFeedback.getDisplayOrder() + ";");
				sb.append(clientFeedback.getQuestionCategory() + ";~");
			}

			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	
	}

}
