package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskRecycler;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Task;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.TaskRepositoryInterface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

public class TaskViewModel extends ViewModel {

    private MutableLiveData<List<TaskRecycler>> taskRecyclerLiveData;
    private MutableLiveData<CustomQuizModel> customQuizModelLiveData;
    private MutableLiveData<List<CustomQuizModel>> customQuizModelsLiveData;
    private MutableLiveData<Integer> positionMutableLiveData;

    private TaskRepositoryInterface taskRepository;

    public void init()
    {
        if(taskRepository == null) {
            taskRepository = TaskRepository.getInstance();
        }
        if(taskRecyclerLiveData == null) {
            taskRecyclerLiveData = new MutableLiveData<>(new ArrayList<>());
        }
        if(customQuizModelLiveData == null) {
            customQuizModelLiveData = new MutableLiveData<>(new CustomQuizModel());
        }
        if(customQuizModelsLiveData == null) {
            customQuizModelsLiveData = new MutableLiveData<>();
        }
        if(positionMutableLiveData == null) {
            positionMutableLiveData = new MutableLiveData<>(-1);
        }

        getTasks();

    }


    public TaskViewModel() {
        super();
    }


    //TaskRecycler

    public LiveData<List<TaskRecycler>> getTaskRecyclerLiveData() {
        if (taskRecyclerLiveData == null) {
            taskRecyclerLiveData = new MutableLiveData<>(new ArrayList<TaskRecycler>());
        }

        return  taskRecyclerLiveData;
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
        value.set(positionMutableLiveData.getValue(),taskRecycler);
        taskRecyclerLiveData.setValue(value);
    }

    //Position

    public LiveData<Integer> getPosition() {
        return positionMutableLiveData;
    }

    public void setPosition(int position) {
        this.positionMutableLiveData.setValue(position);
    }


    //CustomQuizModel

    public LiveData<CustomQuizModel> getCustomQuizModelLiveData() {
        return customQuizModelLiveData;
    }

    public void setCustomQuizModelLiveData(CustomQuizModel customQuizModel) {
        this.customQuizModelLiveData.setValue(customQuizModel);
    }

    public void setCustomQuizModelLiveData(String uuid) {
        this.customQuizModelLiveData.setValue(getTask(uuid));
    }

    private CustomQuizModel getTask(String uuid)
    {

        Task task = changePlaceOfGoodAnswers(taskRepository.getTaskByUUID(uuid).getValue());
        CustomQuizModel customQuizModel = TaskToCustomQuizModel(task);

        return customQuizModel;
    }



    //CustomQuizModels

    public LiveData<List<CustomQuizModel>> getCustomQuizModelsLiveData() {
        return getTasks();
    }



    public LiveData<List<CustomQuizModel>> getTasks()
    {
        List<CustomQuizModel> customQuizModels = new ArrayList<>();
        List<Task> values = taskRepository.getTasks().getValue();

        if(values!= null) {
            for (Task task : values) {
                task = changePlaceOfGoodAnswers(task);
                customQuizModels.add(TaskToCustomQuizModel(task));
            }
        }

        customQuizModelsLiveData.setValue(customQuizModels);

        return customQuizModelsLiveData;
    }



    //Task

    public String insertTask(boolean forAll)
    {
        List<TaskRecycler> values = taskRecyclerLiveData.getValue();
        List<List<String>> answers = new ArrayList<>();
        List<String> questions = new ArrayList<>();
        List<Integer> positivNumbers = new ArrayList<>();
        String uuid = UUID.randomUUID().toString();

        for(TaskRecycler value : values)
        {
            answers.add(value.getListAnswers());
            questions.add(value.getQuestion());
            positivNumbers.add(0);
        }

        taskRepository.insertTask(new Task(customQuizModelLiveData.getValue().getTopic()
                ,customQuizModelLiveData.getValue().getDescripcion()
                ,questions
                ,fromNestedListtoMap(answers)
                ,positivNumbers
                , new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()).toString()
                ,customQuizModelLiveData.getValue().getNumberOfFields()
                ,customQuizModelLiveData.getValue().getTimerValue()
                , uuid
                , forAll
        ));

        return uuid;
    }

    public static CustomQuizModel TaskToCustomQuizModel(Task task)
    {
        CustomQuizModel customQuizModel = new CustomQuizModel(0
                , task.getQuestions().size()
                , task.getNumberOfFields()
                , task.getTimerValue()
                , task.getTopic()
                , task.getDescription()
                , task.getQuestions()
                , task.getPositiveNumbers()
                , fromMapToNestedList(task.getAnswers())
                , task.getData());


        return customQuizModel;
    }

    private Task changePlaceOfGoodAnswers(Task task)
    {
        List<Integer> postitiveNumbers = task.getPositiveNumbers();
        List<List<String>> answersList = fromMapToNestedList(task.getAnswers());
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

        task.setAnswers(fromNestedListtoMap(answersList));
        task.setPositiveNumbers(postitiveNumbers);

        return task;
    }

    public static CustomQuizModel shuffle(CustomQuizModel customQuizModel)
    {
        List<Integer> postitiveNumbers = customQuizModel.getPositiveNumbers();
        List<List<String>> answersList = customQuizModel.getListAnswers();
        List<String> answers = new ArrayList<>();

        int numberOfFields = customQuizModel.getNumberOfFields();
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

        customQuizModel.setListAnswers(answersList);
        customQuizModel.setPositiveNumbers(postitiveNumbers);

        return customQuizModel;
    }

    private static int getNewPositiveNumber(int numberOfFields, int positivenumber)
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

    private Map<String, List<String>> fromNestedListtoMap(List<List<String>> list)
    {
        Map map = new TreeMap();
        for(int i = 0; i < list.size(); i++)
        {
            map.put(i+"",list.get(i));
        }

        return map;
    }

    private static List<List<String>> fromMapToNestedList(Map<String, List<String>> map)
    {
        List<List<String>> list = new ArrayList<>();

        for( List<String> answers : map.values())
        {
            list.add(answers);
        }

        return list;
    }
}