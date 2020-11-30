package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import java.util.List;
import java.util.Map;

public interface QuizModelInteface {

    public List<String> getQuestions();
    public List<Integer> getPositiveNumbers();
    public List<List<String>> getListAnswers();

    public int getCurrentNumber();
    public int getMaxNumber();
    public int getPercent();
    public int getBlockNumber();

    public Map<Integer, Boolean> getAnwsers();
    public void addAnwsers(Integer index, boolean score);


    public void setCurrentNumber(int currentNumber);




}
