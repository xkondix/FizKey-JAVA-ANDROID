package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.dao.UserDAO;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.UserRepositoryInterface;

import java.util.List;

public class UserRepository implements UserRepositoryInterface {

    private final static UserDAO userDao = new UserDAO();

    @Override
    public MutableLiveData<User> getUserByUUID(String uuid) {
        return userDao.getUserByUUID(uuid);
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
        new InsertUserAsyncTask().execute(user);
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


    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private InsertUserAsyncTask() {}
        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }
}
