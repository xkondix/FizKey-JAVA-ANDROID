package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import com.google.common.base.Optional;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserDAO implements UserRepositoryInterface{

    private final static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final static FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private final static CollectionReference usersRef = rootRef.collection("users");

    @Override
    public Optional<User> getUserByUUID(String uuid) {
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
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
        authAccount(user);
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

    private boolean checkIfTheUserExists(String email)
    {
        final AtomicBoolean isNewUser = new AtomicBoolean(false);
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(authTask -> {
                    if (authTask.isSuccessful()) {
                        isNewUser.set(authTask.getResult().getSignInMethods().isEmpty());
                    }
                    else {
                        System.out.println("xd");
                    }});

        return isNewUser.get();
    }

    private void authAccount(User user)
    {

        if(checkIfTheUserExists(user.getEmail())) {
            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(authTask -> {
                        if (authTask.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                user.setUuid(firebaseUser.getUid());
                                createUser(user);
                            }
                        } else {
                            //  logErrorMessage(authTask.getException().getMessage());
                        }
                    });
        }
    }

    private void createUser(final User user) {
        DocumentReference uuidRef = usersRef.document(user.getUuid());
        uuidRef.get().addOnCompleteListener(uuidTask -> {
            if (uuidTask.isSuccessful()) {
                DocumentSnapshot document = uuidTask.getResult();
                if (!document.exists()) {
                    uuidRef.set(user).addOnCompleteListener(userCreationTask -> {
                        if (userCreationTask.isSuccessful()) {

                        } else {
                            // logErrorMessage(userCreationTask.getException().getMessage());
                        }
                    });
                } else {
                    // newUserMutableLiveData.setValue(authenticatedUser);
                }
            } else {
                //logErrorMessage(uidTask.getException().getMessage());
            }
        });
    }

}
