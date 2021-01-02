package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;

public interface GroupRepositoryInteface {

    public void insertGroup(Group group);
    public void addToGroup(String groupUuid, User user);
}

