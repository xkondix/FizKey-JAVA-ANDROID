package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.dao;

import android.util.Log;

import com.google.common.base.Optional;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.UserRepositoryInterface;

import java.util.List;

public class UserDAO implements UserRepositoryInterface {

    private final static FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private final static CollectionReference usersRef = rootRef.collection("users");

    @Override
    public Optional<User> getUserByUUID(String uuid) {
        return null;
    }

    @Override
    public List<Group> getGroupsByUserUUID(String uuid) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public void insertUser(User user) {
        createUser(user);
    }

    @Override
    public void deleteUser(String uuid) {

    }

    @Override
    public void addGroup(String uuidUser, String uuidGroup) {

    }

    @Override
    public void setUUID(User user, String uuid) {

    }




    private void createUser(final User user) {
        DocumentReference uuidRef = usersRef.document(user.getUuid());
        uuidRef.get().addOnCompleteListener(uuidTask -> {
            if (uuidTask.isSuccessful()) {
                DocumentSnapshot document = uuidTask.getResult();
                if (!document.exists()) {
                    Log.i("createUser", "Document not exists");
                    uuidRef.set(user).addOnCompleteListener(userCreationTask -> {
                        if (userCreationTask.isSuccessful()) {
                            Log.i("createUser", "User document create");
                        } else {
                            Log.i("createUser", "User document not create");
                        }
                    });
                } else {
                    Log.i("createUser", "Document exists");
                }
            } else {
                Log.i("createUser", "ref uuid error");
            }
        });
    }

}
