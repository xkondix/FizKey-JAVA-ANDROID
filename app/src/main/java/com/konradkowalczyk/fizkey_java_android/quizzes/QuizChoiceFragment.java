package com.konradkowalczyk.fizkey_java_android.quizzes;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.mainly.FallDown;

public class QuizChoiceFragment extends Fragment {


    private Button button1,button2,button3,button4;
    private TextView textView;


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

        FallDown fallDown = new FallDown(getContext());

        button1 = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);

        textView = view.findViewById(R.id.textxddd);

        textView.setText(fallDown.getQuestion());
        button1.setText(fallDown.getAnswers().get(0));
        button2.setText(fallDown.getAnswers().get(1));
        button3.setText(fallDown.getAnswers().get(2));
        button4.setText(fallDown.getAnswers().get(3));


        return view;
    }
}