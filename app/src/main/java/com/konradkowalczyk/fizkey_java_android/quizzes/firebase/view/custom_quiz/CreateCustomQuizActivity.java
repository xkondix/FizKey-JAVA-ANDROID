package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CreateCustomQuizRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskRecycler;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.TaskViewModel;

import java.util.List;

public class CreateCustomQuizActivity extends AppCompatActivity {

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_quiz);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_create_custom_quiz_activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);
        final CreateCustomQuizRecyclerViewAdapter adapter = new CreateCustomQuizRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getTaskRecyclerLiveData().observe(this, new Observer<List<TaskRecycler>>() {
            @Override
            public void onChanged(@Nullable List<TaskRecycler> taskRecyclers) {
                adapter.changeList(taskRecyclers);
            }
        });

    }
}