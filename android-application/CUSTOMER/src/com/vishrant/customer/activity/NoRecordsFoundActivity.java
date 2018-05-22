package com.vishrant.customer.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.vishrant.customer.R;

public class NoRecordsFoundActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_no_records_found);
		
		Bundle bundle = getIntent().getExtras();
		String message = bundle.getString("message");

		TextView tv = (TextView) findViewById(R.id.noRecordsFoundTxtView);
		tv.setTypeface(null, Typeface.BOLD);
		tv.setTextColor(Color.RED);

		tv.setText(message);

		
	}

}
