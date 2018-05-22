package com.vishrant.manager.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vishrant.manager.R;
import com.vishrant.manager.constant.ApplicationGlobalVariable;
import com.vishrant.manager.utils.Utils;

public class LoginActivity extends Activity {

	Boolean flag = false;
	String appUserDetails = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle("Login");
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_login);

		Button btnLoginSubmit = (Button) findViewById(R.id.btnLoginSubmit);
		btnLoginSubmit.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText userName = (EditText) findViewById(R.id.edtTxtUserName);
				EditText password = (EditText) findViewById(R.id.edtTxtPassword);

				BackgroundLogin checkConnectivity = new BackgroundLogin(userName.getText().toString(), password.getText().toString());
				checkConnectivity.execute(new String[]{new String("")});
				
			}
		});
		
	}

	
	private class BackgroundLogin extends AsyncTask<String, Void, Boolean> {

		ProgressDialog progressDialog;
		
		String userName;
		String password;
		
		public BackgroundLogin(String userName, String password) {
			this.userName = userName;
			this.password= password;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progressDialog = new ProgressDialog(LoginActivity.this);
	        progressDialog.setMessage("Please wait...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.setCancelable(false);
	        progressDialog.show();
			
		}

		@Override
		protected Boolean doInBackground(String... params) {
			
			try {
				Utils utils = new Utils();
				String result = utils.isValidUser("M", userName, password);
				
				 if (!result.equalsIgnoreCase("fail")) {
					 flag = true;
					 ((ApplicationGlobalVariable) getApplication()).setUserId(userName);
					 appUserDetails = result;
					 return true;
				} else {
					flag = false;
					appUserDetails = null;
					return false;
				}

			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}

			return false;
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(Boolean flag) {
			
			if (flag) {
				Intent slideactivity = new Intent(LoginActivity.this, AppUserDetailsActivity.class);
				slideactivity.putExtra("appUserDetails", appUserDetails);
				startActivity(slideactivity);
				// finish();
				flag = false;
			} else {
				Toast.makeText(getApplicationContext(), "Invalid login, please try again.", Toast.LENGTH_SHORT).show();
			}
			
	        progressDialog.dismiss();
	    }
	}
	
}
