package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.Question;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizModelInteface;

import java.util.ArrayList;
import java.util.List;

public class QuizModelBase implements Parcelable, QuizModelInteface {

    private int currentNumber, maxNumber, blockNumber, timerValue;

    private List<String> questions;
    private List<Integer> positiveNumbers;
    private List<List<String>> listAnswers;


    public QuizModelBase() {

        this.questions = new ArrayList<>();
        this.positiveNumbers = new ArrayList<>();
        this.listAnswers = new ArrayList<>();

        this.maxNumber = 5;
        this.blockNumber = 2;
        this.currentNumber = 0;
        this.timerValue = 0;
    }


    protected QuizModelBase(Parcel in) {
        timerValue = in.readInt();
        currentNumber = in.readInt();
        maxNumber = in.readInt();
        blockNumber = in.readInt();
        questions = in.createStringArrayList();
        positiveNumbers = new ArrayList<Integer>();
        in.readList(positiveNumbers, null);
        listAnswers = new ArrayList<List<String>>();
        in.readList(listAnswers, null);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(timerValue);
        dest.writeInt(currentNumber);
        dest.writeInt(maxNumber);
        dest.writeInt(blockNumber);
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
    public int getCurrentlyNumber() {
        return currentNumber;
    }

    @Override
    public void setCurrentlyNumber(int currentlyNumber) {
        this.currentNumber = currentlyNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Override
    public int getNumberOfFields() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
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

    @Override
    public int getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(int secounds) {
        this.timerValue = secounds;
    }

    public void restartQuizQuestionsData() {
        this.questions = new ArrayList<>();
        this.listAnswers = new ArrayList<>();
        this.positiveNumbers = new ArrayList<>();
    }
}