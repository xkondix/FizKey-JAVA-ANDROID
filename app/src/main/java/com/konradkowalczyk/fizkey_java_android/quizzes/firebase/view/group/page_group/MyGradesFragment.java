package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;


public class MyGradesFragment extends Fragment {


    public MyGradesFragment() {
        // Required empty public constructor
    }

    public static MyGradesFragment newInstance() {
        MyGradesFragment fragment = new MyGradesFragment();
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
        return inflater.inflate(R.layout.fragment_my_grades, container, false);
    }
}