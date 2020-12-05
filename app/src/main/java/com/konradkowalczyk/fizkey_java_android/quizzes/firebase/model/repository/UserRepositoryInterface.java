package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import com.google.common.base.Optional;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;

import java.util.List;

public interface UserRepositoryInterface {

    public Optional<User> getUserByUUID(String uuid);
    public Optional<User> getUserByEmail(String email);
    public List<Group> getGroupsByUserUUID(String uuid);
    public List<User> getUsers();

    public void insertUser(User user);

    public void deleteUser(String uuid);
    
    public void addGroup(String uuidUser, String uuidGroup);

    public void setUUID(User user, String uuid);





}
