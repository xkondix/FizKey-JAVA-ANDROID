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

public class QuizActivity extends AppCompatActivity implements QuizFragment.SendData {

    public static final String EXTRA_MODEL_ID = "model";
    public static final String RESULTS = "results";

    private QuizModelInteface quizModel;

    private QuizFragment fragment;
    private TextView counterTextView, timerTextView;
    private Toolbar toolbar;

    private QuizResults quizResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        counterTextView = findViewById(R.id.counter_question);
        timerTextView = findViewById(R.id.quiz_activity_timer);


        quizModel =  getIntent().getParcelableExtra(QuizActivity.EXTRA_MODEL_ID);
        quizResults = new QuizResults();
        setNumberOfQuestionTextView();


        fragment =  QuizFragment.newInstance(quizModel.getNumberOfFields()
                , quizModel.getListAnswers().get(quizModel.getCurrentlyNumber())
                , quizModel.getQuestions().get(quizModel.getCurrentlyNumber())
                , quizModel.getPositiveNumbers().get(quizModel.getCurrentlyNumber())
                , quizModel.getTimerValue());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.quiz_frame, fragment);
        ft.commit();
    }

    @Override
    public void getResults(final String question
            , final String yourAnswer
            , final String goodAnswer
            , final boolean boolAnswer) {

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                quizResults.addQuestion(question);
                quizResults.addGoodAnswer(goodAnswer);
                quizResults.addYourAnswer(yourAnswer);
                quizResults.addBooleanAnswer(boolAnswer);


                if(quizModel.getCurrentlyNumber()>= quizModel.getMaxNumber()-1)
                {

                    fragment.removeHandler();

                    Intent intent = new Intent();
                    intent.putExtra(RESULTS, quizResults);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {

                    quizModel.setCurrentlyNumber(quizModel.getCurrentlyNumber() + 1);
                    setNumberOfQuestionTextView();
                    fragment.setQuestion(quizModel.getQuestions().get(quizModel.getCurrentlyNumber()));
                    fragment.setAnwsers(quizModel.getListAnswers().get(quizModel.getCurrentlyNumber()));
                    fragment.setPositiveNumber(quizModel.getPositiveNumbers().get(quizModel.getCurrentlyNumber()));
                    fragment.setButtonsBasicColorAndUnlock();

                    if(quizModel.getTimerValue() != 0)
                    {
                        fragment.setSecounds(quizModel.getTimerValue());
                        fragment.resume();
                    }

                }

            }
        }.start();

    }

    @Override
    public void sendActualTime(int secounds) {
        setTimeTextView(secounds);
    }

    private void setNumberOfQuestionTextView()
    {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                counterTextView.setText((quizModel.getCurrentlyNumber() + 1) + "/" + quizModel.getMaxNumber());

            }
        });
    }

    private void setTimeTextView(final int secounds)
    {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                timerTextView.setText("time :"+" "+ secounds);

            }
        });
    }

}