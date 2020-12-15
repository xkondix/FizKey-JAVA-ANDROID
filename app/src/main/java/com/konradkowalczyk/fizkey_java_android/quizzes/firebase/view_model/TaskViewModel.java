package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskRecycler;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Task;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TaskViewModel extends ViewModel {

    private MutableLiveData<List<TaskRecycler>> taskRecyclerLiveData;
    private MutableLiveData<CustomQuizModel> customQuizModelLiveData;
    private MutableLiveData<List<CustomQuizModel>> customQuizModelsLiveData;
    private MutableLiveData<Integer> position = new MutableLiveData<>(-1);

    private final static TaskRepository TASK_REPOSITORY = new TaskRepository();


    public TaskViewModel() {
        super();
        customQuizModelLiveData =  new MutableLiveData<CustomQuizModel>(new CustomQuizModel());
//        customQuizModelsLiveData = getTasks();
    }


    public LiveData<List<TaskRecycler>> getTaskRecyclerLiveData() {
        if (taskRecyclerLiveData == null) {
            taskRecyclerLiveData = new MutableLiveData<>(new ArrayList<TaskRecycler>());
        }

        return  taskRecyclerLiveData;
    }

    public void insertTask()
    {
        List<TaskRecycler> values = taskRecyclerLiveData.getValue();
        List<List<String>> answers = new ArrayList<>();
        List<String> questions = new ArrayList<>();
        List<Integer> positivNumbers = new ArrayList<>();

        for(TaskRecycler value : values)
        {
            answers.add(value.getListAnswers());
            questions.add(value.getQuestion());
            positivNumbers.add(0);
        }

        TASK_REPOSITORY.insertTask(new Task(customQuizModelLiveData.getValue().getTopic()
                ,customQuizModelLiveData.getValue().getDescripcion()
                ,questions
                ,answers
                ,positivNumbers
                , new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()).toString()
                ,customQuizModelLiveData.getValue().getNumberOfFields()
                ,customQuizModelLiveData.getValue().getTimerValue()
                ,UUID.randomUUID().toString()
                ));
    }

    public MutableLiveData<Integer> getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position.setValue(position);
    }

    public MutableLiveData<CustomQuizModel> getCustomQuizModelLiveData() {
        return customQuizModelLiveData;
    }

    public void setCustomQuizModelLiveData(CustomQuizModel customQuizModel) {
        if (customQuizModelLiveData == null) {
            customQuizModelLiveData =  new MutableLiveData<CustomQuizModel>(customQuizModel);
        }
        this.customQuizModelLiveData.setValue(customQuizModel);
    }

    public LiveData<List<CustomQuizModel>> getCustomQuizModelsLiveData() {
        return customQuizModelsLiveData;
    }

    public void setCustomQuizModelsLiveData(MutableLiveData<List<CustomQuizModel>> customQuizModelsLiveData) {
        this.customQuizModelsLiveData = customQuizModelsLiveData;
    }

    public void removeFromList(TaskRecycler taskRecycler)
    {
        List<TaskRecycler> value = taskRecyclerLiveData.getValue();
        value.remove(taskRecycler);
        this.taskRecyclerLiveData.setValue(value);
    }

    public void addToList(TaskRecycler taskRecycler)
    {
        List<TaskRecycler> value = taskRecyclerLiveData.getValue();
        value.add(taskRecycler);
        this.taskRecyclerLiveData.setValue(value);
    }

    public void changeList(TaskRecycler taskRecycler) {
        List<TaskRecycler> value = taskRecyclerLiveData.getValue();
        value.set(position.getValue(),taskRecycler);
        taskRecyclerLiveData.setValue(value);
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