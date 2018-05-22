package com.vishrant.model.iduhead;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.vishrant.bean.ActionItems;
import com.vishrant.service.iduhead.FetchActionItemsService;

public class FetchActionItemsBO {

	public String fetchActionItems(String iduHeadId, String projectName, String questionType) {

		try {
			FetchActionItemsService feedbackService = new FetchActionItemsService();
			ArrayList<ActionItems> clientFeedbacks = feedbackService.fetchActionItems(iduHeadId, projectName,	questionType);

			StringBuffer sb = new StringBuffer();

			SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
			
			for (ActionItems actionItem : clientFeedbacks) {
				sb.append(actionItem.getFeedbackId() + ";");
				sb.append(actionItem.getQuestionId() + ";");
				sb.append(actionItem.getFeedbackTxt() + ";");
				sb.append(actionItem.getFeedbackType() + ";");
				sb.append(actionItem.getQuestionTxt() + ";");
				sb.append(format.format(actionItem.getMonth()) + ";");
				sb.append(actionItem.getDisplayOrder() + ";");
				sb.append(actionItem.getQuestionCategory() + ";");
				sb.append(actionItem.getActionItemMgrTxt() + ";~");
			}

			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	
	}

}
