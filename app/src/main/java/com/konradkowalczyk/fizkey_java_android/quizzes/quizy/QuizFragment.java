package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuizFragment extends Fragment implements View.OnClickListener {


    private Map<Integer,Button> anwserButtons;
    private TextView questionTextView;
    private SendData sendData;

    private static final String QUANITY_ANWSERS = "quanity";
    private static final String POSITIVE_NUMBER = "positiveNumber";
    private static final String QUESTION = "question";
    private static final String ANWSERS = "anwsers";

    private static final String TIMER_ON = "timerOn";
    private static final String SECOUNDS = "secounds";
    private static final String RUNNING = "running";
    private static final String WAS_RUNNING = "wasRunning";



    private int positiveNumber;
    private String question;
    private List<String> anwsers;
    private int quanity;

    private int secound;
    private boolean timerOn;
    private boolean running;
    private boolean wasRunning;

    private final Handler handler = new Handler();




    public interface SendData {
        void getResults(String question, String yourAnswer, String goodAnswer, boolean boolAnswer);
        void sendActualTime(int secounds);
    }


    public static QuizFragment newInstance(int quanity,List<String> anwsers,String question,int positiveNumber) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt(QUANITY_ANWSERS, quanity);
        args.putInt(POSITIVE_NUMBER, positiveNumber);
        args.putString(QUESTION, question);
        args.putStringArrayList(ANWSERS, new ArrayList<>(anwsers));
        fragment.setArguments(args);
        return fragment;
    }

    public static QuizFragment newInstance(int quanity,List<String> anwsers
            ,String question
            ,int positiveNumber
            ,int timerValue) {

        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();

        args.putInt(QUANITY_ANWSERS, quanity);
        args.putInt(POSITIVE_NUMBER, positiveNumber);
        args.putString(QUESTION, question);
        args.putStringArrayList(ANWSERS, new ArrayList<>(anwsers));

        args.putBoolean(TIMER_ON, timerValue != 0);
        if(timerValue != 0) {
            args.putInt(SECOUNDS, timerValue);
            args.putBoolean(WAS_RUNNING, false);
            args.putBoolean(RUNNING, true);
        }

        fragment.setArguments(args);
        return fragment;
    }


    public QuizFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            quanity = getArguments().getInt(QUANITY_ANWSERS);
            positiveNumber = getArguments().getInt(POSITIVE_NUMBER);
            question = getArguments().getString(QUESTION);
            anwsers = getArguments().getStringArrayList(ANWSERS);

            timerOn = getArguments().getBoolean(TIMER_ON);

            if(timerOn == true) {
                secound = getArguments().getInt(SECOUNDS);
                wasRunning = getArguments().getBoolean(WAS_RUNNING);
                running = getArguments().getBoolean(RUNNING);
                timer();
            }
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        anwserButtons = new TreeMap<>();

        View view = inflater.inflate(R.layout.fragment_quiz, container, false);


        questionTextView = view.findViewById(R.id.question_quiz);
        questionTextView.setText(question);

        int column =  2;
        int row = quanity / 2;
        GridLayout gridLayout = view.findViewById(R.id.quiz_gridlayout);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row+1);



        for (int i = 0; i < quanity; i++) {

            Button button = new Button(getActivity().getApplicationContext());
            button.setText(anwsers.get(i));
            button.setAllCaps(false);
            button.setTypeface(null, Typeface.BOLD);
            button.setId(i);
            button.setOnClickListener(this);
            anwserButtons.put(i,button);

            gridLayout.addView(button);

            GridLayout.LayoutParams param= new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED,GridLayout.FILL,1f),
                    GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f));
            param.height = 0;
            param.width = 0;

            param.bottomMargin = 5;
            param.leftMargin = 5;
            param.rightMargin = 5;
            param.topMargin = 5;

            button.setLayoutParams(param);

        }

        setButtonsBasicColorAndUnlock();

        return view;
    }

    @Override
    public void onClick(View v) {

        colorAnwser(v.getId(),anwserButtons.get(v.getId()));
        sendData.getResults(question
                , anwsers.get(v.getId())
                , anwsers.get(positiveNumber)
                ,v.getId() == positiveNumber);
        lockButtons();
        pause();
    }

    private void colorAnwser(int number, Button button)
    {
        if(number == positiveNumber)
        {
            button.setBackgroundColor(Color.GREEN);
            button.setTextColor(Color.BLACK);
        }
        else
        {
            button.setBackgroundColor(Color.RED);
            anwserButtons.get(positiveNumber).setBackgroundColor(Color.GREEN);

            button.setTextColor(Color.BLACK);
            anwserButtons.get(positiveNumber).setTextColor(Color.BLACK);

        }
    }


    public void setQuestion(String question) {
        this.question = question;
        questionTextView.setText(question);
    }

    public void setAnwsers(List<String> anwsers) {
        this.anwsers = anwsers;
        int i = 0;
        for(Button b : anwserButtons.values())
        {
            b.setText(anwsers.get(i));
            i++;
        }
    }

    public void setPositiveNumber(int positiveNumber) {
        this.positiveNumber = positiveNumber;
    }

    public void setButtonsBasicColorAndUnlock()
    {
        for(Button b : anwserButtons.values())
        {
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.GRAY);
            b.setEnabled(true);
        }
    }

    private void timer()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {

                if(running)
                {
                    secound--;
                    if(secound <= 0)
                    {
                        sendData.getResults(question
                                , "null"
                                , anwsers.get(positiveNumber)
                                ,false);
                        pause();
                    }
                }

                sendData.sendActualTime(secound);

                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            this.sendData = (SendData) getActivity();
        }catch (ClassCastException e){
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.sendData = null;
    }

    private void lockButtons()
    {
        for(Button button : anwserButtons.values())
        {
            button.setEnabled(false);
        }
    }

    public void setSecounds(int secounds)
    {
        this.secound = secounds;
    }

    public void pause()
    {
        wasRunning = running;
        running = false;
    }

    public void resume()
    {
        if (wasRunning){
            running = true;
        }
    }

    public void removeHandler()
    {
        handler.removeCallbacksAndMessages(null);
    }



}