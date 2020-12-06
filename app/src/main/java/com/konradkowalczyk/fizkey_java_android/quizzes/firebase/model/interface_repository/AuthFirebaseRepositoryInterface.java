package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository;

import com.google.firebase.auth.FirebaseUser;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;

public interface AuthFirebaseRepositoryInterface {

    public void registerUser(Account account);
    public void loginUser(Account account);
    public FirebaseUser getCurrentlyUser();
}
