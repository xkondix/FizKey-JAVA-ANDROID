package com.konradkowalczyk.fizkey_java_android.quizzes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.konradkowalczyk.fizkey_java_android.Constants;

public class NetworkViewModel extends ViewModel {

    public NetworkViewModel() {
        super();
    }

    public LiveData<Boolean> getIsNetworkLiveData() {
        return Constants.isInternet();
    }
}
