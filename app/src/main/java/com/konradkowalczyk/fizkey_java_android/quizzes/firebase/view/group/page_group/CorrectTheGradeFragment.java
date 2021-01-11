package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;


public class CorrectTheGradeFragment extends Fragment {


    public CorrectTheGradeFragment() {
        // Required empty public constructor
    }

    public static CorrectTheGradeFragment newInstance() {
        CorrectTheGradeFragment fragment = new CorrectTheGradeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_correct_the_grade, container, false);
    }
}