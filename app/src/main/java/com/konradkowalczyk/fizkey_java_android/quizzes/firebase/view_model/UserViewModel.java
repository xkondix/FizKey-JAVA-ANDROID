package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.UserRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private MutableLiveData<User> liveDataUser;
    private UserRepositoryInterface userRepository;


    public void init()
    {
        if(userRepository == null) {
            userRepository = UserRepository.getInstance();
        }
        if(liveDataUser == null) {
            liveDataUser = new MutableLiveData<>();
        }

    }


    public void insertUser(User user)
    {
        userRepository.insertUser(user);
    }

    public void getUserByUuid(String uuid)
    {
        liveDataUser = userRepository.getUserByUUID(uuid);
    }

    public LiveData<User> getLiveDataUser() {
        return liveDataUser;

    }
}
