package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.Question;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizModelInteface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuizModelBase implements Parcelable, QuizModelInteface {

    private int currentNumber, maxNumber, percent, blockNumber;

    private Map<Integer, Boolean> anwsers;

    private List<String> activePhenomena;
    private List<String> questions;
    private List<Integer> positiveNumbers;
    private List<List<String>> listAnswers;


    public QuizModelBase() {

        this.activePhenomena = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.positiveNumbers = new ArrayList<>();
        this.listAnswers = new ArrayList<>();

        this.maxNumber = 5;
        this.blockNumber = 2;
        this.currentNumber = 0;
        this.percent = 0;
    }


    protected QuizModelBase(Parcel in) {
        currentNumber = in.readInt();
        maxNumber = in.readInt();
        percent = in.readInt();
        blockNumber = in.readInt();
        activePhenomena = in.createStringArrayList();
        questions = in.createStringArrayList();
        positiveNumbers = new ArrayList<Integer>();
        in.readList(positiveNumbers, null);
        listAnswers = new ArrayList<List<String>>();
        in.readList(listAnswers, null);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentNumber);
        dest.writeInt(maxNumber);
        dest.writeInt(percent);
        dest.writeInt(blockNumber);
        dest.writeStringList(activePhenomena);
        dest.writeStringList(questions);
        dest.writeList(positiveNumbers);
        dest.writeList(listAnswers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizModelBase> CREATOR = new Creator<QuizModelBase>() {
        @Override
        public QuizModelBase createFromParcel(Parcel in) {
            return new QuizModelBase(in);
        }

        @Override
        public QuizModelBase[] newArray(int size) {
            return new QuizModelBase[size];
        }
    };

    public void setQuestion(List<Question> dataQuestions)
    {
        for(Question question: dataQuestions) {
            this.questions.add(question.getQuestion());
            this.listAnswers.add(question.getAnswers());
            this.positiveNumbers.add(question.getPositiveNumber());
        }
    }

    @Override
    public int getCurrentNumber() {
        return currentNumber;
    }

    @Override
    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Override
    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    @Override
    public Map<Integer, Boolean> getAnwsers() {
        return anwsers;
    }

    public void setAnwsers(Map<Integer, Boolean> anwsers) {
        this.anwsers = anwsers;
    }

    @Override
    public void addAnwsers(Integer index, boolean score)
    {
        if(anwsers == null)
        {
            anwsers = new TreeMap<>();
        }
        this.anwsers.put(index,score);
    }

    public List<String> getActivePhenomena() {
        return activePhenomena;
    }

    public void setActivePhenomena(List<String> activePhenomena) {
        this.activePhenomena = activePhenomena;
    }

    @Override
    public List<String> getQuestions() {
        return questions;
    }


    @Override
    public List<Integer> getPositiveNumbers() {
        return positiveNumbers;
    }


    @Override
    public List<List<String>> getListAnswers() {
        return listAnswers;
    }



}