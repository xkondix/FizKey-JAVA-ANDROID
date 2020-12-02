package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizModelBase;

public class QuizActivity extends AppCompatActivity implements QuizFragment.SendData {

    public static final String EXTRA_MODEL_ID = "model";
    public static final String RESULTS = "results";

    private QuizModelInteface quizModelBase;

    private QuizFragment fragment;
    private TextView counterTextView;
    private Toolbar toolbar;

    private QuizResults quizResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        counterTextView = findViewById(R.id.counter_question);

        quizModelBase = (QuizModelBase) getIntent().getParcelableExtra(QuizActivity.EXTRA_MODEL_ID);
        quizResults = new QuizResults();
        setText();


        System.out.println(quizModelBase.getListAnswers());

        fragment =  QuizFragment.newInstance(quizModelBase.getBlockNumber()
                ,quizModelBase.getListAnswers().get(quizModelBase.getCurrentNumber())
                ,quizModelBase.getQuestions().get(quizModelBase.getCurrentNumber())
                ,quizModelBase.getPositiveNumbers().get(quizModelBase.getCurrentNumber()));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.quiz_frame, fragment);
        ft.commit();
    }

    @Override
    public void getBooleanAnwser(final String question
            ,final String yourAnswer
            ,final String goodAnswer
            ,final boolean boolAnswer) {

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                quizResults.addQuestion(question);
                quizResults.addGoodAnswer(goodAnswer);
                quizResults.addYourAnswer(yourAnswer);
                quizResults.addBooleanAnswer(boolAnswer);


                if(quizModelBase.getCurrentNumber()>= quizModelBase.getMaxNumber()-1)
                {
                    Intent intent = new Intent();
                    intent.putExtra(RESULTS, quizResults);
                    setResult(RESULT_OK, intent);
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

    private void addDataToResult(String question, String yourAnswer, String goodAnswer, boolean boolAnswer)
    {

    }

}