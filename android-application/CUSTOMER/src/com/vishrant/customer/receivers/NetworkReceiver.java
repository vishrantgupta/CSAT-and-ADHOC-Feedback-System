package com.vishrant.customer.receivers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.vishrant.customer.connectivity.SimpleHttpClient;
import com.vishrant.customer.constant.HttpRequestURL;

public class NetworkReceiver extends BroadcastReceiver /*implements LoaderManager.LoaderCallbacks<Cursor>*/ {   
    
	Context c = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    
		ConnectivityManager conn =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = conn.getActiveNetworkInfo();
	
		if (networkInfo != null && ( networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
				|| networkInfo.getType() == ConnectivityManager.TYPE_WIFI)) {

			this.c = context;
			
	        
	        
	    } else {
	        
	    }
	}
	
	private Boolean checkIfFeedbackGiven(Context context, String customerUserName) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
		
		/*SharedPreferences prefs = context.getSharedPreferences("FEEDBACK_USER_DETAILS", Context.MODE_PRIVATE); 
		String customerUserName = prefs.getString("customerUserName", null);*/
		
		Toast.makeText(context, "In check if feedback given customer user name " + customerUserName, Toast.LENGTH_SHORT).show();
		
		if (customerUserName != null) {
			
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("userName", customerUserName));
			postParameters.add(new BasicNameValuePair("applicableMonth", sdf.format(new Date())));
			postParameters.add(new BasicNameValuePair("status", "NEW"));
			
			String response = null;
			try {
				response = SimpleHttpClient.executeHttpPost(HttpRequestURL.URL + HttpRequestURL.IS_FEEDBACK_SET_FOR_MONTH,
						postParameters);
				String res = response.toString();

				return res != null && res.trim().equalsIgnoreCase("yes") ? true : false;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return false;
	}
	
	/*CursorLoader cursorLoader;
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		cursorLoader= new CursorLoader(c , Uri.parse("content://com.vishrant.customer/user_details"), null, null, null, null);
		return cursorLoader;
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		cursor.moveToFirst();
		StringBuilder res=new StringBuilder();
		
		String userName = null;
		
        while (!cursor.isAfterLast()) {
        	
        	userName = cursor.getString(0);
        	Toast.makeText(getApplicationContext(), cursor.getString(1), Toast.LENGTH_LONG).show();
        	Toast.makeText(getApplicationContext(), cursor.getString(2), Toast.LENGTH_LONG).show();
        	Toast.makeText(getApplicationContext(), cursor.getString(3), Toast.LENGTH_LONG).show();
        	
        	Toast.makeText(getApplicationContext(), cursor.getString(1), Toast.LENGTH_LONG).show();
        	Toast.makeText(getApplicationContext(), cursor.getString(2), Toast.LENGTH_LONG).show();
        	Toast.makeText(getApplicationContext(), cursor.getString(3), Toast.LENGTH_LONG).show();
        	Toast.makeText(getApplicationContext(), cursor.getString(4), Toast.LENGTH_LONG).show();
        	
			
            cursor.moveToNext();
        }
        
        if (checkIfFeedbackGiven(c, userName)) {
        	Toast.makeText(c, "Please provide your valuable feedback to improve our service.", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(c, "Fuck.", Toast.LENGTH_LONG).show();
		}
        
        //resultView.setText(res);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}*/

}
