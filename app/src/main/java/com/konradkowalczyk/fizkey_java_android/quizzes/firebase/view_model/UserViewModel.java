package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.UserRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {

    private UserRepositoryInterface userRepository;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<List<Group>> groupsLiveData;
    private MutableLiveData<List<Group>> myGroupsLiveData;



    public void init()
    {
        if(userRepository == null) {
            userRepository = UserRepository.getInstance();
        }
        if(userLiveData == null) {
            userLiveData = new MutableLiveData<>();
        }
        if(myGroupsLiveData == null) {
            myGroupsLiveData = new MutableLiveData<>(new ArrayList<>());
        }
        if(groupsLiveData == null) {
            groupsLiveData = new MutableLiveData<>(new ArrayList<>());
        }

    }


    public void insertUser(User user)
    {
        userLiveData = userRepository.insertUser(user);
    }

    public void updateUser(User user)
    {
        userRepository.updateUser(user);
    }

    public void getUserByUuid(String uuid)
    {
        userLiveData = userRepository.getUserByUUID(uuid);
    }

    public LiveData<User> getLiveDataUser() {
        return userLiveData;
    }

    public LiveData<List<Group>> getGroupsLiveData() {
        return groupsLiveData;
    }

    public LiveData<List<Group>> getMyGroupsLiveData() {
        return myGroupsLiveData;
    }

    public LiveData<List<DocumentReference>> getAllGroupsLiveData() {
        return new MutableLiveData<>(userRepository.getUserByUUID(getUuid()).getValue().getGroups());
    }

    public void setGroups() {
        List<Group> groups = new ArrayList<>();
        List<Group> myGroups = new ArrayList<>();

        for (DocumentReference documentReference : userLiveData.getValue().getGroups()) {
            documentReference.get().addOnCompleteListener(groupDocument -> {
                if (groupDocument.isSuccessful()) {
                    DocumentSnapshot document = groupDocument.getResult();
                    Group group = document.toObject(Group.class);
                    if(group != null) {
                        if (group.getAuthorUUID().equals(userLiveData.getValue().getUuid())) {
                            myGroups.add(group);
                            myGroupsLiveData.postValue(myGroups);
                        } else {
                            groups.add(group);
                            groupsLiveData.postValue(groups);
                        }
                    }
                }
            });
        }



    }

    public String getUuid() {
        return userLiveData.getValue().getUuid();
    }

    public void leaveGroup(LiveData<Group> groupLiveData) {
        Group group = groupLiveData.getValue();
        User user = userLiveData.getValue();

        List<DocumentReference> groups = user.getGroups();
        for(int i = 0; i < groups.size(); i++) {
            if(groups.get(i).getId().equals(group.getUuid()))
            {
                groups.remove(i);
                break;
            }
        }

        user.setGroups(groups);
        userRepository.updateUser(user);
    }
}
