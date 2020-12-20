package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Task implements Serializable {

    private String uuid;
    private String topic;
    private String description;
    private String data;

    private List<String> questions;
    private Map<String, List<String>> answers;
    private List<Integer> positiveNumbers;

    private int numberOfFields;
    private int timerValue;


    public Task(String topic, String description, List<String> questions, Map<String, List<String>> answers, List<Integer> positiveNumbers, String data, int numberOfFields, int timerValue, String uuid) {
        this.topic = topic;
        this.description = description;
        this.questions = questions;
        this.answers = answers;
        this.positiveNumbers = positiveNumbers;
        this.data = data;
        this.numberOfFields = numberOfFields;
        this.timerValue = timerValue;
        this.uuid = uuid;

    }

    private Task(){}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public Map<String, List<String>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, List<String>> answers) {
        this.answers = answers;
    }

    public List<Integer> getPositiveNumbers() {
        return positiveNumbers;
    }

    public void setPositiveNumbers(List<Integer> positiveNumbers) {
        this.positiveNumbers = positiveNumbers;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    @Override
    public String toString() {
        return "Task{" +
                "uuid='" + uuid + '\'' +
                ", topic='" + topic + '\'' +
                ", description='" + description + '\'' +
                ", data='" + data + '\'' +
                ", questions=" + questions +
                ", answers=" + answers +
                ", positiveNumbers=" + positiveNumbers +
                ", numberOfFields=" + numberOfFields +
                ", timerValue=" + timerValue +
                '}';
    }
}
