package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;

public interface GroupRepositoryInteface {

    public MutableLiveData<DocumentReference> insertGroup(Group group);
    public void addToGroup(String groupUuid, User user);
    public void updateGroup(Group group);
    public MutableLiveData<Group> getGroupByUUID(String groupUuid);
}

