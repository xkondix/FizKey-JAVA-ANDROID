package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz;

import android.content.Intent;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizActivity;

import java.util.ArrayList;
import java.util.List;


public class CreateCustomQuizFragment extends Fragment {

    private Button createQuizButton;
    private Spinner numberOfFieldsSpinner;
    private RadioGroup timerRadioGroup;
    private EditText secondsValueEditText, topicEditText, desripctionEditText;


    private List<Integer> numberOfFildsName, numberOfRoundsName;
    private CustomQuizModel customQuizModel;

    public CreateCustomQuizFragment() {
        // Required empty public constructor
    }

    public static CreateCustomQuizFragment newInstance() {
        CreateCustomQuizFragment fragment = new CreateCustomQuizFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_custom_quiz, container, false);

        createQuizButton = view.findViewById(R.id.create_custom_quiz_fragment);
        numberOfFieldsSpinner = view.findViewById(R.id.number_of_fields_create_custom_quiz_fragment);
        timerRadioGroup = view.findViewById(R.id.timer_create_custom_quiz_fragment);
        secondsValueEditText = view.findViewById(R.id.timer_value_create_custom_quiz_fragment);
        topicEditText = view.findViewById(R.id.topic_create_custom_quiz_fragment);
        desripctionEditText = view.findViewById(R.id.descricption_create_custom_quiz_fragment);

        customQuizModel = new CustomQuizModel();

        numberOfRoundsName = getList(getContext().getResources().getStringArray(R.array.quanity_question));
        numberOfFildsName = getList(getContext().getResources().getStringArray(R.array.quanity_block));


        timerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.off_create_custom_quiz_fragment:
                        customQuizModel.setTimerValue(0);
                        secondsValueEditText.setEnabled(false);
                        break;

                    case R.id.on__create_custom_quiz_fragment:
                        secondsValueEditText.setEnabled(true);
                        if(!(secondsValueEditText.getText().toString().equals(""))) {
                            setTimer(Integer.parseInt(secondsValueEditText.getText().toString()));
                        }
                        break;
                }
            }
        });



        numberOfFieldsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customQuizModel.setNumberOfFields(numberOfFildsName.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

        createQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(checkFields().equals(""))
                    {
                        Intent intent = new Intent(getActivity(), CreateCustomQuizActivity.class);
                        intent.putExtra(QuizActivity.EXTRA_MODEL_ID, customQuizModel);
                        getActivity().startActivity(intent);
                    }
                    else {
                        Toast.makeText(getContext(),checkFields(),Toast.LENGTH_SHORT);
                    }
            }
        });


        secondsValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int value;

                try {

                    value = Integer.parseInt(secondsValueEditText.getText().toString());
                    setTimer(value);

                }catch (NumberFormatException e)
                {
                    System.out.println(e);
                }

            }
        });

        desripctionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                customQuizModel.setDescripcion(s.toString());
            }
        });

        topicEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                customQuizModel.setTopic(s.toString());
            }
        });

        return view;

    }



    private List<Integer> getList(String[] array)
    {
        List<Integer> list = new ArrayList<>();

        for(String string : array)
            list.add(Integer.valueOf(string));

        return list;
    }

    private String checkFields()
    {
        String information = "";

        if(topicEditText.getText().toString().trim().equals(""))
        {
            topicEditText.setError(getContext().getString(R.string.empty_field));
            information+="topic";
        }
         if(topicEditText.getText().toString().trim().equals("")){
            desripctionEditText.setError(getContext().getString(R.string.empty_field));
            information+="descricton";
         }

         return information;
    }

    private void setTimer(int value)
    {
        if(value >= 5 && value <= 300){
            customQuizModel.setTimerValue(value);
        }
        else
        {
            Toast.makeText(getContext(), "5 - 600 secounds", Toast.LENGTH_SHORT).show();
        }
    }


}