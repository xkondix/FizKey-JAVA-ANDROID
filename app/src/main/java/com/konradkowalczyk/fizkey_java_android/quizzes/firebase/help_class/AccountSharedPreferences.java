package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class;

import android.content.Context;
import android.content.SharedPreferences;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;

public class AccountSharedPreferences {

    private final static String PREFRENCES_NAME = "account Information";

    public static void saveData(String email, String password, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFRENCES_NAME, 0);
        settings.edit().putString("email", email).putString("password", password).commit();
    }


    public static Account getData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFRENCES_NAME, 0);
        String email = settings.getString("email", "");
        String password = settings.getString("password", "");
        return new Account(email,password);
    }


}
