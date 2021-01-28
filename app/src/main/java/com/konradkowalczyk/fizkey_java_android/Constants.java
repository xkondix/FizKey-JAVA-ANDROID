package com.konradkowalczyk.fizkey_java_android;

import androidx.lifecycle.MutableLiveData;

public class Constants
{
    public static boolean LOGIN = false;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static boolean isInternet;

    public static MutableLiveData<Boolean> isInternetMutableLiveData = new MutableLiveData(isInternet);

    public static MutableLiveData<Boolean> isInternet() {
        return isInternetMutableLiveData;
    }


}
