package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository;

import androidx.lifecycle.MutableLiveData;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Task;

import java.util.List;

public interface TaskRepositoryInterface {

    public MutableLiveData<Task> getTaskByUUID(String uuid);
    public MutableLiveData<List<Task>> getTasks();

    public void insertTask(Task task);

}

