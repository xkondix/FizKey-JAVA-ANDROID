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

    private MutableLiveData<User> userLiveData;
    private UserRepositoryInterface userRepository;


    public void init()
    {
        if(userRepository == null) {
            userRepository = UserRepository.getInstance();
        }
        if(userLiveData == null) {
            userLiveData = new MutableLiveData<>();
        }

    }


    public void insertUser(User user)
    {
        userLiveData = userRepository.insertUser(user);
    }

    public void updateUser(User user)
    {
        userLiveData = userRepository.updateUser(user);
    }

    public void getUserByUuid(String uuid)
    {
        userLiveData = userRepository.getUserByUUID(uuid);
    }

    public LiveData<User> getLiveDataUser() {
        return userLiveData;

    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        for (DocumentReference documentReference : userLiveData.getValue().getGroups()) {
            documentReference.get().addOnCompleteListener(groupDocument -> {

                if (groupDocument.isSuccessful()) {

                    DocumentSnapshot document = groupDocument.getResult();
                    Group group = document.toObject(Group.class);
                    groups.add(group);

                }

            });
        }
        return groups;
    }

    public String getUuid() {
        return userLiveData.getValue().getUuid();
    }
}
