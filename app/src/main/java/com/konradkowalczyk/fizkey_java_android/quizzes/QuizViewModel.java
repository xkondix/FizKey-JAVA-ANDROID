package com.konradkowalczyk.fizkey_java_android.quizzes;

import java.util.List;
import java.util.Map;

public class QuizViewModel {

    private int currentNumber = 0;
    private int maxNumber;
    private Map<String,Map<Integer, List<Double>>> dataQuestionAnwser;
    private Map<Integer,Boolean> anwser;
    private double percent;


    public QuizViewModel(int maxNumber)
    {
        this.maxNumber = maxNumber;
    }

}
