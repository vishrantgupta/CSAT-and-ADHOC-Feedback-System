package com.vishrant.bean;

public class FeedbackStatus {

	String feedback_id = null;
	String question_id = null;
	String feedback_txt = null;
	String feedback_type = null;
	String question_txt = null;
	String month = null;
	String displayOrder = null;
	String questionCategory = null;
	String action_item_mngr_txt = null;

	Integer projectId = null;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getFeedback_id() {
		return feedback_id;
	}

	public void setFeedback_id(String feedback_id) {
		this.feedback_id = feedback_id;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}

	public String getFeedback_txt() {
		return feedback_txt;
	}

	public void setFeedback_txt(String feedback_txt) {
		this.feedback_txt = feedback_txt;
	}

	public String getFeedback_type() {
		return feedback_type;
	}

	public void setFeedback_type(String feedback_type) {
		this.feedback_type = feedback_type;
	}

	public String getQuestion_txt() {
		return question_txt;
	}

	public void setQuestion_txt(String question_txt) {
		this.question_txt = question_txt;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}

	public String getAction_item_mngr_txt() {
		return action_item_mngr_txt;
	}

	public void setAction_item_mngr_txt(String action_item_mngr_txt) {
		this.action_item_mngr_txt = action_item_mngr_txt;
	}

}
