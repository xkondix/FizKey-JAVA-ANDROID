package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuizModel implements Parcelable {

    private int currentNumber, maxNumber, percent, blockNumber;
    private Map<Integer, Boolean> anwsers;
    private List<String> activePhenomena;


    public QuizModel() {
        anwsers = new TreeMap<>();
        activePhenomena = new ArrayList<>();
        maxNumber = 5;
        blockNumber = 2;
        currentNumber = 0;
        percent = 0;
    }


    protected QuizModel(Parcel in) {
        currentNumber = in.readInt();
        maxNumber = in.readInt();
        percent = in.readInt();
        blockNumber = in.readInt();
        activePhenomena = in.createStringArrayList();
    }

    public static final Creator<QuizModel> CREATOR = new Creator<QuizModel>() {
        @Override
        public QuizModel createFromParcel(Parcel in) {
            return new QuizModel(in);
        }

        @Override
        public QuizModel[] newArray(int size) {
            return new QuizModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentNumber);
        dest.writeInt(maxNumber);
        dest.writeInt(percent);
        dest.writeInt(blockNumber);
        dest.writeStringList(activePhenomena);
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

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Map<Integer, Boolean> getAnwsers() {
        return anwsers;
    }

    public void setAnwsers(Map<Integer, Boolean> anwsers) {
        this.anwsers = anwsers;
    }

    public List<String> getActivePhenomena() {
        return activePhenomena;
    }

    public void setActivePhenomena(List<String> activePhenomena) {
        this.activePhenomena = activePhenomena;
    }


}