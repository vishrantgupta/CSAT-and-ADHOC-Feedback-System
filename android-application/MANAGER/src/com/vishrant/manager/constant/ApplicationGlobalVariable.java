package com.vishrant.manager.constant;

import android.app.Application;

public class ApplicationGlobalVariable extends Application {

	private String userId = null;
	private String applicableFeedbacks = null;

	public String getApplicableFeedbacks() {
		return applicableFeedbacks;
	}

	public void setApplicableFeedbacks(String applicableFeedbacks) {
		this.applicableFeedbacks = applicableFeedbacks;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
