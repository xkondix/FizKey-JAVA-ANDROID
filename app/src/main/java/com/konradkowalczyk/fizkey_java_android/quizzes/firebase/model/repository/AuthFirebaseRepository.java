package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.AuthFirebaseRepositoryInterface;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Singleton;

@SuppressWarnings("ConstantConditions")
@Singleton
public class AuthFirebaseRepository implements AuthFirebaseRepositoryInterface {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static AuthFirebaseRepository authFirebaseRepository;

    public static AuthFirebaseRepository getInstance()
    {
        if(authFirebaseRepository == null)
        {
            authFirebaseRepository = new AuthFirebaseRepository();
        }

        return authFirebaseRepository;
    }


    @Override
    public MutableLiveData<Boolean> registerUser(Account account) {

        MutableLiveData accountMutableLiveData = new MutableLiveData();

        if(!checkIfTheUserExists(account.getEmail())) {
            firebaseAuth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword())
                    .addOnCompleteListener(authTask -> {
                        if (authTask.isSuccessful()) {
                            Log.i("registerUser", "User auth account create");
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            accountMutableLiveData.setValue(true);
                            if (firebaseUser != null) {
                                Log.i("registerUser", "getFirebaseUser != null");
                                firebaseUser.sendEmailVerification().addOnCompleteListener(emailTask -> {
                                    if (emailTask.isSuccessful()) {
                                        Log.i("registerUser", "sendEmailVerification success");
                                    } else {
                                        Log.i("registerUser", "sendEmailVerification failure");
                                    }
                                });
                            }
                            else {

                                Log.i("registerUser", "getFirebaseUser == null");
                            }
                        } else {
                            accountMutableLiveData.setValue(false);
                            Log.i("registerUser", "User auth accont not create");

                        }
                    });
        }
        else {
            accountMutableLiveData.setValue(false);
        }

        return accountMutableLiveData;

    }

    @Override
    public MutableLiveData<FirebaseUser>  loginUser(Account account){

        MutableLiveData accountMutableLiveData = new MutableLiveData();

        firebaseAuth.signInWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnCompleteListener(loginTask -> {
                    if (loginTask.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser.isEmailVerified()) {
                            accountMutableLiveData.setValue(firebaseUser);
                            Log.i("login", "Logged in account");
                            Constants.LOGIN = true;
                        }
                        else {
                            accountMutableLiveData.setValue(null);
                            Log.i("login", "Email not verified");
                            firebaseAuth.signOut();
                        }
                    }
                    else {
                        accountMutableLiveData.setValue(null);
                        Log.i("login", "Login error");
                    }
                });

        return accountMutableLiveData;
    }

    @Override
    public MutableLiveData<Boolean> changePassword(String email) {

        MutableLiveData accountMutableLiveData = new MutableLiveData();

        if(checkIfTheUserExists(email)) {
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(loginTask -> {
                        if (loginTask.isSuccessful()) {
                            accountMutableLiveData.setValue(true);
                            Log.i("changePasswordUser", "sendPasswordResetEmail success");
                        } else {
                            accountMutableLiveData.setValue(false);
                            Log.i("changePasswordUser", "sendPasswordResetEmail failure");
                        }
                    });
        }
        else {
            accountMutableLiveData.setValue(false);
        }

        return accountMutableLiveData;
    }

    @Override
    public void logout() {
        authFirebaseRepository.logout();
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




}
