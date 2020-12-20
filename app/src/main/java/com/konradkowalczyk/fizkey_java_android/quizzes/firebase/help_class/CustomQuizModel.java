package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class;

import android.os.Parcel;
import android.os.Parcelable;

import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizModelInteface;

import java.util.ArrayList;
import java.util.List;

public class CustomQuizModel implements QuizModelInteface, Parcelable {


    private int currentlyNumber, maxNumber, numberOfFields, timerValue;
    private String topic, descripcion, data;

    private List<String> questions;
    private List<Integer> positiveNumbers;
    private List<List<String>> listAnswers;


    public CustomQuizModel() {

        this.questions = new ArrayList<>();
        this.positiveNumbers = new ArrayList<>();
        this.listAnswers = new ArrayList<>();

        this.maxNumber = 5;
        this.numberOfFields = 2;
        this.currentlyNumber = 0;
        this.timerValue = 0;

        this.topic = "";
        this.descripcion = "";
        this.data = "";
    }

    public CustomQuizModel(int currentlyNumber, int maxNumber, int numberOfFields, int timerValue, String topic, String descripcion, List<String> questions, List<Integer> positiveNumbers, List<List<String>> listAnswers, String data) {

        this.questions = questions;
        this.positiveNumbers = positiveNumbers;
        this.listAnswers = listAnswers;

        this.currentlyNumber = currentlyNumber;
        this.maxNumber = maxNumber;
        this.numberOfFields = numberOfFields;
        this.timerValue = timerValue;

        this.topic = topic;
        this.descripcion = descripcion;
        this.data = data;


    }

    protected CustomQuizModel(Parcel in) {
        currentlyNumber = in.readInt();
        maxNumber = in.readInt();
        numberOfFields = in.readInt();
        timerValue = in.readInt();
        topic = in.readString();
        descripcion = in.readString();
        questions = in.createStringArrayList();
        data = in.readString();
        positiveNumbers = new ArrayList<Integer>();
        in.readList(positiveNumbers, null);
        listAnswers = new ArrayList<List<String>>();
        in.readList(listAnswers, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentlyNumber);
        dest.writeInt(maxNumber);
        dest.writeInt(numberOfFields);
        dest.writeInt(timerValue);
        dest.writeString(topic);
        dest.writeString(descripcion);
        dest.writeStringList(questions);
        dest.writeString(data);
        dest.writeList(positiveNumbers);
        dest.writeList(listAnswers);
    }

    public static final Creator<CustomQuizModel> CREATOR = new Creator<CustomQuizModel>() {
        @Override
        public CustomQuizModel createFromParcel(Parcel in) {
            return new CustomQuizModel(in);
        }

        @Override
        public CustomQuizModel[] newArray(int size) {
            return new CustomQuizModel[size];
        }
    };

    @Override
    public List<String> getQuestions() {
        return this.questions;
    }

    @Override
    public List<Integer> getPositiveNumbers() {
        return this.positiveNumbers;
    }

    @Override
    public List<List<String>> getListAnswers() {
        return this.listAnswers;
    }

    @Override
    public int getCurrentlyNumber() {
        return this.currentlyNumber;
    }

    @Override
    public int getMaxNumber() {
        return this.maxNumber;
    }

    @Override
    public int getNumberOfFields() {
        return this.numberOfFields;
    }

    @Override
    public int getTimerValue() {
        return this.timerValue;
    }

    @Override
    public void setCurrentlyNumber(int currentlyNumber) {
        this.currentlyNumber = currentlyNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }


    public String getTopic() {
        return topic;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setPositiveNumbers(List<Integer> positiveNumbers) {
        this.positiveNumbers = positiveNumbers;
    }

    public void setListAnswers(List<List<String>> listAnswers) {
        this.listAnswers = listAnswers;
    }

    public String getData() {
        return data;
    }
}

