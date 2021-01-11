package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Task;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.TaskRepositoryInterface;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements TaskRepositoryInterface {

    //private final static FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private final static CollectionReference taskRef
            = FirestoreInstance.FIREBASE_FIRESTORE_INSTANCE.collection("tasks");

    private static TaskRepositoryInterface taskRepository;

    public static TaskRepositoryInterface getInstance()
    {
        if(taskRepository == null)
        {
            taskRepository = new TaskRepository();
        }

        return taskRepository;
    }

    private MutableLiveData<List<Task>> tasksMutableLiveData = new MutableLiveData<>();
    private List<Task> tasks = new ArrayList<>();


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

        if (tasks.size() == 0)
        {
            System.out.println("task = 0");
            onLoadTasks();
        }

        tasksMutableLiveData.setValue(tasks);

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

    public void onLoadTasks()
    {

        taskRef.orderBy("data").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("onLoadTasks", "listen:error", e);
                    return;
                }

                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        Task task = dc.getDocument().toObject(Task.class);
                        if(task.isForAll() == true) {
                            tasks.add(task);
                        }
                    }
                }

                tasksMutableLiveData.postValue(tasks);
            }

        });
    }

}
