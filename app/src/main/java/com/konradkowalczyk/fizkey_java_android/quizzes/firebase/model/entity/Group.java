package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Group implements Serializable {

    private String uuid;
    private DocumentReference author;
    private String authorUUID;
    private String nameOfGroup;
    private String description;
    private List<DocumentReference> students;
    // { User : { Task : List<{Data : QuizResult}> } }
    private Map<DocumentReference, Map<DocumentReference, List<Map<String,Object>>>> studentGrades;

    public Group(String uuid, DocumentReference author, String authorUUID,  String nameOfGroup, String description, List<DocumentReference> students, Map<DocumentReference, Map<DocumentReference, List<Map<String,Object>>>> studentGrades) {
        this.uuid = uuid;
        this.author = author;
        this.nameOfGroup = nameOfGroup;
        this.students = students;
        this.studentGrades = studentGrades;
        this.description = description;
        this.authorUUID = authorUUID;

    }

    public Group(DocumentReference author, String authorUUID, String nameOfGroup, String description) {
        this.author = author;
        this.nameOfGroup = nameOfGroup;
        this.description = description;
        this.authorUUID = authorUUID;
    }

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

    public List<DocumentReference> getStudents() {
        return students;
    }

    public Map<DocumentReference, Map<DocumentReference, List<Map<String,Object>>>> getStudentGrades() {
        return studentGrades;
    }

    public void addToGroup(DocumentReference documentReference)
    {
        students.add(documentReference);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


}
