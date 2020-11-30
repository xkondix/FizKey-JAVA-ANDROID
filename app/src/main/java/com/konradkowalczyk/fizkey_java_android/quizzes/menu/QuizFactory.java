package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.content.Context;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.FallDown;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.Question;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.ThrowUp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizFactory {

    private Random random;
    private Context context;
    private int quanityBlocks;
    private List<Question> dataQuestionAnwser;
    private String[] resourceForces;
    private List<Integer> acceptedForceNumbers;


    public QuizFactory(Context context, int quanityBlocks)
    {
        this.context=context;
        this.quanityBlocks = quanityBlocks;
        random = new Random();
        dataQuestionAnwser = new ArrayList<>();
        acceptedForceNumbers = new ArrayList<>();
        resourceForces = context.getResources().getStringArray(R.array.force_for_quizzes);
    }

    public Question getQuestion()
    {
        int randomNumber;

        while(true)
        {
            randomNumber = random.nextInt(2);
            if(acceptedForceNumbers.contains(randomNumber))
            {
                break;
            }
        }

        switch(randomNumber)
        {
            case 0:
            {
                return new FallDown(context,quanityBlocks);
            }
            case 1:
            {
                return new ThrowUp(context,quanityBlocks);
            }
            default:
                return new FallDown(context,quanityBlocks);

        }

    }


    public void generateQuestions(int maxNumber)
    {
        for(int i = 0; i < maxNumber; i++)
        {
            this.dataQuestionAnwser.add(getQuestion());
        }
    }

    public void acceptForces(List<String> actives)
    {
        for(int i = 0; i < resourceForces.length; i++)
        {
            if(actives.contains(resourceForces[i]))
            {
                acceptedForceNumbers.add(i);
            }

        }
    }

    public List<Question> getDataQuestionAnwser() {
        return dataQuestionAnwser;
    }



}
