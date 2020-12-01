package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

public class QuizResult {

    private String question;
    private String goodAnswer;
    private String yourAnswer;
    private String numberQuestion;

    public QuizResult(String question, String goodAnswer, String yourAnswer, String numberQuestion) {
        this.question = question;
        this.goodAnswer = goodAnswer;
        this.yourAnswer = yourAnswer;
        this.numberQuestion = numberQuestion;
    }

    public QuizResult() {
        this.question = "";
        this.goodAnswer = "";
        this.yourAnswer = "";
        this.numberQuestion = "";
    }

    public String getQuestion() {
        return question;
    }

    public String getGoodAnswer() {
        return goodAnswer;
    }

    public String getYourAnswer() {
        return yourAnswer;
    }

    public String getNumberQuestion() {
        return numberQuestion;
    }
}
