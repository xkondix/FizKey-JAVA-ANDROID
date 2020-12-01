package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizGameActivity;

import java.util.ArrayList;
import java.util.List;


public class QuizMenuFragment extends Fragment implements View.OnClickListener, SelectPhenomenonDialogFragment.OnFeedBack {

    private Button startQuizButton, selectRangeButton;
    private Spinner quanityQuizzesSpinner, quanityBlockQuizzesSpinner;
    private List<Integer> quanityBlock, quanityQuizess;

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
        quanityBlockQuizzesSpinner =  view.findViewById(R.id.quanity_block_quiz); //2 4 6


        quizViewModel = new QuizModelBase();
        quanityBlock = getList(getContext().getResources().getStringArray(R.array.quanity_question));
        quanityQuizess = getList(getContext().getResources().getStringArray(R.array.quanity_block));


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

                if(quizViewModel.getActivePhenomena().isEmpty())
                {
                    Toast.makeText(getContext(), getContext().getResources()
                                    .getString(R.string.select_phenomena)
                            ,Toast.LENGTH_LONG).show();
                }
                else {

                    setQuestions();
                    Intent intent = new Intent(getActivity(), QuizGameActivity.class);
                    intent.putExtra(QuizGameActivity.EXTRA_MODELID, quizViewModel);
                    getActivity().startActivity(intent);
                }
                break;

            case R.id.select_quiz_range:

                SelectPhenomenonDialogFragment dialog = SelectPhenomenonDialogFragment
                        .newInstance(quizViewModel.getActivePhenomena());
                dialog.setTargetFragment(QuizMenuFragment.this, 1);
                dialog.show(getActivity().getSupportFragmentManager(), "Quiz phenomena selector");
                break;

            default:
                break;

        }
    }

    @Override
    public void sendStatusMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void sendActivePhenomena(List<String> activePhenomena) {
        quizViewModel.setActivePhenomena(activePhenomena);
    }

    private void setQuestions()
    {
        quizFactory = new QuizFactory(getContext(), quizViewModel.getBlockNumber());
        quizFactory.acceptForces(quizViewModel.getActivePhenomena());
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


}