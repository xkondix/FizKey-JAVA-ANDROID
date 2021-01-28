package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.EmailInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmailRepository {

    private static EmailRepository emailRepository;

    private EmailInterface emailInterface;

    public static EmailRepository getInstance() {
        if (emailRepository == null) {
            emailRepository = new EmailRepository();
        }
        return emailRepository;
    }

    public EmailRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fizkeyapp.herokuapp.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        emailInterface = retrofit.create(EmailInterface.class);
    }

    public EmailInterface getEmailInterface() {
        return emailInterface;
    }
}
