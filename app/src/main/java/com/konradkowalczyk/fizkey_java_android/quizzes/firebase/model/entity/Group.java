package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Group implements Serializable {

    private String uuid;
    private String uuidTeacher;
    private String nameOfGroup;
    private List<DocumentReference> students;
    // { User : { Task : List<{Data : QuizResult}> } }
    private Map<DocumentReference, Map<DocumentReference, List<Map<String,Object>>>> studentGrades;

    public Group(String uuid, String uuidTeacher, String nameOfGroup, List<DocumentReference> students, Map<DocumentReference, Map<DocumentReference, List<Map<String,Object>>>> studentGrades) {
        this.uuid = uuid;
        this.uuidTeacher = uuidTeacher;
        this.nameOfGroup = nameOfGroup;
        this.students = students;
        this.studentGrades = studentGrades;
    }

    public Group(String uuidTeacher, String nameOfGroup) {
        this.uuidTeacher = uuidTeacher;
        this.nameOfGroup = nameOfGroup;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUuidTeacher() {
        return uuidTeacher;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public List<DocumentReference> getUuidStudents() {
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
