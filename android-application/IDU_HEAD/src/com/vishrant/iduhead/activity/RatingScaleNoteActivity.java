package com.vishrant.iduhead.activity;
/*package com.vishrant.manager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vishrant.manager.R;

public class RatingScaleNoteActivity extends Activity {

	String selectedFeedback = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating_scale_note);
		
		Bundle bundle = getIntent().getExtras();
		this.selectedFeedback = bundle.getString("feedbackSelected");
		
		Button okBtn = (Button)findViewById(R.id.next);
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {


				
				//Intent slideactivity = new Intent(RatingScaleNoteActivity.this, CustomerFeedbackActivity.class);
				
				
			}
		});
		
	}
	
	public void openNoteActivity(final View view) {

		Log.v("Selected>>>> in RatingScaleActivity ", selectedFeedback);
		
		//Intent slideactivity = new Intent(RatingScaleNoteActivity.this, CopyOfCustomerFeedbackActivity.class);
		Intent slideactivity = new Intent(RatingScaleNoteActivity.this, FeedbackActivity.class);
		slideactivity.putExtra("selectedFeedback", selectedFeedback);
		startActivity(slideactivity);

		finish();
		
	}
}
*/