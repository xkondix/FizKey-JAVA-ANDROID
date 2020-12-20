package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.dao;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Task;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.TaskRepositoryInterface;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements TaskRepositoryInterface {

    private final static FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private final static CollectionReference taskRef = rootRef.collection("tasks");

    private MutableLiveData<List<Task>> tasksMutableLiveData = new MutableLiveData<>();



    @Override
    public MutableLiveData<Task> getTaskByUUID(String uuid) {

        MutableLiveData<Task> taskMutableLiveData = new MutableLiveData<>();


        DocumentReference uuidRef = taskRef.document(uuid);
        uuidRef.get().addOnCompleteListener(uuidTask -> {
            if (uuidTask.isSuccessful()) {
                DocumentSnapshot document = uuidTask.getResult();
                if (!document.exists()) {
                    Log.i("getTaskByUUID", "Document not exists");
                    taskMutableLiveData.setValue(null);
                } else {
                    Log.i("getTaskByUUID", "Document exists");
                    Task task = document.toObject(Task.class);
                    taskMutableLiveData.setValue(task);
                }
            } else {
                Log.i("getTaskByUUID", "ref uuid error");
            }
        });

        return taskMutableLiveData;
    }

    @Override
    public MutableLiveData<List<Task>> getTasks() {


        taskRef.get().addOnCompleteListener(tasks -> {
            List<Task> list = new ArrayList<>();
            if (tasks.isSuccessful()) {
                List<DocumentSnapshot> tasksList = tasks.getResult().getDocuments();

                for (DocumentSnapshot document : tasksList) {
                    list.add(document.toObject(Task.class));
                }
            }

            tasksMutableLiveData.setValue(list);

        });

        return tasksMutableLiveData;

    }

    @Override
    public void insertTask(Task task) {

        DocumentReference uuidRef = taskRef.document(task.getUuid());
        uuidRef.get().addOnCompleteListener(uuidTask -> {
            if (uuidTask.isSuccessful()) {
                DocumentSnapshot document = uuidTask.getResult();
                if (!document.exists()) {
                    Log.i("insertTask", "Document not exists");
                    uuidRef.set(task).addOnCompleteListener(userCreationTask -> {
                        if (userCreationTask.isSuccessful()) {
                            Log.i("insertTask", "User document create");
                        } else {
                            Log.i("insertTask", "User document not create");
                        }
                    });
                } else {
                    Log.i("insertTask", "Document exists");
                }
            } else {
                Log.i("insertTask", "ref uuid error");
            }
        });

    }





}
