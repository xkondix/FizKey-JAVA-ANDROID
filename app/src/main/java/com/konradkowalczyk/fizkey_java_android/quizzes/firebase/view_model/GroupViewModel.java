package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.GroupRepositoryInteface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.GroupRepository;

public class GroupViewModel extends ViewModel {

    private GroupRepositoryInteface groupRepository;

    private MutableLiveData<DocumentReference> createReferenceLiveData;
    private MutableLiveData<Group> groupLiveData;


    public GroupViewModel() {
        super();
    }

    public void init() {
        if (groupRepository == null) {
            groupRepository = GroupRepository.getInstance();
        }
        if (createReferenceLiveData == null) {
            createReferenceLiveData = new MutableLiveData<>();
        }
        if (groupLiveData == null) {
            groupLiveData = new MutableLiveData<>();
        }

    }

    public void updateGroup(Group group)
    {
        groupRepository.updateGroup(group);
    }

    public LiveData<DocumentReference> getCreateReferenceLiveData() {
        return createReferenceLiveData;
    }

    public void insertGroup(Group group) {
        createReferenceLiveData = groupRepository.insertGroup(group);
    }

    public void getGroupsByUUID(String groupUuid) {
        groupLiveData = groupRepository.getGroupByUUID(groupUuid);
    }

    public MutableLiveData<Group> getGroupLiveData() {
        return groupLiveData;
    }
}


