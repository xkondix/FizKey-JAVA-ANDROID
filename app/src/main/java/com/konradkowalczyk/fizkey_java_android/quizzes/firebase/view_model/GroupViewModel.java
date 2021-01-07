package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.GroupRepositoryInteface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.GroupRepository;

public class GroupViewModel extends ViewModel {

    private GroupRepositoryInteface groupRepository;

    private MutableLiveData<DocumentReference> createReferenceLiveData;
    private MutableLiveData<DocumentReference> addToGroupLiveData;
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
        if (addToGroupLiveData == null) {
            addToGroupLiveData = new MutableLiveData<>();
        }

    }

    public LiveData<Group> getGroupLiveData() {
        return groupLiveData;
    }


    public void getGroupsByUUID(String groupUuid) {
        groupLiveData = groupRepository.getGroupByUUID(groupUuid);
    }

    public LiveData<DocumentReference> getCreateReferenceLiveData() {
        return createReferenceLiveData;
    }

    public MutableLiveData<DocumentReference> getAddToGroupLiveData() {
        return addToGroupLiveData;
    }

    public void insertGroup(Group group) {
        createReferenceLiveData = groupRepository.insertGroup(group);
    }

    public void updateGroup(Group group)
    {
        groupRepository.updateGroup(group);
    }

    public void joinWithEntryCodetoGroup(String groupUuid, User user)
    {
        addToGroupLiveData = groupRepository.addToGroup(groupUuid,user);
    }


}


