package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Email;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailInterface {

    @POST("email")
    Call<Email> createEmail(@Body Email emal);

}
