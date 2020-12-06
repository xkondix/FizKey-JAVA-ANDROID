package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.AuthFirebaseRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.AuthFirebaseRepository;

public class AuthViewModel extends AndroidViewModel {

    private AuthFirebaseRepositoryInterface authFirebaseRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.authFirebaseRepository = new AuthFirebaseRepository();
    }

    public void registerUser(Account account)
    {
        authFirebaseRepository.registerUser(account);
    }

    public void loginUser(Account account)
    {
        authFirebaseRepository.registerUser(account);
    }

    public FirebaseUser getCurrentlyUser()
    {
        return getCurrentlyUser();
    }
}
