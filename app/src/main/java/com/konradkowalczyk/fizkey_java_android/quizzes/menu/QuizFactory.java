package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.FallDown;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.Question;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.ThrowUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuizFactory {

    private Random random;
    private Context context;
    private int quanityBlocks;
    private List<Question> dataQuestionAnwser;
    private String[] resourceForces;
    private Map<Integer,Boolean> acceptedForceNumbers;


    public QuizFactory(Context context, int quanityBlocks)
    {
        this.context=context;
        this.quanityBlocks = quanityBlocks;
        random = new Random();
        dataQuestionAnwser = new ArrayList<>();
        acceptedForceNumbers = new HashMap<>();
        resourceForces = context.getResources().getStringArray(R.array.force_for_quizzes);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Question getQuestion()
    {
        int randomNumber;

        while(true)
        {
            randomNumber = random.nextInt(2);
            if(acceptedForceNumbers.keySet().contains(randomNumber))
            {
                break;
            }
        }
        System.out.println(randomNumber);

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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void generateQuestions(int maxNumber)
    {
        System.out.println(maxNumber);
        for(int i = 0; i < maxNumber; i++)
        {
            this.dataQuestionAnwser.add(getQuestion());
            System.out.println(dataQuestionAnwser.get(i).getQuestion());
        }
    }

    public void acceptForces(List<String> actives)
    {
        for(int i = 0; i < resourceForces.length; i++)
        {
            if(actives.contains(resourceForces[i]))
            {
                acceptedForceNumbers.put(i,true);
            }
            else
            {
                acceptedForceNumbers.put(i,false);

            }
        }
    }

    public List<Question> getDataQuestionAnwser() {
        return dataQuestionAnwser;
    }

}
