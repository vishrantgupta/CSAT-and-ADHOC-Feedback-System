package com.vishrant.customer.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import com.vishrant.customer.beans.ApplicableFeedbacks;
import com.vishrant.customer.beans.Question;
import com.vishrant.customer.connectivity.SimpleHttpClient;
import com.vishrant.customer.constant.HttpRequestURL;


public class Utils {
	
	public Boolean isMobileDataEnabled(Activity activity) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Boolean isMobileDataEnabled = false;
	    ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class cmClass = Class.forName(cm.getClass().getName());
        Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
        method.setAccessible(true);
        isMobileDataEnabled = (Boolean)method.invoke(cm);

        // TODO check for wifi and mobile data.
	    // return isMobileDataEnabled;
        return true;
		
	}
	
	public String isValidUser(String userType, String userName, String password) throws Exception {

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userName", userName));
		postParameters.add(new BasicNameValuePair("password", password));
		postParameters.add(new BasicNameValuePair("userType", userType));
		
		String response = null;
		try {
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.LOGIN_URL,
					postParameters);
			String res = response.toString().trim();
			//String resp = res.replaceAll("\\s+", "");

			if (!res.equalsIgnoreCase("fail")) {
				return res;
			} else {
				return "fail";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		// return "fail";
		
	}
	
	public ArrayList<Question> fetchQuestion(String type, String date) {
			
		
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("questionType", type));
		postParameters.add(new BasicNameValuePair("date", date));
		
		String response = null;
		try {
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.QUESTION_FETCH_URL,
					postParameters);
			String res = response.toString();

			ArrayList<Question> questions = new ArrayList<Question>();
			String questionsArray[] = res.trim().split("~");
			
			SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
			
			for (String question : questionsArray) {
				String ques[] = question.split("[;]");
				
				Question questionBean = new Question();
				
				questionBean.setQuestionId(Integer.parseInt(ques[0]));
				questionBean.setQuestionTxt(ques[1]);
				questionBean.setQuestionTypeOptions(ques[2]);
				questionBean.setQuestionTypeHint(ques[3]);
				questionBean.setQuestionType(ques[4]);
				questionBean.setMonth(format.parse(ques[5]));
				questionBean.setQuestionDisplayType(ques[6]);
				questionBean.setRatingMaxValue(Integer.parseInt(ques[7]));
				questionBean.setQuestionCategory(ques[8]);
				questionBean.setQuestionDisplayOrder(Integer.parseInt(ques[9]));
				
				questions.add(questionBean);
			}
			
			return questions;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Boolean submitFeedback(String feedbackString) {
			
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("feedbackString", feedbackString));
		
		String response = null;
		try {
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.SUBMIT_FEEDBACK_STRING,
					postParameters);
			String res = response.toString().trim();

			return res.equalsIgnoreCase("success") ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public ArrayList<ApplicableFeedbacks> isFeedbackGive(String userId, String selectedProjectName) {
		
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userId", userId));
		postParameters.add(new BasicNameValuePair("projectName", selectedProjectName));
		
		String response = null;
		try {
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.IS_FEEDBACK_SUBMITTED,
					postParameters);
			String res = response.toString().trim();

			if (!res.equalsIgnoreCase("fail") && !res.equalsIgnoreCase("noRecordFound")) {
				String[] values = res.trim().split("#");
				
				ArrayList<ApplicableFeedbacks> feedbackList = new ArrayList<ApplicableFeedbacks>();
				
				if (values != null && values.length > 0) {
					for (String str : values) {
						String[] val = str.trim().split("~");
						
						ApplicableFeedbacks feedbackType = new ApplicableFeedbacks();
						
						feedbackType.setFeedbackType(val[0]);
						feedbackType.setStatus(val[1]);
						
						feedbackList.add(feedbackType);
					}
					return feedbackList;
				}
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getFeedbackStatus(String userId, String feedbackType, String month) {
		
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userId", userId));
		postParameters.add(new BasicNameValuePair("feedbackType", feedbackType));
		postParameters.add(new BasicNameValuePair("month", month));
		
		String response = null;
		try {
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.GET_FEEDBACK_STATUS,
					postParameters);
			String res = response.toString().trim();

			if (!res.equalsIgnoreCase("fail") && !res.equalsIgnoreCase("noRecordFound")) {
				//String[] values = res.split("~");
				return res;
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Boolean submitActionItem(String feedbackString) {
		
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("actionItemString", feedbackString));
		
		String response = null;
		try {
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.APPROVE_REJECT_FEEBACK,
					postParameters);
			String res = response.toString().trim();

			return res.equalsIgnoreCase("success") ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
