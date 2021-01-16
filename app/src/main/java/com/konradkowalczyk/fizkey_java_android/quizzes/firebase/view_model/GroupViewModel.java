package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    private MutableLiveData<List<CustomQuizModel>> tasksDoneCurrentlyUserMutableLiveData;
    private MutableLiveData<Map<CustomQuizModel, Map<String, QuizResults>>> tasksAndGradesCurrentlyUserMutableLiveData;

    private MutableLiveData<CustomQuizModel> currentlyCustomQuizModelMutableLiveData;

    private MutableLiveData<Boolean> isGradesMutableLiveData;




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
        if (tasksDoneCurrentlyUserMutableLiveData == null) {
            tasksDoneCurrentlyUserMutableLiveData = new MutableLiveData<>();
        }
        if (tasksAndGradesCurrentlyUserMutableLiveData == null) {
            tasksAndGradesCurrentlyUserMutableLiveData = new MutableLiveData<>();
        }
        if (currentlyCustomQuizModelMutableLiveData == null) {
            currentlyCustomQuizModelMutableLiveData = new MutableLiveData<>();
        }
        if (isGradesMutableLiveData == null) {
            isGradesMutableLiveData = new MutableLiveData<>(false);
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

    public LiveData<List<CustomQuizModel>> getTasksDoneCurrentlyUserMutableLiveData() {
        return tasksDoneCurrentlyUserMutableLiveData;
    }

    public LiveData<Map<CustomQuizModel, Map<String, QuizResults>>> getTasksAndGradesCurrentlyUserMutableLiveData() {
        return tasksAndGradesCurrentlyUserMutableLiveData;
    }

    public void setTasksAndGradesCurrentlyUserMutableLiveData(String userUuid)
    {
        Map<String, Map<String, QuizResults>> tasksAndGrades = groupLiveData.getValue().getStudentGrades().get(userUuid);

        System.out.println(tasksAndGrades);
        if(tasksAndGrades == null)
        {
            return;
        }

        Map<CustomQuizModel,Map<String, QuizResults>> userCustomQuizModelsAndGrades = new HashMap<>();

        List<CustomQuizModel> tasksToDoCurrentlyUser = new ArrayList<>();
        List<CustomQuizModel> tasksDoneCurrentlyUser = new ArrayList<>();

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
                    tasksToDoCurrentlyUserMutableLiveData.postValue(tasksToDoCurrentlyUser);
                }
                else {
                    Map<String, QuizResults> gradues = new HashMap<>();
                    for(Map.Entry<String, QuizResults> gradue : mapEntry.getValue().entrySet()){
                        gradues.put(gradue.getKey(), (QuizResults) gradue.getValue());
                    }
                    userCustomQuizModelsAndGrades.put(customQuizModel, gradues);
                    tasksDoneCurrentlyUser.add(customQuizModel);
                    tasksDoneCurrentlyUserMutableLiveData.postValue(tasksDoneCurrentlyUser);
                }

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


    public void setCurrentlyCustomQuizModelLiveData(MutableLiveData<CustomQuizModel> currentlyCustomQuizModelMutableLiveData) {
        this.currentlyCustomQuizModelMutableLiveData = currentlyCustomQuizModelMutableLiveData;
    }

    public void updateGrades(String userUuid, QuizResults quizResults)
    {
        Group group = groupLiveData.getValue();
        group.addGradesToTask(userUuid,currentlyCustomQuizModelMutableLiveData.getValue().getUuid(), quizResults);
        groupRepository.updateGroup(group);
        removeTasksToDo();

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

    private void removeTasksToDo()
    {
        CustomQuizModel customQuizModel = currentlyCustomQuizModelMutableLiveData.getValue();
        List<CustomQuizModel> customQuizModels = tasksToDoCurrentlyUserMutableLiveData.getValue();
        customQuizModels.remove(customQuizModel);
        tasksToDoCurrentlyUserMutableLiveData.postValue(customQuizModels);
    }

    public LiveData<Boolean> getIsGrades() {
        return isGradesMutableLiveData;
    }

    public void setIsGrades(boolean isGrades) {
       isGradesMutableLiveData.setValue(isGrades);
    }

    public List<String> getCurrentlyDateOfTask()
    {
        return new ArrayList<>(tasksAndGradesCurrentlyUserMutableLiveData.getValue()
                .get(currentlyCustomQuizModelMutableLiveData.getValue()).keySet());
    }

    public QuizResults getyQuizResultOfTaskAndDate(String date)
    {
        return tasksAndGradesCurrentlyUserMutableLiveData.getValue()
                .get(currentlyCustomQuizModelMutableLiveData.getValue()).get(date);
    }

    public List<String> getNamesOfUsers()
    {
        List<String> students = new ArrayList<>();
        for(User user : usersMutableLiveData.getValue())
        {
            students.add(user.getNameAndSurname());
        }

        return students;
    }

    public void onDestroy()
    {
        tasksToDoCurrentlyUserMutableLiveData.setValue(null);
        tasksDoneCurrentlyUserMutableLiveData.setValue(null);
        tasksAndGradesCurrentlyUserMutableLiveData.setValue(null);
    }
}



