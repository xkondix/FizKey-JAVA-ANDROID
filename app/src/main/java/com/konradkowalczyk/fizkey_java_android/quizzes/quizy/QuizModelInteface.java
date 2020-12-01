package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import java.util.List;

public interface QuizModelInteface {


    public List<String> getQuestions();
    public List<Integer> getPositiveNumbers();
    public List<List<String>> getListAnswers();

    public int getCurrentNumber();
    public int getMaxNumber();
    public int getBlockNumber();


    public void setCurrentNumber(int currentNumber);




}
