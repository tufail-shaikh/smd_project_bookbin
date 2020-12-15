package com.tufailshaikh.bookstore1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class MyBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equalsIgnoreCase(intent.getAction())){
            boolean conn=intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
            if(conn){
                Toast.makeText(context,"DisConnected",Toast.LENGTH_SHORT).show();
                viewBooks.internetConnectivity=false;
            }
            else {
                Toast.makeText(context,"Connected",Toast.LENGTH_SHORT).show();
                viewBooks.internetConnectivity=true;
            }
        }
    }
}

