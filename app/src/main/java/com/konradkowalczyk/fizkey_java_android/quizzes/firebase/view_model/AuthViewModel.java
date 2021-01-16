package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.AuthFirebaseRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.AuthFirebaseRepository;

public class AuthViewModel extends ViewModel {

    private AuthFirebaseRepositoryInterface authFirebaseRepository;

    private MutableLiveData<Boolean> registerUserLiveData;
    private MutableLiveData<FirebaseUser> loginUserLiveData;
    private MutableLiveData<Boolean> changePasswordUserLiveData;
    private MutableLiveData<Boolean> isLogedLiveData;


    public AuthViewModel() {
        super();
    }

    public void init()
    {
        if(authFirebaseRepository == null) {
            authFirebaseRepository = AuthFirebaseRepository.getInstance();
        }
        if(registerUserLiveData == null) {
            registerUserLiveData = new MutableLiveData<>();
        }
        if(loginUserLiveData == null) {
            loginUserLiveData = new MutableLiveData<>();
        }
        if(changePasswordUserLiveData == null) {
            changePasswordUserLiveData = new MutableLiveData<>();
        }
        if(isLogedLiveData == null) {
            isLogedLiveData = new MutableLiveData<>(false);
        }
    }

    public void registerUser(Account account)
    {
        registerUserLiveData = authFirebaseRepository.registerUser(account);
    }

    public void loginUser(Account account)
    {
        loginUserLiveData = authFirebaseRepository.loginUser(account);
    }

    public void changePassword(String email)
    {
        changePasswordUserLiveData = authFirebaseRepository.changePassword(email);
    }

    public LiveData<Boolean> getRegisterUserLiveData() {
        return registerUserLiveData;
    }

    public LiveData<FirebaseUser> getLoginUserLiveData() {
        return loginUserLiveData;
    }

    public LiveData<Boolean> getChangePasswordUserLiveData() {
        return changePasswordUserLiveData;
    }

    public String getCurrentlyUuid()
    {
        return loginUserLiveData.getValue().getUid();
    }

    public void logout()
    {
        loginUserLiveData = authFirebaseRepository.signOut();
    }

    public LiveData<Boolean> getIsLogedLiveData() {
        return isLogedLiveData;
    }

    public void setIsLogedLiveData(boolean isLoged) {
        this.isLogedLiveData.postValue(isLoged);
    }
}
