package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskQuizRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;


public class MyGradesFragment extends Fragment {

    private RecyclerView recyclerViewTasks;
    private TaskQuizRecyclerViewAdapter adapter;
    private GroupViewModel groupViewModel;

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
        View view = inflater.inflate(R.layout.fragment_my_grades, container, false);

        recyclerViewTasks = view.findViewById(R.id.recycler_view_my_grades_fragment);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTasks.setHasFixedSize(true);


        groupViewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);
        groupViewModel.getTasksDoneCurrentlyUserMutableLiveData().observe(getViewLifecycleOwner(), customQuizModels -> {
            adapter.submitList(customQuizModels);
        });

        adapter = new TaskQuizRecyclerViewAdapter();
        recyclerViewTasks.setAdapter(adapter);


        adapter.setOnItemClickListener(new TaskQuizRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomQuizModel customQuizModel, int position) {
                groupViewModel.setCurrentlyCustomQuizModelLiveData(new MutableLiveData<>(customQuizModel));
                groupViewModel.setIsGrades(true);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.group_content_frame, DateGradesFragment.newInstance());
                ft.commit();
            }
        });

        return view;
    }
}