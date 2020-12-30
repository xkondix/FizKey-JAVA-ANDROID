package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.UserRepositoryInterface;

import java.util.List;

public class UserRepository implements UserRepositoryInterface {

    private final static FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private final static CollectionReference usersRef = rootRef.collection("users");

    private static UserRepository userRepository;

    public static UserRepository getInstance()
    {
        if(userRepository == null)
        {
            userRepository = new UserRepository();
        }

        return userRepository;
    }

    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private User user = null;


    @Override
    public MutableLiveData<User> getUserByUUID(String uuid) {

        if (user == null)
        {
            onLoadUser(uuid);
        }

        userMutableLiveData.setValue(user);

        return userMutableLiveData;
    }

    public void onLoadUser(String uuid)
    {
        DocumentReference uuidRef = usersRef.document(uuid);
        uuidRef.get().addOnCompleteListener(uuidTask -> {
            if (uuidTask.isSuccessful()) {
                DocumentSnapshot document = uuidTask.getResult();
                if (!document.exists()) {
                    Log.i("getUserByUUID", "Document not exists");
                    userMutableLiveData.postValue(new User("",""));
                } else {
                    Log.i("getUserByUUID", "Document exists");
                    User user = document.toObject(User.class);
                    userMutableLiveData.postValue(user);
                }
            } else {
                Log.i("getUserByUUID", "ref uuid error");
            }
        });
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
    public void insertUser(final User user) {
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


    @Override
    public void deleteUser(String uuid) {

    }

    @Override
    public void addGroup(String uuidUser, String uuidGroup) {

    }

    @Override
    public void setUUID(User user, String uuid) {

    }



}
