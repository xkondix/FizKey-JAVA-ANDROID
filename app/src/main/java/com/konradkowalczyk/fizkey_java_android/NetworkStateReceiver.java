package com.konradkowalczyk.fizkey_java_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getExtras()!=null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if(activeNetwork!=null && activeNetwork.getState()==NetworkInfo.State.CONNECTED) {
                Toast toast = Toast.makeText(context
                        , context.getResources().getString(R.string.connected)
                        , Toast.LENGTH_SHORT);
                toast.show();
                Constants.isInternet = true;
                Constants.isInternetMutableLiveData.postValue(true);
            }
            else {
                Toast toast = Toast.makeText(context
                        , context.getResources().getString(R.string.not_connected)
                        , Toast.LENGTH_SHORT);
                toast.show();
                Constants.isInternet = false;
                Constants.isInternetMutableLiveData.postValue(false);

            }
        }
    }

}
