package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;

public interface AuthFirebaseRepositoryInterface {

    public MutableLiveData<Boolean> registerUser(Account account);
    public MutableLiveData<FirebaseUser> loginUser(Account account);
    public MutableLiveData<Boolean> changePassword(String email);

    public MutableLiveData<FirebaseUser> signOut();
}
