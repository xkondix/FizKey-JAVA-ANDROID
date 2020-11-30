package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;

public class QuizGameActivity extends AppCompatActivity implements QuizFragment.SendData {

    public static final String EXTRA_MODELID = "model";
    private QuizModel quizModel;
    private QuizFactory quizFactory;
    private QuizFragment fragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        quizModel= (QuizModel) getIntent().getParcelableExtra(QuizGameActivity.EXTRA_MODELID);
        quizFactory = new QuizFactory(getApplication().getApplicationContext(),quizModel.getBlockNumber());
        quizFactory.acceptForces(quizModel.getActivePhenomena());
        quizFactory.generateQuestions(quizModel.getMaxNumber());

        System.out.println(quizFactory.getDataQuestionAnwser());
        System.out.println(quizModel.getBlockNumber());
        System.out.println(quizModel.getMaxNumber());
        System.out.println(quizModel.getCurrentNumber());




        fragment =  QuizFragment.newInstance(quizModel.getBlockNumber()
                ,quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getAnswers()
                ,quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getQuestion()
                ,quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getPositiveNumber());

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
                quizModel.setCurrentNumber(quizModel.getCurrentNumber()+1);

                if(!(quizModel.getCurrentNumber()<quizModel.getMaxNumber()))
                {
                finish();
                }

                fragment.setQuestion(quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getQuestion());
                fragment.setAnwsers(quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getAnswers());
                fragment.setPositiveNumber(quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getPositiveNumber());
                fragment.setButtonsBasicColor();

            }
        }.start();

    }
}