package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.GroupRepositoryInteface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.GroupRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupViewModel extends ViewModel {

    private GroupRepositoryInteface groupRepository;

    private MutableLiveData<DocumentReference> createReferenceLiveData;
    private MutableLiveData<DocumentReference> addToGroupLiveData;
    private MutableLiveData<Group> groupLiveData;
    private MutableLiveData<List<User>> usersMutableLiveData;



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
        if (usersMutableLiveData == null) {
            usersMutableLiveData = new MutableLiveData<>();
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

    public LiveData<List<User>> getUsersMutableLiveData() {
        return usersMutableLiveData;
    }

    public LiveData<DocumentReference> getAddToGroupLiveData() {
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

    public void setUsers(){
        List<User> users = new ArrayList<>();
        for (DocumentReference documentReference : groupLiveData.getValue().getStudents()) {
            documentReference.get().addOnCompleteListener(groupDocument -> {
                if (groupDocument.isSuccessful()) {
                    DocumentSnapshot document = groupDocument.getResult();
                    User user = document.toObject(User.class);
                    users.add(user);
                }

            });
        }

        Collections.sort(users);
        usersMutableLiveData.postValue(users);

    }


}


