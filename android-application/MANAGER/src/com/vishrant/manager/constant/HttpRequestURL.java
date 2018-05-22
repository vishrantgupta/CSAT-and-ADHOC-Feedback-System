package com.vishrant.manager.constant;

public interface HttpRequestURL {

	String URL = "http://vishrant.ddns.net:8082/CustomerFeedback"; // http://vishrant.ddns.net
	// String URL = "http://49.248.203.244:8082/CustomerFeedback"; // http://vishrant.ddns.net
	
	String LOGIN_URL = "/login";
	String QUESTION_FETCH_URL = "/FetchQuestions";
	
	String SUBMIT_FEEDBACK_STRING = "/SubmitFeedback";
	String IS_FEEDBACK_SUBMITTED = "/IsFeedbackSubmitted";
	
	String GET_SUBMITTED_FEEDBACK_TYPES = "/getSubmittedFeedbackTypes";
	
	String GET_CLIENT_FEEDBACK = "/fetchClientFeedback";
	String SUBMIT_ACTION_ITEM_STRING = "/submitActionItems";
	
	
}
