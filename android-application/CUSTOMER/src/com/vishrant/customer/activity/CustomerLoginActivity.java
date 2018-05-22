package com.vishrant.customer.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vishrant.customer.R;
import com.vishrant.customer.constant.ApplicationGlobalVariable;
import com.vishrant.customer.utils.UserNameContentProvider;
import com.vishrant.customer.utils.Utils;

public class CustomerLoginActivity extends Activity {

	Boolean flag = false;
	String appUserDetails = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle("Login");
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_customer_login);

		Button btnLoginSubmit = (Button) findViewById(R.id.btnLoginSubmit);
		btnLoginSubmit.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText userName = (EditText) findViewById(R.id.edtTxtUserName);
				EditText password = (EditText) findViewById(R.id.edtTxtPassword);

				//if ((userName == null || password == null) || (userName.getText().toString().isEmpty() || password.getText().toString().isEmpty())) {
					BackgroundLogin checkConnectivity = new BackgroundLogin(userName.getText().toString(), password.getText().toString());
					checkConnectivity.execute(new String[]{new String("")});
				/*} else {
					Toast.makeText(getApplicationContext(), "Please enter username and password.", Toast.LENGTH_LONG).show();
				}*/
				
			}
		});
		
	}

	
	private class BackgroundLogin extends AsyncTask<String, Void, Boolean> {

		ProgressDialog progressDialog;
		
		String userName;
		String password;
		
		String errorMsg = null;
		
		public BackgroundLogin(String userName, String password) {
			this.userName = userName;
			this.password= password;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progressDialog = new ProgressDialog(CustomerLoginActivity.this);
	        progressDialog.setMessage("Please wait...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.setCancelable(false);
	        progressDialog.show();
			
		}

		@Override
		protected Boolean doInBackground(String... params) {
			
			try {
				Utils utils = new Utils();
				String result = utils.isValidUser("C", userName, password);
				
				errorMsg = result;
				
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
				errorMsg = e.getMessage();
				Log.e("Error: ", e.getMessage());
			}

			return false;
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(Boolean flag) {
			
			if (flag) {
				
				/*SharedPreferences prefs = getSharedPreferences("CUSTOMER_USER_DETAILS", MODE_WORLD_READABLE); 
				
				Editor editor = prefs.edit();
				editor.putString("customerUserName", userName);
				editor.commit();*/
				
				// saveUserDetails(userName);
				
				Intent slideactivity = new Intent(CustomerLoginActivity.this, AppUserDetailsActivity.class);
				slideactivity.putExtra("appUserDetails", appUserDetails);
				startActivity(slideactivity);
				// finish();
				flag = false;
			} else {
				Toast.makeText(getApplicationContext(), "Invalid login, please try again. " + errorMsg, Toast.LENGTH_LONG).show();
			}
			
	        progressDialog.dismiss();
	    }
		
		private void saveUserDetails(String userName){
			
			ContentValues values = new ContentValues();
			values.put(UserNameContentProvider.user_name, userName);
			
			
			
			Uri uri = getContentResolver().insert(UserNameContentProvider.CONTENT_URI, values);
			
		}
		
	}
	
}
