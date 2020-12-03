package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizActivity;

import java.util.ArrayList;
import java.util.List;


public class QuizMenuFragment extends Fragment implements View.OnClickListener, SelectPhenomenonDialogFragment.OnFeedBack {

    public static final int GET_RESULTS_REQUEST = 1;



    private Button startQuizButton, selectRangeButton;
    private Spinner quanityQuizzesSpinner, quanityBlockQuizzesSpinner;
    private RadioGroup levelRadioGroup, timerRadioGroup;
    private EditText secondsValueEditText;

    private List<Integer> quanityBlock, quanityQuizess;
    private List<String> activesPhenomena;
    private QuizFactory.Level level;
    private int onOff = 0;


    private QuizModelBase quizViewModel;
    private QuizFactory quizFactory;


    public QuizMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_menu, container, false);

        startQuizButton = view.findViewById(R.id.begin_quizzes);
        selectRangeButton= view.findViewById(R.id.select_quiz_range);

        quanityQuizzesSpinner = view.findViewById(R.id.quanity_block_question);
        quanityBlockQuizzesSpinner =  view.findViewById(R.id.quanity_block_quiz);

        level = QuizFactory.Level.NORMAL;
        levelRadioGroup = view.findViewById(R.id.quiz_menu_fragment_lvl);
        levelRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.quiz_menu_fragment_hard:
                        level = QuizFactory.Level.HARD;
                        System.out.println(level);

                        break;
                    case R.id.quiz_menu_fragment_normal:
                        level = QuizFactory.Level.NORMAL;
                        System.out.println(level);

                        break;
                }
            }
        });

        timerRadioGroup = view.findViewById(R.id.quiz_menu_fragment_timer);
        timerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.quiz_menu_fragment_OFF:
                        onOff = 0;
                        secondsValueEditText.setFocusable(false);


                        break;
                    case R.id.quiz_menu_fragment_ON:
                        onOff = 1;
                        secondsValueEditText.setFocusableInTouchMode(true);

                        break;
                }
            }
        });


        secondsValueEditText = view.findViewById(R.id.quiz_menu_fragment_timer_value);
        secondsValueEditText.addTextChangedListener(generalTextWatcher);

        quizViewModel = new QuizModelBase();
        quanityBlock = getList(getContext().getResources().getStringArray(R.array.quanity_question));
        quanityQuizess = getList(getContext().getResources().getStringArray(R.array.quanity_block));


        activesPhenomena = new ArrayList<>();


        startQuizButton.setOnClickListener(this);
        selectRangeButton.setOnClickListener(this);

        quanityQuizzesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quizViewModel.setMaxNumber(quanityBlock.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});


        quanityBlockQuizzesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quizViewModel.setBlockNumber(quanityQuizess.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

            return view;
        }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.begin_quizzes:

                if(activesPhenomena.isEmpty())
                {
                    Toast.makeText(getContext(), getContext().getResources()
                                    .getString(R.string.select_phenomena)
                            ,Toast.LENGTH_LONG).show();
                }
                else {

                    setQuestions();
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    intent.putExtra(QuizActivity.EXTRA_MODEL_ID, quizViewModel);
                    getActivity().startActivityForResult(intent,GET_RESULTS_REQUEST);

                }
                break;

            case R.id.select_quiz_range:

                SelectPhenomenonDialogFragment dialog = SelectPhenomenonDialogFragment
                        .newInstance(activesPhenomena);
                dialog.setTargetFragment(QuizMenuFragment.this, 1);
                dialog.show(getActivity().getSupportFragmentManager(), "Quiz phenomena selector");
                break;

            default:
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        quizViewModel.restartQuizQuestionsData();
    }

    @Override
    public void sendStatusMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void sendActivePhenomena(List<String> activePhenomena) {
        this.activesPhenomena = activePhenomena;
    }

    private void setQuestions()
    {
        quizFactory = new QuizFactory(getContext(), quizViewModel.getBlockNumber());
        quizFactory.setLevel(level);
        quizFactory.acceptForces(activesPhenomena);
        quizFactory.generateQuestions(quizViewModel.getMaxNumber());
        quizViewModel.setQuestion(quizFactory.getDataQuestionAnwser());

    }

    private List<Integer> getList(String[] array)
    {
        List<Integer> list = new ArrayList<>();

        for(String string : array)
            list.add(Integer.valueOf(string));

        return list;
    }

    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println(secondsValueEditText.getText().toString());
            System.out.println(s);
            if(Integer.parseInt(secondsValueEditText.getText().toString()) >= 5
            && Integer.parseInt(secondsValueEditText.getText().toString()) <= 600){
            quizViewModel.setTimerValue(Integer.parseInt(secondsValueEditText.getText().toString()));
            }
            else
            {
                Toast.makeText(getContext(), "5 - 600 secounds", Toast.LENGTH_SHORT).show();
            }
        }

    };


}