package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import java.util.List;

public class Task {

    private String uuid;
    private List<String> questions;
    private List<List<String>> answers;
    private List<String> goodAnswers;
}
