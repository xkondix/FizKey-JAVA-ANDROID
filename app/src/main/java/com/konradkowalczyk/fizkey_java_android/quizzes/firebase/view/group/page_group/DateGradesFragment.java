package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.DateGradesRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizResultDialog;


public class DateGradesFragment extends Fragment {

    private RecyclerView recyclerViewDateGrades;
    private DateGradesRecyclerViewAdapter adapter;
    private GroupViewModel groupViewModel;

    public DateGradesFragment() {
        // Required empty public constructor
    }


    public static DateGradesFragment newInstance() {
        DateGradesFragment fragment = new DateGradesFragment();
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
        View view = inflater.inflate(R.layout.fragment_date_grades, container, false);

        recyclerViewDateGrades = view.findViewById(R.id.recycler_view_date_grades_fragment);
        recyclerViewDateGrades.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDateGrades.setHasFixedSize(true);


        groupViewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);


        adapter = new DateGradesRecyclerViewAdapter();
        adapter.submitList(groupViewModel.getCurrentlyDateOfTask());

        recyclerViewDateGrades.setAdapter(adapter);


        adapter.setOnItemClickListener(new DateGradesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String date, int position) {
                QuizResultDialog dialog = QuizResultDialog
                        .newInstance(groupViewModel.getyQuizResultOfTaskAndDate(date));
                dialog.show(getActivity().getSupportFragmentManager(), "Results View");
            }
        });

        return view;
    }
}