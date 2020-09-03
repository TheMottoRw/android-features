package com.example.a17rp01001;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class ExampleBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            boolean connection = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
            if(connection) Toast.makeText(context,"Connected",Toast.LENGTH_SHORT).show();
            else Toast.makeText(context,"Change found",Toast.LENGTH_SHORT).show();
        }
    }
}
