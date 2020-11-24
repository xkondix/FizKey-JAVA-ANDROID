package com.konradkowalczyk.fizkey_java_android.quizzes;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.mainly.Question;

public class QuizChoiceFragment extends Fragment implements View.OnClickListener {


    private Button button1, button2, button3, button4;
    private TextView textView;
    private Question dataForce;
    private SendData sendData;


    public interface SendData {
        void getBooleanAnwser(boolean anwser);
    }


    public QuizChoiceFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        dataForce = new QuizFactory(getContext()).getQuestion();

        button1 = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);


        textView = view.findViewById(R.id.textxddd);

        textView.setText(dataForce.getQuestion());
        button1.setText(dataForce.getAnswers().get(0));
        button2.setText(dataForce.getAnswers().get(1));
        button3.setText(dataForce.getAnswers().get(2));
        button4.setText(dataForce.getAnswers().get(3));


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                colorAnwser(0,button1);
                sendData.getBooleanAnwser(true);
                break;
            case R.id.button2:
                colorAnwser(1,button2);
                sendData.getBooleanAnwser(true);
                break;
            case R.id.button3:
                colorAnwser(2,button3);
                sendData.getBooleanAnwser(true);
                break;
            case R.id.button4:
                colorAnwser(3,button4);
                sendData.getBooleanAnwser(true);
                break;
            default:
                break;

        }
    }

    private void colorAnwser(int number, Button button)
    {
        if(number == dataForce.getPositiveNumber())
        {
            button.setBackgroundColor(Color.GREEN);
        }
        else
        {
            button.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            sendData = (SendData) getActivity();
        }catch (ClassCastException e){
        }
    }
}