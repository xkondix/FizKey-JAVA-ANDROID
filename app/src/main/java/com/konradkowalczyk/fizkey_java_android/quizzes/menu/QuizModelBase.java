package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.Question;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizModelInteface;

import java.util.ArrayList;
import java.util.List;

public class QuizModelBase implements Parcelable, QuizModelInteface {

    private int currentNumber, maxNumber, blockNumber;

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
    }


    protected QuizModelBase(Parcel in) {
        currentNumber = in.readInt();
        maxNumber = in.readInt();
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
    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
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