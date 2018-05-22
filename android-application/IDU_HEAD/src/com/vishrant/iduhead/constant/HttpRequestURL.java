package com.vishrant.iduhead.constant;

public interface HttpRequestURL {

	String URL = "http://vishrant.ddns.net:8082/CustomerFeedback"; // http://vishrant.ddns.net
	// String URL = "http://49.248.203.244:8082/CustomerFeedback"; // http://vishrant.ddns.net

	String LOGIN_URL = "/login";
	String QUESTION_FETCH_URL = "/FetchQuestions";
	
	String SUBMIT_FEEDBACK_STRING = "/SubmitFeedback";
	String IS_FEEDBACK_SUBMITTED = "/IsFeedbackSubmitted";
	
	String GET_ACTION_ITEM_FEEDBACK_TYPES = "/getSubmittedActionItemFeedbackTypes";
	
	String GET_ACTION_ITEMS = "/fetchActionItems";
	String SUBMIT_CLOSURE_COMMENTS = "/submitClosureComments";
	
	
}
