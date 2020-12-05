package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Group implements Serializable {

    private String uuid;
    private String uuidTeacher;
    private String nameOfGroup;
    private List<String> uuidStudents;
    private Map<String, Map<String,String>> studentGrades;
}
