package com.konradkowalczyk.fizkey_java_android.quizzes;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;

public class QuizGameActivity extends AppCompatActivity implements QuizChoiceFragment.SendData {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Fragment fragment = new QuizChoiceFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.quiz_frame, fragment);
        ft.commit();
    }

    @Override
    public void getBooleanAnwser(boolean anwser) {

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Fragment fragment = new QuizChoiceFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.quiz_frame, fragment);
                ft.commit();
            }
        }.start();

    }
}