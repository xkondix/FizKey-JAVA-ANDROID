package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskRecycler;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Task;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskViewModel extends AndroidViewModel {

    private LiveData<List<TaskRecycler>> taskRecyclerLiveData;
    private LiveData<CustomQuizModel> customQuizModelLiveData;
    private LiveData<List<CustomQuizModel>> customQuizModelsLiveData;

    private final static TaskRepository TASK_REPOSITORY = new TaskRepository();


    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRecyclerLiveData =  new MutableLiveData<List<TaskRecycler>>(new ArrayList<>());
        customQuizModelLiveData =  new MutableLiveData<CustomQuizModel>(new CustomQuizModel());
//        customQuizModelsLiveData = getTasks();
    }


    public LiveData<List<TaskRecycler>> getTaskRecyclerLiveData() {
        return taskRecyclerLiveData;
    }

    public void setTaskRecyclerLiveData(LiveData<List<TaskRecycler>> taskRecyclerLiveData) {
        this.taskRecyclerLiveData = taskRecyclerLiveData;
    }

    public LiveData<CustomQuizModel> getCustomQuizModelLiveData() {
        return customQuizModelLiveData;
    }

    public void setCustomQuizModelLiveData(LiveData<CustomQuizModel> customQuizModelLiveData) {
        this.customQuizModelLiveData = customQuizModelLiveData;
    }

    public LiveData<List<CustomQuizModel>> getCustomQuizModelsLiveData() {
        return customQuizModelsLiveData;
    }

    public void setCustomQuizModelsLiveData(LiveData<List<CustomQuizModel>> customQuizModelsLiveData) {
        this.customQuizModelsLiveData = customQuizModelsLiveData;
    }

    public void removeFromList(TaskRecycler taskRecycler)
    {
        List<TaskRecycler> value = taskRecyclerLiveData.getValue();
        value.remove(taskRecycler);
        this.taskRecyclerLiveData = new MutableLiveData<>(value);
    }

    private LiveData<List<CustomQuizModel>>  getTasks()
    {
        List<CustomQuizModel> customQuizModels = new ArrayList<>();
        List<Task> values = TASK_REPOSITORY.getTasks().getValue();

        if(values.size()>0){
            for(Task task : values) {
                task = changePlaceOfGoodAnswers(task);
                customQuizModels.add(TaskToCustomQuizModel(task));
            }
        }

        return new MutableLiveData<>(customQuizModels);
    }

    private LiveData<CustomQuizModel> getTask(String uuid)
    {
        Task task = changePlaceOfGoodAnswers(TASK_REPOSITORY.getTaskByUUID(uuid).getValue());

        CustomQuizModel customQuizModel = TaskToCustomQuizModel(task);

        return new MutableLiveData<>(customQuizModel);
    }

    private CustomQuizModel TaskToCustomQuizModel(Task task)
    {
        return new CustomQuizModel(0
            ,task.getQuestions().size()
            ,task.getNumberOfFields()
            ,task.getTimerValue()
            ,task.getTopic()
            ,task.getDescription()
            ,task.getQuestions()
            ,task.getPositiveNumbers()
            ,task.getAnswers());
    }

    private Task changePlaceOfGoodAnswers(Task task)
    {
        List<Integer> postitiveNumbers = task.getPositiveNumbers();
        List<List<String>> answersList = task.getAnswers();
        List<String> answers = new ArrayList<>();

        int numberOfFields = task.getNumberOfFields();
        int postitiveNumber;
        int newPositiveNumber;
        String goodAnswer = "";

        for(int i = 0; i < postitiveNumbers.size(); i++)
        {
            //init values
            postitiveNumber = postitiveNumbers.get(i);
            newPositiveNumber = getNewPositiveNumber(numberOfFields,postitiveNumber);
            answers = answersList.get(i);
            goodAnswer = answers.get(postitiveNumber);
            //swap
            answers.set(postitiveNumber,answers.get(newPositiveNumber));
            answers.set(newPositiveNumber,goodAnswer);
            //change list
            answersList.set(i,answers);
            postitiveNumbers.set(i,newPositiveNumber);
        }

        task.setAnswers(answersList);
        task.setPositiveNumbers(postitiveNumbers);

        return task;
    }

    private int getNewPositiveNumber(int numberOfFields, int positivenumber)
    {
        Random random = new Random();
        boolean rand = true;
        int newPositiveNumber = 0;

        while(rand)
        {
            newPositiveNumber = random.nextInt(numberOfFields);
            if(newPositiveNumber != positivenumber)
            {
                rand = false;
            }
        }

        return newPositiveNumber;
    }
}