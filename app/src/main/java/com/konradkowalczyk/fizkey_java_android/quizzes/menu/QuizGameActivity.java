package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizGameActivity extends AppCompatActivity implements QuizFragment.SendData {

    int counter = 1;
    private QuizFragment fragment;
    List<Question> q;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        QuizFactory qo = new QuizFactory(getApplicationContext());
        q = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            q.add(qo.getQuestion());
        }


        fragment =  QuizFragment.newInstance(4,q.get(0).getAnswers(),q.get(0).getQuestion(),q.get(0).getPositiveNumber());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.quiz_frame, fragment);
        ft.commit();
    }

    @Override
    public void getBooleanAnwser(boolean anwser) {

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                fragment.setQuestion(q.get(counter).getQuestion());
                fragment.setAnwsers(q.get(counter).getAnswers());
                fragment.setPositiveNumber(q.get(counter).getPositiveNumber());
                fragment.setButtonsBasicColor();
                if(counter<10)
                {
                    counter++;
                }
                else
                    finish();

            }
        }.start();

    }
}