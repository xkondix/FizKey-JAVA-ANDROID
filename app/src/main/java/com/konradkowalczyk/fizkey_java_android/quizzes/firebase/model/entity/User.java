package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String uuid;
    private String name;
    private String surname;
    private List<String> uuidGroups;



    public User() {}


    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.uuidGroups = new ArrayList<>();
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

    public List<String> getUuidGroups() {
        return uuidGroups;
    }


}
