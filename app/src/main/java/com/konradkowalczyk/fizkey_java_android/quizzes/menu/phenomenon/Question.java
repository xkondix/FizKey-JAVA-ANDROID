package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon;

import java.util.List;

public interface Question  {

    public String getQuestion();
    public int getPositiveNumber();
    public List<String> getAnswers();

}
