package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.AuthFirebaseRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.UserRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.AuthFirebaseRepository;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepositoryInterface userRepository;
    private AuthFirebaseRepositoryInterface authFirebaseRepository;


    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository();
        this.authFirebaseRepository = new AuthFirebaseRepository();

    }

    public void insertAccount(User user)
    {
        user.setUuid(authFirebaseRepository.getCurrentlyUser().getUid());
        userRepository.insertUser(user);
    }


}
