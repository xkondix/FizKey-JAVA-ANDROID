package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.AuthFirebaseRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.UserRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.AuthFirebaseRepository;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    public MutableLiveData<User> liveDataUser;

    private UserRepositoryInterface userRepository;
    private AuthFirebaseRepositoryInterface authFirebaseRepository;


    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository();
        this.authFirebaseRepository = new AuthFirebaseRepository();

    }

    public void insertUser(User user)
    {
        user.setUuid(authFirebaseRepository.getCurrentlyUser().getUid());
        userRepository.insertUser(user);
    }

    public void getCurrentlyUser()
    {
        if(liveDataUser == null) {
            liveDataUser = new MutableLiveData<>(null);
        }

        String uid = authFirebaseRepository.getCurrentlyUser().getUid();
        if(uid == null) {
            this.liveDataUser.setValue(null);
        }
        else {
            MutableLiveData<User> userByUUID = userRepository.getUserByUUID(uid);
            this.liveDataUser.setValue(userByUUID.getValue());
        }

    }


}
