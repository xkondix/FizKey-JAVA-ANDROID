package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuizViewModel extends AndroidViewModel {

    private int currentNumber, maxNumber, percent, blockNumber;
    private List<Question> dataQuestionAnwser;
    private Map<Integer, Boolean> anwsers;
    private List<String> activePhenomena;


    public QuizViewModel(@NonNull Application application) {
        super(application);

        anwsers = new TreeMap<>();
        activePhenomena = new ArrayList<>();
        dataQuestionAnwser = new ArrayList<>();
    }


    public List<String> getActivePhenomena() {
        return activePhenomena;
    }

    public void setActivePhenomena(List<String> activePhenomena) {
        this.activePhenomena = activePhenomena;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public List<Question> getDataQuestionAnwser() {
        return dataQuestionAnwser;
    }

    public void setDataQuestionAnwser(List<Question> dataQuestionAnwser) {
        this.dataQuestionAnwser = dataQuestionAnwser;
    }

    public Map<Integer, Boolean> getAnwsers() {
        return anwsers;
    }

    public void setAnwsers(Map<Integer, Boolean> anwsers) {
        this.anwsers = anwsers;
    }
}