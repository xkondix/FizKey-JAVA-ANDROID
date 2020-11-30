package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizFactory;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizModel;

public class QuizGameActivity extends AppCompatActivity implements QuizFragment.SendData {

    public static final String EXTRA_MODELID = "model";

    private QuizModel quizModel;
    private QuizFactory quizFactory;

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

        quizModel= (QuizModel) getIntent().getParcelableExtra(QuizGameActivity.EXTRA_MODELID);
        quizFactory = new QuizFactory(getApplication().getApplicationContext(),quizModel.getBlockNumber());
        quizFactory.acceptForces(quizModel.getActivePhenomena());
        quizFactory.generateQuestions(quizModel.getMaxNumber());
        setText();



        fragment =  QuizFragment.newInstance(quizModel.getBlockNumber()
                ,quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getAnswers()
                ,quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getQuestion()
                ,quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getPositiveNumber());

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

                quizModel.addAnwsers(quizModel.getCurrentNumber(),anwser);

                if(quizModel.getCurrentNumber()>=quizModel.getMaxNumber()-1)
                {
                    finish();
                }
                else {

                    quizModel.setCurrentNumber(quizModel.getCurrentNumber() + 1);
                    setText();

                    fragment.setQuestion(quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getQuestion());
                    fragment.setAnwsers(quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getAnswers());
                    fragment.setPositiveNumber(quizFactory.getDataQuestionAnwser().get(quizModel.getCurrentNumber()).getPositiveNumber());
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

                counterTextView.setText((quizModel.getCurrentNumber() + 1) + "/" + quizModel.getMaxNumber());

            }
        });
    }

}