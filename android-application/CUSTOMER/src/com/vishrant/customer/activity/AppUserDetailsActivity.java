package com.vishrant.customer.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.vishrant.customer.R;
import com.vishrant.customer.constant.ApplicationGlobalVariable;

public class AppUserDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_user_details);
		
		Bundle csatBundle = getIntent().getExtras();
		
		String userDetails = csatBundle.getString("appUserDetails");
		String details[] = userDetails.split("~");
		
		TextView name = (TextView) findViewById(R.id.appUserName);
		name.setText(details[3] + " " + details[4]);
		
		TextView designation = (TextView) findViewById(R.id.appUserDesignation);
		designation.setText(details[6]);
		
		TextView email = (TextView) findViewById(R.id.emailid);
		email.setText(details[7]);
		
		TextView organization = (TextView) findViewById(R.id.organization);
		organization.setText(details[8]);
		
		// setting applicable feedbacks
		((ApplicationGlobalVariable) getApplication()).setApplicableFeedbacks(details[9]);
		
		ArrayList<String> spinnerArray = new ArrayList<String>();
		for (String optionLabel : details[5].split("#")) {
			spinnerArray.add(optionLabel);
		}
	
		Spinner spinner = (Spinner) findViewById(R.id.projectList);
	    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
	    spinner.setAdapter(spinnerArrayAdapter);
	    
	    Button continueBtn = (Button) findViewById(R.id.next);
	    continueBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Spinner spinner = (Spinner) findViewById(R.id.projectList);
				String selectedProject = ((String)spinner.getSelectedItem()).replaceAll("#", " ").replaceAll("~", " ");
				
				Intent slideactivity = new Intent(AppUserDetailsActivity.this, FeedbackTypeSelectActivity.class);
				slideactivity.putExtra("selectedProject", selectedProject);
				
				startActivity(slideactivity);
				finish();
			}
		});
	    
	    
	}

}
