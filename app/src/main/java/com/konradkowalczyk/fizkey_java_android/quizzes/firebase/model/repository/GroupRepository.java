package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.GroupRepositoryInteface;

public class GroupRepository implements GroupRepositoryInteface {

    private final static CollectionReference groupRef
        = FirestoreInstance.FIREBASE_FIRESTORE_INSTANCE.collection("groups");

    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();

    private static GroupRepositoryInteface groupRepository;

    public static GroupRepositoryInteface getInstance()
    {
        if(groupRepository == null)
        {
            groupRepository = new GroupRepository();
        }

        return groupRepository;
    }

    public MutableLiveData<DocumentReference> insertGroup(Group group)
    {
        DocumentReference uuidRef = groupRef.document();
        uuidRef.get().addOnCompleteListener(uuidGroup -> {
            if (uuidGroup.isSuccessful()) {
                DocumentSnapshot document = uuidGroup.getResult();
                if (!document.exists()) {
                    Log.i("insertGroup", "Document not exists");
                    group.setUuid(document.getId());
                    uuidRef.set(group).addOnCompleteListener(groupCreation -> {
                        if (groupCreation.isSuccessful()) {
                            Log.i("insertGroup", "Group document create");
                        } else {
                            Log.i("insertGroup", "Group document not create");
                        }
                    });
                } else {
                    Log.i("insertGroup", "Document exists");
                }
            } else {
                Log.i("insertGroup", "ref uuid error");
            }
        });

        return new MutableLiveData<>(uuidRef);
    }

    public MutableLiveData<Group> getGroupByUUID(String groupUuid)
    {
        onLoadGroup(groupUuid);
        return groupMutableLiveData;
    }

    public void updateGroup(Group group)
    {
        DocumentReference uuidRef = groupRef.document(group.getUuid());
        uuidRef.update("students", group.getStudents()
                ,"studentGrades", group.getStudentGrades()
                ,"tasks", group.getTasks())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("updateUser", "User successfully updated!");
                        groupMutableLiveData.postValue(group);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("updateUser", "Error updating user document", e);
                    }
                });
    }

    private void onLoadGroup(String groupUuid) {

        DocumentReference uuidRef = groupRef.document(groupUuid);
        uuidRef.get().addOnCompleteListener(uuidGroup -> {
            if (uuidGroup.isSuccessful()) {
                DocumentSnapshot document = uuidGroup.getResult();
                if (document.exists()) {
                    groupMutableLiveData.postValue(document.toObject(Group.class));
                    Log.i("onLoadGroup", "Document exists");
                } else {
                    Log.i("onLoadGroup", "Document not exists");
                }

            } else {
                Log.i("onLoadGroup", "Ref uuid error");
            }
        });
    }

    public MutableLiveData<DocumentReference> addToGroup(String groupUuid, User user)
    {
        MutableLiveData group = new MutableLiveData();

        DocumentReference uuidRef = groupRef.document(groupUuid);
        uuidRef.get().addOnCompleteListener(uuidGroup -> {
            if (uuidGroup.isSuccessful()) {
                DocumentSnapshot document = uuidGroup.getResult();
                if (document.exists()) {

                    Group groupFind = document.toObject(Group.class);
                    DocumentReference userRef = FirestoreInstance.FIREBASE_FIRESTORE_INSTANCE
                            .collection("users").document(user.getUuid());

                    if(!groupFind.getAuthorUUID().equals(user.getUuid())) {

                        if (!groupFind.getStudents().contains(userRef)) {
                            groupFind.addToGroup(userRef);
                        }
                        if (groupFind.getStudentGrades().get(userRef) == null) {
                            groupFind.addNewUser(user.getUuid());
                        }
                        else {
                            groupFind.addTasksToNewUser(user.getUuid());
                        }
                        uuidRef.set(groupFind);
                        group.postValue(uuidRef);

                    }
                    Log.i("addToGroup", "Document  exists");

                } else {
                    Log.i("addToGroup", "Document exists");
                }
            } else {
                Log.i("addToGroup", "Ref uuid error");
            }
        });

      return group;

    }
}
