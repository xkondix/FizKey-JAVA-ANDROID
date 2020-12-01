package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;


public class QuizResultDialog extends DialogFragment {


    private static final String QUIZ_RESULTS = "quizResults";

    private QuizResults quizResults;
    private RecyclerView recyclerView;
    private TextView  percentTextView, cancelTextView;

    public QuizResultDialog() {
        // Required empty public constructor
    }

    public static QuizResultDialog newInstance(QuizResults quizResults) {
        QuizResultDialog fragment = new QuizResultDialog();
        Bundle args = new Bundle();
        args.putParcelable(QUIZ_RESULTS, quizResults);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizResults = getArguments().getParcelable(QUIZ_RESULTS);
        }

        setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz_result_dialog, container, false);

        percentTextView = view.findViewById(R.id.dialog_quiz_result_percent);
        percentTextView.setText(quizResults.getPercent() + "%");

        recyclerView = view.findViewById(R.id.recycler_view_quiz_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.hasFixedSize();

        final QuizRecyclerViewAdapter quizRecyclerViewAdapter = new QuizRecyclerViewAdapter();
        recyclerView.setAdapter(quizRecyclerViewAdapter);

        quizRecyclerViewAdapter.setQuizResults(quizResults.getQuizResults(getContext()));




        cancelTextView = view.findViewById(R.id.dialog_quiz_result_close);
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}