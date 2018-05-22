package com.vishrant.manager.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.vishrant.manager.R;
import com.vishrant.manager.tabActivity.FetchQuestionsActivity;

public class FeedbackActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_feedback_main);

		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		TabSpec tab1 = tabHost.newTabSpec("First Tab");

		Bundle bundle = getIntent().getExtras();
		String selectedFeedback = bundle.getString("selectedFeedback");
		String selectedProject = bundle.getString("selectedProject");
		
		Intent csatIntent = new Intent(FeedbackActivity.this, FetchQuestionsActivity.class);
		csatIntent.putExtra("selectedFeedback", selectedFeedback);
		csatIntent.putExtra("selectedProject", selectedProject);
		
		tab1.setIndicator(selectedFeedback);
		tab1.setContent(csatIntent);
		tabHost.addTab(tab1);
		
	}
}