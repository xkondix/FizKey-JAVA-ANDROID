package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseUser;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.dao.AuthFirebaseDAO;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.AuthFirebaseRepositoryInterface;

public class AuthFirebaseRepository implements AuthFirebaseRepositoryInterface {

    private static AuthFirebaseDAO authFirebaseDAO = new AuthFirebaseDAO();


    @Override
    public void registerUser(Account account) {
        new RegisterUserAsyncTask().execute(account);
    }

    @Override
    public void loginUser(Account account){
        new LoginUserAsyncTask().execute(account);
    }

    @Override
    public void changePassword(String email) {
        new ChangePasswordAsyncTask().execute(email);
    }

    @Override
    public FirebaseUser getCurrentlyUser() {
        return authFirebaseDAO.getCurrentlyUser();
    }


    private static class RegisterUserAsyncTask extends AsyncTask<Account, Void, Void> {

        private RegisterUserAsyncTask() {}
        @Override
        protected Void doInBackground(Account... accounts) {
            System.out.println("xd 3");
            authFirebaseDAO.registerUser(accounts[0]);
            return null;
        }
    }

    private static class LoginUserAsyncTask extends AsyncTask<Account, Void, Void> {

        private LoginUserAsyncTask() {}
        @Override
        protected Void doInBackground(Account... accounts) {
            authFirebaseDAO.loginUser(accounts[0]);
            return null;
        }
    }

    private static class ChangePasswordAsyncTask extends AsyncTask<String, Void, Void> {

        private ChangePasswordAsyncTask() {}

        @Override
        protected Void doInBackground(String... strings) {
            authFirebaseDAO.changePassword(strings[0]);
            return null;
        }
    }

}