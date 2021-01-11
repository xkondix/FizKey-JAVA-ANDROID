package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskQuizRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.TaskViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizActivity;

import static com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizMenuFragment.GET_RESULTS_REQUEST;


public class TaskToBeSolvedFragment extends Fragment {

    private RecyclerView recyclerViewTasks;
    private TaskQuizRecyclerViewAdapter adapter;
    private GroupViewModel groupViewModel;



    public TaskToBeSolvedFragment() {
        // Required empty public constructor
    }

    public static TaskToBeSolvedFragment newInstance() {
        TaskToBeSolvedFragment fragment = new TaskToBeSolvedFragment();
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
        View view = inflater.inflate(R.layout.fragment_task_to_be_solved, container, false);

        recyclerViewTasks = view.findViewById(R.id.recycler_view_task_to_be_solved_fragment);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTasks.setHasFixedSize(true);


        groupViewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);
        groupViewModel.getTasksToDoCurrentlyUserMutableLiveData().observe(getViewLifecycleOwner(), customQuizModels -> {
            adapter.submitList(customQuizModels);
        });

        adapter = new TaskQuizRecyclerViewAdapter();
        recyclerViewTasks.setAdapter(adapter);


        adapter.setOnItemClickListener(new TaskQuizRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomQuizModel customQuizModel, int position) {
                groupViewModel.setCurrentlyCustomQuizModelLiveData(new MutableLiveData<>(customQuizModel));
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra(QuizActivity.EXTRA_MODEL_ID, TaskViewModel.shuffle(customQuizModel));
                getActivity().startActivityForResult(intent,GET_RESULTS_REQUEST);
            }
        });

        return view;

    }
}