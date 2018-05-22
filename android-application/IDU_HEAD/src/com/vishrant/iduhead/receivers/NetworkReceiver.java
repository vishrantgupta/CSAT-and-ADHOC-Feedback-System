package com.vishrant.iduhead.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {   
    
	@Override
	public void onReceive(Context context, Intent intent) {
	    
		ConnectivityManager conn =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = conn.getActiveNetworkInfo();
	
	    if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE_DUN) {
	        Toast.makeText(context, "Dun Mobile data connected", Toast.LENGTH_SHORT).show();
	        Log.v(">>>Receiver", "Dun Mobile data connected");
	
	    } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
	    	Toast.makeText(context, "Type Mobile data connected", Toast.LENGTH_SHORT).show();
	        
	    	Log.v(">>>Receiver", "Type Mobile data connected");
	    } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
	    	Toast.makeText(context, "Wifi connected", Toast.LENGTH_SHORT).show();

	    	Log.v(">>>Receiver", "Wifi connected");
	    } else if (networkInfo.getType() == ConnectivityManager.TYPE_DUMMY) {
	    	Toast.makeText(context, "Type dunny", Toast.LENGTH_SHORT).show();

	    	Log.v(">>>Receiver", "Type dunny");
	    } else {
	        Toast.makeText(context, "R.string.lost_connection", Toast.LENGTH_SHORT).show();
	    }
	}
	
	private void checkIfFeedbackGiven() {
		
	}
	
}
