package com.developmentapps.summerschool.Connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionCheck extends BroadcastReceiver
{
    public static boolean connection;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (intent.getExtras() != null)
        {
            if (isConnected)
            {
                //Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
                connection = true;
            }
            else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE))
            {
                //Toast.makeText(context, "There's no network connectivity", Toast.LENGTH_SHORT).show();
                connection = false;
            }
        }
    }
}

