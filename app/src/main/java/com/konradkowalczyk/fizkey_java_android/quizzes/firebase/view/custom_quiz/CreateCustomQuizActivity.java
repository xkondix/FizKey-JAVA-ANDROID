package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CreateCustomQuizRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskRecycler;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.TaskViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizActivity;

public class CreateCustomQuizActivity extends AppCompatActivity implements InsertTaskDialogFragment.OnFeedBack{

    public static final String EXTRA_MODEL_ID = "model";

    public static final String TASK_RECYCLER = "task";
    public static final String NUMBER_OF_FIELDS = "fields";
    public static final String ACTION_NEW = "new";
    public static final String ACTION_CHANGE = "change";


    public static final int GET_NEW_RESULTS_REQUEST = 2;
    public static final int CHANGE_RESULTS_REQUEST = 3;


    private TaskViewModel taskViewModel;
    private Button addNewQuestionButton, saveTaskButton;
    private CustomQuizModel customQuizModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_quiz);

        customQuizModel = (CustomQuizModel) getIntent().getParcelableExtra(QuizActivity.EXTRA_MODEL_ID);

        addNewQuestionButton = findViewById(R.id.add_question_create_custom_quiz_activity);
        addNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewData();
            }
        });

        saveTaskButton = findViewById(R.id.save_question_create_custom_quiz_activity);
        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_create_custom_quiz_activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);
        final CreateCustomQuizRecyclerViewAdapter adapter = new CreateCustomQuizRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.init();

        taskViewModel.getTaskRecyclerLiveData().observe(this, taskRecyclers -> {

            adapter.submitList(taskRecyclers);

        });

        taskViewModel.setCustomQuizModelLiveData(customQuizModel);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.removeFromList(adapter.getTaskRecyclerAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new CreateCustomQuizRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TaskRecycler task, int position) {
                changeData(task,position);
            }

        });

    }

    private void saveTask()
    {
        if(taskViewModel.getTaskRecyclerLiveData().getValue().size()>0) {
            InsertTaskDialogFragment dialog = new InsertTaskDialogFragment();
            dialog.show(getSupportFragmentManager(), "Save Task Dialog");
        }
    }


    private void addNewData()
    {
        Intent intent = new Intent(this, CustomResultActivity.class);
        intent.putExtra(CreateCustomQuizActivity.NUMBER_OF_FIELDS, customQuizModel.getNumberOfFields());
        intent.setAction(ACTION_NEW);
        startActivityForResult(intent,GET_NEW_RESULTS_REQUEST);
    }

    private void changeData(TaskRecycler taskRecycler, int position)
    {
        Intent intent = new Intent(this, CustomResultActivity.class);
        intent.putExtra(CreateCustomQuizActivity.NUMBER_OF_FIELDS, customQuizModel.getNumberOfFields());
        intent.putExtra(CreateCustomQuizActivity.TASK_RECYCLER, taskRecycler);
        intent.setAction(ACTION_CHANGE);
        taskViewModel.setPosition(position);
        startActivityForResult(intent,CHANGE_RESULTS_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CreateCustomQuizActivity.GET_NEW_RESULTS_REQUEST && resultCode == RESULT_OK) {

            TaskRecycler taskRecycler = data.getParcelableExtra(CustomResultActivity.RESULTS);
            taskViewModel.addToList(taskRecycler);
        }
        else if(requestCode == CreateCustomQuizActivity.CHANGE_RESULTS_REQUEST && resultCode == RESULT_OK) {

            TaskRecycler taskRecycler = data.getParcelableExtra(CustomResultActivity.RESULTS);
            taskViewModel.changeList(taskRecycler);


        }

    }

    @Override
    public void sendValues(String topic, String descripction) {

        CustomQuizModel customQuizModel = taskViewModel.getCustomQuizModelLiveData().getValue();
        customQuizModel.setTopic(topic);
        customQuizModel.setDescripcion(descripction);
        taskViewModel.setCustomQuizModelLiveData(customQuizModel);

        taskViewModel.insertTask();

    }
}