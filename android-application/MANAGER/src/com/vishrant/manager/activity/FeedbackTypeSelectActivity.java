package com.vishrant.manager.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import com.vishrant.manager.R;
import com.vishrant.manager.constant.ApplicationGlobalVariable;
import com.vishrant.manager.utils.Utils;

public class FeedbackTypeSelectActivity extends Activity {

	private View view = null;
	private LinearLayout linearLayout = null;
	private LinearLayout linearLayoutMain = null;
	// private ScrollView scrollView = null;
	
	private String[] feedbackTypeArray = null;
	
	private String selectedProjectName = null;
	
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

	    //linearLayoutMain.setBackgroundColor(Color.rgb(221, 221, 221));

	    
	    Bundle bundle = getIntent().getExtras();
		this.selectedProjectName = bundle.getString("selectedProject");
	    
	    
	    // Create a LinearLayout element
	    linearLayout.setOrientation(LinearLayout.VERTICAL);

	    LayoutParams layoutParamsLinearLayout = new LayoutParams(
	    		LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	    
	    layoutParamsLinearLayout.setMargins(20, 200, 20, 10);
	    linearLayout.setLayoutParams(layoutParamsLinearLayout);
	    
	    // feedbackTypeRadioBtn();
	    
	    linearLayoutMain.addView(linearLayout);
	    
	    linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
	    //linearLayoutMain.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
	    
	    setContentView(view);
	    
	    CheckIfFeedbackGiven isFeedbackSubmitted = new CheckIfFeedbackGiven();
	    isFeedbackSubmitted.execute(new String[]{new String("")});
	}

	private void setMargins (View v, int l, int t, int r, int b) {
		android.widget.LinearLayout.LayoutParams layoutparams = null;
		// for RatingBar
		if (v instanceof RatingBar) {
			layoutparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(l, t, r, b);
			v.setLayoutParams(layoutparams);
		} else if (v  instanceof View) { // for views other then RatingBar
			layoutparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(l, t, r, b);
			v.setLayoutParams(layoutparams);
	    }
	}
	
	private void createSubmitBtn() {
		
		LinearLayout submitBtnLayout = new LinearLayout(this);
		
		LayoutParams layoutParams = new LayoutParams(
	    		LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
	    
		submitBtnLayout.setLayoutParams(layoutParams);
		submitBtnLayout.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
		
		submitBtnLayout.setPadding(0, 0, 0, 20);
		
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

					Bundle bundle = getIntent().getExtras();
					String selectedProject = bundle.getString("selectedProject");
					
					Intent slideactivity = new Intent(FeedbackTypeSelectActivity.this, FeedbackActivity.class);
					slideactivity.putExtra("selectedFeedback", radioSelection);
					slideactivity.putExtra("selectedProject", selectedProject);
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
		String[] values = null;
		
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

			String userId = ((ApplicationGlobalVariable) getApplication()).getUserId();
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
			
			if (values != null && values.length > 0) {
				
				int idCount = 100;
				for (String optionLabel : values) {
					RadioButton btn = new RadioButton(FeedbackTypeSelectActivity.this);
					btn.setId(++idCount);
					
					btn.setText(optionLabel);
					
					group.addView(btn);
				}
				
				// createSubmitBtn();
				
				linearLayout.addView(group);
			} else {
				
				Intent slideactivity = new Intent(FeedbackTypeSelectActivity.this, NoRecordsFoundActivity.class);
				slideactivity.putExtra("message", "No feedback type available, please come back again.");
				startActivity(slideactivity);
				finish();
				
			}
			
			if (values != null && values.length > 0) {
	        	// creating submit button
	    		createSubmitBtn();
			}
			
			
	        progressDialog.dismiss();
		}
	}
	
	private void createViewStatusBtn(String btnLabel, String feedbackType){
		
		Button submitBtn = new Button(FeedbackTypeSelectActivity.this);
		
		LayoutParams layoutParams = new LayoutParams(
	    		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		submitBtn.setLayoutParams(layoutParams);
		
		submitBtn.setText(btnLabel);
		//setMargins(submitBtn, 25, 15, 25, 40);
		
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
				String radioSelection = (String) btn.getText();
				
				/*Intent slideactivity = new Intent(FeedbackTypeSelectActivity.this, RatingScaleNoteActivity.class);
				slideactivity.putExtra("feedbackSelected", radioSelection);
				startActivity(slideactivity);
				finish();*/
				
			}
		});
		
		linearLayout.addView(submitBtn);
	}
	
}
