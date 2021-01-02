package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.GroupRepositoryInteface;

public class GroupRepository implements GroupRepositoryInteface {

    private final static FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private final static CollectionReference groupRef = rootRef.collection("groups");

    private static GroupRepositoryInteface groupRepository;

    public static GroupRepositoryInteface getInstance()
    {
        if(groupRepository == null)
        {
            groupRepository = new GroupRepository();
        }

        return groupRepository;
    }

    public void insertGroup(Group group)
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

    }

    public void addToGroup(String groupUuid, User user)
    {
        MutableLiveData group = new MutableLiveData();

        DocumentReference uuidRef = groupRef.document(groupUuid);
        uuidRef.get().addOnCompleteListener(uuidGroup -> {
            if (uuidGroup.isSuccessful()) {
                DocumentSnapshot document = uuidGroup.getResult();
                if (!document.exists()) {
                    Log.i("addToGroup", "Document not exists");
                    Group groupFind = document.toObject(Group.class);
                    groupFind.addToGroup(rootRef.collection("users").document(user.getUuid()));
                    group.setValue(document.toObject(Group.class));
                } else {
                    Log.i("addToGroup", "Document exists");
                }
            } else {
                Log.i("addToGroup", "Ref uuid error");
            }
        });

        if(group.getValue()!=null)
        {
            uuidRef.get().addOnCompleteListener(uuidGroup -> {
                if (uuidGroup.isSuccessful()) {
                    DocumentSnapshot document = uuidGroup.getResult();
                    if (!document.exists()) {
                        Log.i("addToGroup", "Document not exists");
                    } else {
                        Log.i("addToGroup", "Document exists");
                        uuidRef.set(group).addOnCompleteListener(groupCreation -> {
                        if (groupCreation.isSuccessful()) {
                            Log.i("addToGroup", "Group document create");
                        } else {
                            Log.i("addToGroup", "Group document not create");
                        }
                        });
                    }
                } else {
                    Log.i("addToGroup", "Ref uuid error");
                }
            });
        }
        else {
            Log.i("addToGroup", "Group not exist");
        }
    }
}
