package com.vishrant.iduhead.beans;

import java.util.Date;

public class Question {

	private Integer questionId;
	private String questionTxt;
	private String questionTypeOptions;
	private String questionTypeHint;
	private String questionType;
	private Date month;
	private String questionDisplayType;

	private Integer ratingMaxValue;
	
	private String questionCategory;
	private Integer questionDisplayOrder;
	
	public String getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}

	public Integer getQuestionDisplayOrder() {
		return questionDisplayOrder;
	}

	public void setQuestionDisplayOrder(Integer questionDisplayOrder) {
		this.questionDisplayOrder = questionDisplayOrder;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTxt() {
		return questionTxt;
	}

	public void setQuestionTxt(String questionTxt) {
		this.questionTxt = questionTxt;
	}

	public String getQuestionTypeOptions() {
		return questionTypeOptions;
	}

	public void setQuestionTypeOptions(String questionTypeOptions) {
		this.questionTypeOptions = questionTypeOptions;
	}

	public String getQuestionTypeHint() {
		return questionTypeHint;
	}

	public void setQuestionTypeHint(String questionTypeHint) {
		this.questionTypeHint = questionTypeHint;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public String getQuestionDisplayType() {
		return questionDisplayType;
	}

	public void setQuestionDisplayType(String questionDisplayType) {
		this.questionDisplayType = questionDisplayType;
	}

	public Integer getRatingMaxValue() {
		return ratingMaxValue;
	}

	public void setRatingMaxValue(Integer ratingMaxValue) {
		this.ratingMaxValue = ratingMaxValue;
	}

}
