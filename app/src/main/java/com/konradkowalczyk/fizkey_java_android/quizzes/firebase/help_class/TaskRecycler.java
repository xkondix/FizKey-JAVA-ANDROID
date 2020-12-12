package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TaskRecycler implements Parcelable {

    private String question;
    private String goodAnswer;
    private String answers;


    public TaskRecycler(String question, String goodAnswer, List<String> answers) {
        this.question = question;
        this.goodAnswer = goodAnswer;
        this.answers = listToString(answers);
    }


    protected TaskRecycler(Parcel in) {
        question = in.readString();
        goodAnswer = in.readString();
        answers = in.readString();
    }

    public static final Creator<TaskRecycler> CREATOR = new Creator<TaskRecycler>() {
        @Override
        public TaskRecycler createFromParcel(Parcel in) {
            return new TaskRecycler(in);
        }

        @Override
        public TaskRecycler[] newArray(int size) {
            return new TaskRecycler[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(goodAnswer);
        dest.writeString(answers);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getGoodAnswer() {
        return goodAnswer;
    }

    public void setGoodAnswer(String goodAnswer) {
        this.goodAnswer = goodAnswer;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = listToString(answers);
    }


    private String listToString(List<String> answers)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String answer: answers)
        {
            stringBuilder.append(answer);
            stringBuilder.append("  ");
        }

        return stringBuilder.toString();
    }


}
