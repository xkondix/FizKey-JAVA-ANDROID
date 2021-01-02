package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String uuid;
    private String name;
    private String surname;
    private List<DocumentReference> groups;

    public User() {}

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.groups = new ArrayList<>();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<DocumentReference> getGroups() {
        return groups;
    }


}
