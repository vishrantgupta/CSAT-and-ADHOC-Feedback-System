package com.vishrant.manager.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import com.vishrant.manager.beans.ClientFeedback;
import com.vishrant.manager.beans.Question;
import com.vishrant.manager.connectivity.SimpleHttpClient;
import com.vishrant.manager.constant.HttpRequestURL;


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
	
	public String isValidUser(String userType, String userName, String password) {

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
		}
		
		return "fail";
		
	}
	
	public ArrayList<ClientFeedback> fetchClientFeedback(String managerId, String projectName, String feedbackType) {
			
		
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("managerId", managerId));
		postParameters.add(new BasicNameValuePair("projectName", projectName));
		postParameters.add(new BasicNameValuePair("questionType", feedbackType));
		
		String response = null;
		try {
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.GET_CLIENT_FEEDBACK,
					postParameters);
			String res = response.toString();

			ArrayList<ClientFeedback> questions = new ArrayList<ClientFeedback>();
			String questionsArray[] = res.trim().split("~");
			
			SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
			
			for (String question : questionsArray) {
				String ques[] = question.split("[;]");
				
				ClientFeedback questionBean = new ClientFeedback();
				
				
				questionBean.setFeedbackId(ques[0]);
				questionBean.setQuestionId(Integer.parseInt(ques[1]));
				questionBean.setFeedbackTxt(ques[2]);
				questionBean.setFeedbackType(ques[3]);
				
				questionBean.setQuestionTxt(ques[4]);
				questionBean.setMonth(format.parse(ques[5]));
				
				questionBean.setDisplayOrder(Integer.parseInt(ques[6]));
				questionBean.setQuestionCategory(ques[7]);
				
				questions.add(questionBean);
			}
			
			return questions;
			
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
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.SUBMIT_ACTION_ITEM_STRING,
					postParameters);
			String res = response.toString().trim();

			return res.equalsIgnoreCase("success") ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public String[] isFeedbackGive(String userId, String selectedProject) {
		
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userId", userId));
		postParameters.add(new BasicNameValuePair("selectedProject", selectedProject));
		
		String response = null;
		try {
			response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.GET_SUBMITTED_FEEDBACK_TYPES,
					postParameters);
			String res = response.toString().trim();

			if (!res.equalsIgnoreCase("fail") && !res.equalsIgnoreCase("noRecordFound")) {
				String[] values = res.split("~");
				return values;
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
