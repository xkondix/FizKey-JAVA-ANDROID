package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import java.util.List;

public interface QuizModelInteface {


    public List<String> getQuestions();
    public List<Integer> getPositiveNumbers();
    public List<List<String>> getListAnswers();

    public int getCurrentlyNumber();
    public int getMaxNumber();
    public int getNumberOfFields();
    public int getTimerValue();



    public void setCurrentlyNumber(int currentlyNumber);





}
