package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable, Comparable<User> {

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

    public User(String uuid, String name, String surname, List<DocumentReference> groups) {
        this.name = name;
        this.surname = surname;
        this.groups = groups;
        this.uuid = uuid;
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

    public void setGroups(List<DocumentReference> groups) {
        this.groups = groups;
    }

    public void addToGroup(DocumentReference groupReference)
    {
        groups.add(groupReference);
    }

    public String getNameAndSurname()
    {
        return name.trim() + " " + surname.trim();
    }

    @Override
    public int compareTo(User user) {
        return this.getNameAndSurname().toLowerCase().compareTo(user.getNameAndSurname().toLowerCase());
    }
}
