package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.GroupRepositoryInteface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.GroupRepository;

import java.util.List;

public class GroupViewModel extends ViewModel {

    private GroupRepositoryInteface groupRepository;

    MutableLiveData<DocumentReference> createReference;

    public GroupViewModel() {
        super();
    }

    public void init() {
        if (groupRepository == null) {
            groupRepository = GroupRepository.getInstance();
        }

        if (createReference == null) {
            createReference = new MutableLiveData<>();
        }

    }

    public LiveData<DocumentReference> getCreateReference() {
        return createReference;
    }

    public void insertGroup(Group group) {
        createReference = groupRepository.insertGroup(group);
    }

    public LiveData<List<Group>> getGroupsByUUID() {
        return null;
    }
}


