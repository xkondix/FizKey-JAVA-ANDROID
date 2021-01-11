package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Task;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.interface_repository.GroupRepositoryInteface;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.FirestoreInstance;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.GroupRepository;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizResults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupViewModel extends ViewModel {

    private GroupRepositoryInteface groupRepository;

    private MutableLiveData<DocumentReference> createReferenceLiveData;
    private MutableLiveData<DocumentReference> addToGroupLiveData;
    private MutableLiveData<Group> groupLiveData;
    private MutableLiveData<List<User>> usersMutableLiveData;

    private MutableLiveData<List<CustomQuizModel>> tasksToDoCurrentlyUserMutableLiveData;
    private MutableLiveData<List<CustomQuizModel>> tasksCurrentlyUserMutableLiveData;
    private MutableLiveData<Map<CustomQuizModel, Map<String, QuizResults>>> tasksAndGradesCurrentlyUserMutableLiveData;

    private MutableLiveData<CustomQuizModel> currentlyCustomQuizModelMutableLiveData;




    public GroupViewModel() {
        super();
    }

    public void init() {
        if (groupRepository == null) {
            groupRepository = GroupRepository.getInstance();
        }
        if (createReferenceLiveData == null) {
            createReferenceLiveData = new MutableLiveData<>();
        }
        if (groupLiveData == null) {
            groupLiveData = new MutableLiveData<>();
        }
        if (addToGroupLiveData == null) {
            addToGroupLiveData = new MutableLiveData<>();
        }
        if (usersMutableLiveData == null) {
            usersMutableLiveData = new MutableLiveData<>();
        }
        if (tasksToDoCurrentlyUserMutableLiveData == null) {
            tasksToDoCurrentlyUserMutableLiveData = new MutableLiveData<>();
        }
        if (tasksCurrentlyUserMutableLiveData == null) {
            tasksCurrentlyUserMutableLiveData = new MutableLiveData<>();
        }
        if (tasksAndGradesCurrentlyUserMutableLiveData == null) {
            tasksAndGradesCurrentlyUserMutableLiveData = new MutableLiveData<>();
        }
        if (currentlyCustomQuizModelMutableLiveData == null) {
            currentlyCustomQuizModelMutableLiveData = new MutableLiveData<>();
        }


    }

    public LiveData<Group> getGroupLiveData() {
        return groupLiveData;
    }

    public void getGroupsByUUID(String groupUuid) {
        groupLiveData = groupRepository.getGroupByUUID(groupUuid);
    }

    public LiveData<DocumentReference> getCreateReferenceLiveData() {
        return createReferenceLiveData;
    }

    public LiveData<List<User>> getUsersMutableLiveData() {
        return usersMutableLiveData;
    }

    public LiveData<DocumentReference> getAddToGroupLiveData() {
        return addToGroupLiveData;
    }

    public void insertGroup(Group group) {
        createReferenceLiveData = groupRepository.insertGroup(group);
    }
    public void joinWithEntryCodetoGroup(String groupUuid, User user)
    {
        addToGroupLiveData = groupRepository.addToGroup(groupUuid,user);
    }

    public void setUsers(){
        List<User> users = new ArrayList<>();
        for (DocumentReference documentReference : groupLiveData.getValue().getStudents()) {
            documentReference.get().addOnCompleteListener(groupDocument -> {
                if (groupDocument.isSuccessful()) {
                    DocumentSnapshot document = groupDocument.getResult();
                    User user = document.toObject(User.class);
                    users.add(user);
                }

                Collections.sort(users);
                usersMutableLiveData.postValue(users);

            });
        }
    }

    public LiveData<List<CustomQuizModel>> getTasksToDoCurrentlyUserMutableLiveData() {
        return tasksToDoCurrentlyUserMutableLiveData;
    }

    public LiveData<List<CustomQuizModel>> getTasksCurrentlyUserMutableLiveData() {
        return tasksCurrentlyUserMutableLiveData;
    }

    public LiveData<Map<CustomQuizModel, Map<String, QuizResults>>> getTasksAndGradesCurrentlyUserMutableLiveData() {
        return tasksAndGradesCurrentlyUserMutableLiveData;
    }

    public void setTasksAndGradesCurrentlyUserMutableLiveData(String userUuid)
    {
        Map<String, Map<String, QuizResults>> tasksAndGrades = groupLiveData.getValue().getStudentGrades().get(userUuid);

        if(tasksAndGrades == null)
        {
            return;
        }

        Map<CustomQuizModel,Map<String, QuizResults>> userCustomQuizModelsAndGrades = new HashMap<>();
        List<CustomQuizModel> tasksToDoCurrentlyUser = new ArrayList<>();
        List<CustomQuizModel> tasksCurrentlyUser = new ArrayList<>();

        List<DocumentReference> tasks = groupLiveData.getValue().getTasks();
        List<String> uuids = doumentReferenceToUuidStrings(tasks);

        for(Map.Entry<String, Map<String, QuizResults>> mapEntry : tasksAndGrades.entrySet())
        {

            tasks.get(uuids.indexOf(mapEntry.getKey())).get().addOnCompleteListener(taskDocument -> {
            if (taskDocument.isSuccessful()) {
                DocumentSnapshot document = taskDocument.getResult();
                Task task = document.toObject(Task.class);
                CustomQuizModel customQuizModel = TaskViewModel.TaskToCustomQuizModel(task);
                customQuizModel.setUuid(task.getUuid());

                if(mapEntry.getValue().isEmpty()) {
                    userCustomQuizModelsAndGrades.put(customQuizModel, new HashMap<>());
                    tasksToDoCurrentlyUser.add(customQuizModel);
                    tasksToDoCurrentlyUserMutableLiveData.postValue(tasksCurrentlyUser);
                }
                else {

                    Map<String, QuizResults> gradues = new HashMap<>();
                    for(Map.Entry<String, QuizResults> gradue : mapEntry.getValue().entrySet()){
                        gradues.put(gradue.getKey(), (QuizResults) gradue.getValue());
                    }
                    userCustomQuizModelsAndGrades.put(customQuizModel, gradues);
                }

                tasksCurrentlyUser.add(customQuizModel);
                tasksCurrentlyUserMutableLiveData.postValue(tasksCurrentlyUser);
                tasksAndGradesCurrentlyUserMutableLiveData.postValue(userCustomQuizModelsAndGrades);
                }
            });
        }
    }

    private List<String> doumentReferenceToUuidStrings(List<DocumentReference> documentReferences)
    {
        List<String> uuidTasks = new ArrayList<>();
        for(DocumentReference documentReference : documentReferences)
        {
            uuidTasks.add(documentReference.getId());
        }

        return uuidTasks;
    }

    public void updateGrades(QuizResults quizResults)
    {

        Map<CustomQuizModel, Map<String, QuizResults>> tasksAndGrades
                = tasksAndGradesCurrentlyUserMutableLiveData.getValue();

        Map<String, QuizResults> grades =
                tasksAndGrades.get(currentlyCustomQuizModelMutableLiveData.getValue());

        grades.put(Timestamp.now().toString(), quizResults);

        tasksAndGrades.put(currentlyCustomQuizModelMutableLiveData.getValue(), grades);

        tasksAndGradesCurrentlyUserMutableLiveData.postValue(tasksAndGrades);


    }

    public void setCurrentlyCustomQuizModelLiveData(MutableLiveData<CustomQuizModel> currentlyCustomQuizModelMutableLiveData) {
        this.currentlyCustomQuizModelMutableLiveData = currentlyCustomQuizModelMutableLiveData;
    }

    public void updateGroup(String userUuid)
    {
        Map<String, Map<String, Map<String, QuizResults>>> usersTasksAndGrades = groupLiveData.getValue().getStudentGrades();
        usersTasksAndGrades.put(userUuid, CustomQuizModelsToTasksUuid());
        Group group = groupLiveData.getValue();
        group.setStudentGrades(usersTasksAndGrades);
        groupRepository.updateGroup(group);
        groupLiveData.postValue(group);

    }


    private Map<String, Map<String, QuizResults>> CustomQuizModelsToTasksUuid()
    {
        Map<String, Map<String, QuizResults>> tasksAndGrades = new HashMap<>();
        for( Map.Entry<CustomQuizModel, Map<String, QuizResults>> tasksEntryMap
                : tasksAndGradesCurrentlyUserMutableLiveData.getValue().entrySet())
        {
            tasksAndGrades.put(tasksEntryMap.getKey().getUuid(), tasksEntryMap.getValue());
        }

        return tasksAndGrades;
    }

    public void updateAllGroupsByUuid(List<Group> groupNames, String taskUuid) {

        DocumentReference taskRef = FirestoreInstance.FIREBASE_FIRESTORE_INSTANCE
                .collection("tasks").document(taskUuid);

        for(Group group : groupNames)
        {
            group.addTaskToStudents(taskUuid);
            group.addTaskToGroup(taskRef);
            groupRepository.updateGroup(group);
        }
    }
}



