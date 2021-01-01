package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskQuizRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.TaskViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizActivity;

import static com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizMenuFragment.GET_RESULTS_REQUEST;

public class SolveCustomQuizFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private TaskQuizRecyclerViewAdapter adapter;

    public static SolveCustomQuizFragment newInstance() {
        return new SolveCustomQuizFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.solve_custom_quiz_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_create_custom_quiz_activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        taskViewModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
        taskViewModel.getCustomQuizModelsLiveData().observe(getViewLifecycleOwner(), customQuizModels -> {
            adapter.submitList(customQuizModels);

        });

        adapter = new TaskQuizRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new TaskQuizRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomQuizModel customQuizModel, int position) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra(QuizActivity.EXTRA_MODEL_ID, taskViewModel.shuffle(customQuizModel));
                getActivity().startActivityForResult(intent,GET_RESULTS_REQUEST);
            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}