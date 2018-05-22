package com.vishrant.iduhead.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.vishrant.iduhead.utils.Utils;
import com.vishrant.iduhead.R;

public class SplashScreenActivity extends Activity {

	private Utils utils = new Utils();
	
	Boolean flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash);

		CheckConnectivity checkConnectivity = new CheckConnectivity();
		checkConnectivity.execute(new String[]{new String("")});
		
	}


	private class CheckConnectivity extends AsyncTask<String, Void, Boolean> {

		ProgressDialog progressDialog;
		
		public CheckConnectivity() {
			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progressDialog = new ProgressDialog(SplashScreenActivity.this);
	        progressDialog.setMessage("Please wait...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.setCancelable(false);
	        progressDialog.show();
			
		}

		@Override
		protected Boolean doInBackground(String... params) {
			
			try {

				 if (utils.isMobileDataEnabled(SplashScreenActivity.this)) {
					 flag = true;
				} else {
					flag = false;
					return false;
				}

			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}

			return true;
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(Boolean flag) {
	        
			if (flag) {
				Intent slideactivity = new Intent(SplashScreenActivity.this,LoginActivity.class);
				startActivity(slideactivity);

				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Please enable mobile data, and start again", Toast.LENGTH_SHORT).show();
			}
			
			progressDialog.dismiss();
	    }
	}

	
}
