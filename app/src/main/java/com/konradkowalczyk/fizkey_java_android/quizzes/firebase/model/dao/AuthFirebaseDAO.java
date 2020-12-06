package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.dao;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.AuthFirebaseRepositoryInterface;

import java.util.concurrent.atomic.AtomicBoolean;

public class AuthFirebaseDAO implements AuthFirebaseRepositoryInterface {

    public final static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    public void registerUser(Account account) {
        authAccount(account);
    }

    @Override
    public void loginUser(Account account) {
        login((account));
    }

    @Override
    public FirebaseUser getCurrentlyUser() {
        return firebaseAuth.getCurrentUser();
    }

    private boolean checkIfTheUserExists(String email)
    {
        final AtomicBoolean isNewUser = new AtomicBoolean(false);
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(authTask -> {
                    if (authTask.isSuccessful()) {
                        isNewUser.set(authTask.getResult().getSignInMethods().isEmpty());
                        Log.i("checkIfTheUserExists", "Users not exists");
                    }
                    else {
                        Log.i("checkIfTheUserExists", "Users exists");
                    }});

        return isNewUser.get();
    }

    private void authAccount(Account account)
    {

        if(checkIfTheUserExists(account.getEmail())) {
            firebaseAuth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword())
                    .addOnCompleteListener(authTask -> {
                        if (authTask.isSuccessful()) {

                            Log.i("authAccount", "User auth account create");
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            if (firebaseUser != null) {

                                Log.i("authAccount", "getFirebaseUser != null");
                                firebaseUser.sendEmailVerification().addOnCompleteListener(emailTask -> {
                                    if (emailTask.isSuccessful()) {
                                        Log.i("authAccount", "sendEmailVerification success");
                                    } else {
                                        Log.i("authAccount", "sendEmailVerification failure");
                                    }
                                });
                            }
                            else {
                                Log.i("authAccount", "getFirebaseUser == null");
                            }
                        } else {
                            Log.i("authAccount", "User auth accont not create");

                        }
                    });
        }
    }

    private void login(Account account)
    {
        firebaseAuth.signInWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnCompleteListener(loginTask -> {
                    if (loginTask.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser.isEmailVerified()) {
                            Log.i("login", "Logged in account");
                            Constants.LOGIN = true;
                        }
                        else {
                            Log.i("login", "Email not verified");
                            firebaseAuth.signOut();
                        }
                    }
                    else {
                        Log.i("login", "Login error");
                    }
                });
    }


}
