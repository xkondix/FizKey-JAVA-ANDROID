package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.os.AsyncTask;

import com.google.common.base.Optional;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;

import java.util.List;

public class UserRepository implements UserRepositoryInterface {

    private final static  UserDAO userDao = new UserDAO();

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
