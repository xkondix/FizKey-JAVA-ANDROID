package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.List;

public class QuizResults implements Parcelable {

    List<String> questions;
    List<String> goodAnswers;
    List<String> yourAnswers;
    List<Boolean> booleansAnswers;


    public QuizResults(List<String> questions, List<String> goodAnswers, List<String> yourAnswers, List<Boolean> booleansAnswers, int percent) {
        this.questions = questions;
        this.goodAnswers = goodAnswers;
        this.yourAnswers = yourAnswers;
        this.booleansAnswers = booleansAnswers;
    }

    public QuizResults()
    {
        this.questions = new ArrayList<>();
        this.goodAnswers = new ArrayList<>();
        this.yourAnswers = new ArrayList<>();
        this.booleansAnswers = new ArrayList<>();
    }


    protected QuizResults(Parcel in) {
        questions = in.createStringArrayList();
        goodAnswers = in.createStringArrayList();
        yourAnswers = in.createStringArrayList();
        booleansAnswers = new ArrayList<Boolean>();
        in.readList(booleansAnswers, null);
    }

    public static final Creator<QuizResults> CREATOR = new Creator<QuizResults>() {
        @Override
        public QuizResults createFromParcel(Parcel in) {
            return new QuizResults(in);
        }

        @Override
        public QuizResults[] newArray(int size) {
            return new QuizResults[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(questions);
        dest.writeStringList(goodAnswers);
        dest.writeStringList(yourAnswers);
        dest.writeList(booleansAnswers);
    }

    public List<QuizResult> getQuizResults(Context context)
    {
        List<QuizResult> quizResults = new ArrayList<>();
        for(int i = 0; i<questions.size(); i++)
        {

            quizResults.add( new QuizResult(
                    context.getResources().getString(R.string.card_view_question) + questions.get(i)
                    , context.getResources().getString(R.string.card_view_good_answer) + goodAnswers.get(i)
                    ,context.getResources().getString(R.string.card_view_your_answer) + yourAnswers.get(i)
                    ,context.getResources().getString(R.string.card_view_number_question) + (i+1)));
        }

        return quizResults;
    }

    public int getPercent()
    {
        double sum = 0;

        for(boolean bool : booleansAnswers)
        {
            if(bool == true)
            {
                sum++;
            }
        }

        return (int) ((sum / booleansAnswers.size()) * 100);
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getGoodAnswers() {
        return goodAnswers;
    }

    public List<String> getYourAnswers() {
        return yourAnswers;
    }

    public List<Boolean> getBooleansAnswers() {
        return booleansAnswers;
    }

    public void addQuestion(String question)
    {
        this.questions.add(question);
    }

    public void addGoodAnswer(String answer)
    {
        this.goodAnswers.add(answer);
    }

    public void addYourAnswer(String answer)
    {
        this.yourAnswers.add(answer);
    }

    public void addBooleanAnswer(boolean choice)
    {
        this.booleansAnswers.add(choice);
    }



}
