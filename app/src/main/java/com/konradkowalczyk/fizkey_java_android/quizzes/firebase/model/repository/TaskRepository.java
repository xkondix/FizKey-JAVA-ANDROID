package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.dao.TaskDAO;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Task;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.TaskRepositoryInterface;

import java.util.List;

public class TaskRepository implements TaskRepositoryInterface {

    private final static TaskDAO TASK_DAO = new TaskDAO();


    @Override
    public MutableLiveData<Task> getTaskByUUID(String uuid) {
        return TASK_DAO.getTaskByUUID(uuid);
    }

    @Override
    public MutableLiveData<List<Task>> getTasks() {
        System.out.println(TASK_DAO.getTasks().getValue());
        MutableLiveData<List<Task>> tasks = TASK_DAO.getTasks();
        return tasks;
    }

    @Override
    public void insertTask(Task task) {
        new InsertTaskAsyncTask().execute(task);
    }



    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private InsertTaskAsyncTask() {}
        @Override
        protected Void doInBackground(Task... tasks) {
            TASK_DAO.insertTask(tasks[0]);
            return null;
        }
    }
}
