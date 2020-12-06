package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import java.io.Serializable;

public class Account implements Serializable {

    @SuppressWarnings("WeakerAccess")
    private String email;
    private  String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
