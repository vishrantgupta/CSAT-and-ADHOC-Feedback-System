package com.vishrant.customer.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishrant.customer.R;
import com.vishrant.customer.beans.ApplicableFeedbacks;
import com.vishrant.customer.beans.Question;
import com.vishrant.customer.constant.ApplicationGlobalVariable;
import com.vishrant.customer.tabActivity.FetchQuestionsActivity;
import com.vishrant.customer.utils.Utils;

public class FeedbackTypeSelectActivity extends Activity {

	private View view = null;
	private LinearLayout linearLayout = null;
	private LinearLayout linearLayoutMain = null;
	// private ScrollView scrollView = null;
	
	private String selectedProjectName = null;
	
	String userId = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		linearLayout = new LinearLayout(this);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	    view = inflater.inflate(R.layout.activity_feedback_type_select, null);

	    // Find the ScrollView
	    linearLayoutMain = (LinearLayout) view.findViewById(R.id.scrollViewFeedbackSelect);

	    Bundle bundle = getIntent().getExtras();
		this.selectedProjectName = bundle.getString("selectedProject");
	    
	    //linearLayoutMain.setBackgroundColor(Color.rgb(221, 221, 221));

	    // Create a LinearLayout element
	    linearLayout.setOrientation(LinearLayout.VERTICAL);

	    LayoutParams layoutParamsLinearLayout = new LayoutParams(
	    		LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	    
	    layoutParamsLinearLayout.setMargins(20, 50, 20, 10);
	    linearLayout.setLayoutParams(layoutParamsLinearLayout);
	    
	    linearLayoutMain.addView(linearLayout);
	    
	    linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
	    //linearLayoutMain.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
	    
	    setContentView(view);
	    
	    CheckIfFeedbackGiven isFeedbackSubmitted = new CheckIfFeedbackGiven();
	    isFeedbackSubmitted.execute(new String[]{new String("")});
	}

	private void createStatusLabel(String messgae) {
		
		TextView statusMessage = new TextView(FeedbackTypeSelectActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		statusMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		statusMessage.setTypeface(null, Typeface.BOLD);
		
		
		LayoutParams layoutParams = new LayoutParams(
	    		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		statusMessage.setLayoutParams(layoutParams);
		statusMessage.setGravity(Gravity.CENTER_HORIZONTAL);
		
		statusMessage.setText(messgae);
		setMargins(statusMessage, 25, 10, 25, 10);
		
	    linearLayout.addView(statusMessage);
		
	}
	
	private void setMargins (View v, int left, int top, int right, int bottom) {
		android.widget.LinearLayout.LayoutParams layoutparams = null;
		// for RatingBar
		if (v instanceof RatingBar) {
			layoutparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(left, top, right, bottom);
			v.setLayoutParams(layoutparams);
		} else if (v  instanceof View) { // for views other then RatingBar
			layoutparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(left, top, right, bottom);
			v.setLayoutParams(layoutparams);
	    }
	}
	
	private void createSubmitBtn() {
		
		LinearLayout submitBtnLayout = new LinearLayout(this);
		
		LayoutParams layoutParams = new LayoutParams(
	    		LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	    
		submitBtnLayout.setLayoutParams(layoutParams);
		submitBtnLayout.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
		
		submitBtnLayout.setPadding(0, 0, 10, 10);
		
		Button submitBtn = new Button(FeedbackTypeSelectActivity.this);
		submitBtn.setText("Continue");
		
		submitBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background));
		submitBtn.setTextColor(Color.WHITE);
		
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				RadioGroup radioGroup = (RadioGroup) findViewById(1);
				int id = radioGroup.getCheckedRadioButtonId();
				View radioButton = radioGroup.findViewById(id);
				int radioId = radioGroup.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
				
				if (btn != null) {
					String radioSelection = (String) btn.getText();
					
					Intent slideactivity = new Intent(FeedbackTypeSelectActivity.this, RatingScaleNoteActivity.class);
					slideactivity.putExtra("feedbackSelected", radioSelection);
					slideactivity.putExtra("selectedProjectName", selectedProjectName);
					
					startActivity(slideactivity);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Please select feedback type", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
		submitBtnLayout.addView(submitBtn);
		linearLayout.addView(submitBtnLayout);
	}
	
	private class CheckIfFeedbackGiven extends AsyncTask<String, Void, Void> {

		ProgressDialog progressDialog;
		ArrayList<ApplicableFeedbacks> values = null;
		
		public CheckIfFeedbackGiven() {
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progressDialog = new ProgressDialog(FeedbackTypeSelectActivity.this);
	        progressDialog.setMessage("Please wait...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.setCancelable(false);
	        progressDialog.show();
			
		}

		@Override
		protected Void doInBackground(String... params) {
			Utils utils = new Utils();

			userId = ((ApplicationGlobalVariable) getApplication()).getUserId();
			values = utils.isFeedbackGive(userId, selectedProjectName);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {

			RadioGroup group = new RadioGroup(FeedbackTypeSelectActivity.this);
			group.setOrientation(RadioGroup.HORIZONTAL);
			
			// random id
			group.setId(1);
			
			group.setGravity(Gravity.CENTER);
			setMargins(group, 35, 50, 35, 10);
			
			int btnDisabledCount = 0;
			ArrayList<RadioButton> listOfRadioButtons = new ArrayList<RadioButton>();
			
			int idCount = 100;
			
			if (values != null) {
				
				dateFormat.applyPattern("MMM - yyyy");
				String monthYear = dateFormat.format(new Date());
				
				TextView monthTv = new TextView(FeedbackTypeSelectActivity.this);
				
				//csatMonth.setPadding(25, 20, 20, 15);
				monthTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				monthTv.setTypeface(null, Typeface.BOLD);
				
				setMargins(monthTv, 25, 20, 25, 15);
				
				monthTv.setText(monthYear.toUpperCase());
				monthTv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				
				linearLayout.addView(monthTv);
				
				for (ApplicableFeedbacks feedbackType : values) {

					RadioButton btn = new RadioButton(
							FeedbackTypeSelectActivity.this);
					btn.setId(++idCount);

					String type = feedbackType.getFeedbackType().trim();

					btn.setText(type);
					String status = feedbackType.getStatus();

					if (status != null && !status.equalsIgnoreCase("null")) {
						if (status.equalsIgnoreCase("new")) {
							/*btnDisabledCount++;
							btn.setEnabled(false);*/
						} else if (status.equalsIgnoreCase("conform")
								|| status.equalsIgnoreCase("resolved")) {
							btn.setEnabled(false);
							// btn.setVisibility(View.INVISIBLE);
							btnDisabledCount++;
							createViewStatusBtn("Check " + type + " status", type, status);
						} else /*if (status.equalsIgnoreCase("open")) {
								btn.setEnabled(false);
								btnDisabledCount++;
								}  else if (status.equalsIgnoreCase("close")) {*/
						{
							btn.setEnabled(false);
							// btn.setVisibility(View.INVISIBLE);
							btnDisabledCount++;
						}

					}

					group.addView(btn);

					listOfRadioButtons.add(btn);
				}
			} else {
				Intent slideactivity = new Intent(FeedbackTypeSelectActivity.this, NoRecordsFoundActivity.class);
				slideactivity.putExtra("message", "No feedback type available, please come back again.");
				startActivity(slideactivity);
				finish();
			}
			
			linearLayout.addView(group);
			
	        if (listOfRadioButtons.size() > btnDisabledCount) {
	        	// creating submit button
	    		createSubmitBtn();
			}
	        
	        progressDialog.dismiss();
		}
	}
	
	private void createViewStatusBtn(String btnLabel, final String feedbackType, String status){
		
		if (status.equalsIgnoreCase("resolved")) {
			
			createStatusLabel(feedbackType + " is resolved.");
			
		} else {
			
			Button submitBtn = new Button(FeedbackTypeSelectActivity.this);
			
			LayoutParams layoutParams = new LayoutParams(
		    		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			submitBtn.setLayoutParams(layoutParams);
			
			submitBtn.setText(btnLabel);
			setMargins(submitBtn, 25, 10, 25, 10);
			
			submitBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background));
			submitBtn.setTextColor(Color.WHITE);
			
			submitBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					/*RadioGroup radioGroup = (RadioGroup) findViewById(1);
					int id = radioGroup.getCheckedRadioButtonId();
					
					View radioButton = radioGroup.findViewById(id);
					int radioId = radioGroup.indexOfChild(radioButton);
					
					RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
					String radioSelection = (String) btn.getText();*/
					
					GetFeedbackStatus feedbackStatus = new GetFeedbackStatus(feedbackType);
				    feedbackStatus.execute(new String[]{new String("")});
					
					/*Intent slideactivity = new Intent(FeedbackTypeSelectActivity.this, RatingScaleNoteActivity.class);
					slideactivity.putExtra("feedbackSelected", radioSelection);
					startActivity(slideactivity);
					finish();*/
					
				}
			});
			
			linearLayout.addView(submitBtn);
		}
		
	}
	
	private class GetFeedbackStatus extends AsyncTask<String, Void, Void> {

		ProgressDialog progressDialog;
		String feedbackStatusStr = null;
		String feedbackType = null;
		
		public GetFeedbackStatus(String feedbackType) {
			this.feedbackType = feedbackType;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progressDialog = new ProgressDialog(FeedbackTypeSelectActivity.this);
	        progressDialog.setMessage("Please wait...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.setCancelable(false);
	        progressDialog.show();
			
		}

		@Override
		protected Void doInBackground(String... params) {
			Utils utils = new Utils();

			String userId = ((ApplicationGlobalVariable) getApplication()).getUserId();

			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
			
			feedbackStatusStr = utils.getFeedbackStatus(userId, feedbackType, sdf.format(d));
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {

			if (feedbackStatusStr != null) {
				
				Intent slideactivity = new Intent(FeedbackTypeSelectActivity.this, FeedbackStatusActivity.class);
				slideactivity.putExtra("feedbackStatus", feedbackStatusStr);
				slideactivity.putExtra("selectedProjectName", selectedProjectName);
				startActivity(slideactivity);
				//finish();
			}
			
			/*for (String str : feedbackStatusStr) {
				Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
			}*/
			
	        progressDialog.dismiss();
		}
	}
	
	
}
