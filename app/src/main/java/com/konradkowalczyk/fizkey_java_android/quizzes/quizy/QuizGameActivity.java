package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizModelBase;

public class QuizGameActivity extends AppCompatActivity implements QuizFragment.SendData {

    public static final String EXTRA_MODELID = "model";

    private QuizModelInteface quizModelBase;

    private QuizFragment fragment;
    private TextView counterTextView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        counterTextView = findViewById(R.id.counter_question);

        quizModelBase = (QuizModelBase) getIntent().getParcelableExtra(QuizGameActivity.EXTRA_MODELID);
        setText();



        fragment =  QuizFragment.newInstance(quizModelBase.getBlockNumber()
                ,quizModelBase.getListAnswers().get(quizModelBase.getCurrentNumber())
                ,quizModelBase.getQuestions().get(quizModelBase.getCurrentNumber())
                ,quizModelBase.getPositiveNumbers().get(quizModelBase.getCurrentNumber()));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.quiz_frame, fragment);
        ft.commit();
    }

    @Override
    public void getBooleanAnwser(final boolean anwser) {

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                quizModelBase.addAnwsers(quizModelBase.getCurrentNumber(),anwser);

                if(quizModelBase.getCurrentNumber()>= quizModelBase.getMaxNumber()-1)
                {
                    finish();
                }
                else {

                    quizModelBase.setCurrentNumber(quizModelBase.getCurrentNumber() + 1);
                    setText();

                    fragment.setQuestion(quizModelBase.getQuestions().get(quizModelBase.getCurrentNumber()));
                    fragment.setAnwsers(quizModelBase.getListAnswers().get(quizModelBase.getCurrentNumber()));
                    fragment.setPositiveNumber(quizModelBase.getPositiveNumbers().get(quizModelBase.getCurrentNumber()));
                    fragment.setButtonsBasicColorAndUnlock();
                }

            }
        }.start();

    }

    private void setText()
    {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                counterTextView.setText((quizModelBase.getCurrentNumber() + 1) + "/" + quizModelBase.getMaxNumber());

            }
        });
    }

}