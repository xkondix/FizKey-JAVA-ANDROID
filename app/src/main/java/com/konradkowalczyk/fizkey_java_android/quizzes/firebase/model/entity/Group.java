package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import com.google.firebase.firestore.DocumentReference;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizResults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group implements Serializable {

    private String uuid;
    private DocumentReference author;
    private String authorUUID;
    private String nameOfGroup;
    private String description;
    private List<DocumentReference> tasks;
    private List<DocumentReference> students;
    // { User : { Task : {Data : QuizResult} } }
    private Map<String, Map<String, Map<String,QuizResults>>> studentGrades;

    public Group(String uuid, DocumentReference author, String authorUUID, String nameOfGroup, String description, List<DocumentReference> tasks, List<DocumentReference> students, Map<String, Map<String, Map<String, QuizResults>>> studentGrades) {
        this.uuid = uuid;
        this.author = author;
        this.authorUUID = authorUUID;
        this.nameOfGroup = nameOfGroup;
        this.description = description;
        this.tasks = tasks;
        this.students = students;
        this.studentGrades = studentGrades;
    }

    public Group(DocumentReference author, String authorUUID, String nameOfGroup, String description) {
        this.author = author;
        this.nameOfGroup = nameOfGroup;
        this.description = description;
        this.authorUUID = authorUUID;
        this.students = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.studentGrades = new HashMap<>();
    }

    private Group(){}


    public String getUuid() {
        return uuid;
    }

    public DocumentReference getAuthor() {
        return author;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthorUUID() {
        return authorUUID;
    }

    public List<DocumentReference> getTasks() {
        return tasks;
    }

    public List<DocumentReference> getStudents() {
        return students;
    }

    public Map<String, Map<String, Map<String,QuizResults>>> getStudentGrades() {
        return studentGrades;
    }

    public void addToGroup(DocumentReference documentReference)
    {
        students.add(documentReference);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setStudents(List<DocumentReference> students) {
        this.students = students;
    }

    public void setStudentGrades(Map<String, Map<String , Map<String,QuizResults>>> studentGrades) {
        this.studentGrades = studentGrades;
    }

    public void addNewUser(String userUuid) {
        this.studentGrades.put(userUuid, new HashMap<>());
    }

    public void addTaskToGroup(DocumentReference documentReference)
    {
        tasks.add(documentReference);
    }

    public void addTaskToStudents(String taskUuid)
    {
        Map<String, Map<String, Map<String, QuizResults>>> tasksUsers = new HashMap<>();
        for( Map.Entry<String, Map<String, Map<String, QuizResults>>> tasksAndGrades: studentGrades.entrySet())
        {
            Map<String, Map<String, QuizResults>> tasks = tasksAndGrades.getValue();
            tasks.put(taskUuid, new HashMap<>());
            tasksUsers.put(tasksAndGrades.getKey(),tasks);
        }
        this.studentGrades = tasksUsers;
    }


}
