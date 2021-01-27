package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.content.Context;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.Question;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics.FallDown;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics.HorizontalProjection;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics.ObliqueProjection;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics.ThrowUp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizFactory {

    public static enum Level {NORMAL, HARD};

    private static final int QUANITY_FORTUNE = 4;

    private Random random;
    private Context context;
    private int quanityBlocks;

    private List<Question> dataQuestionAnwser;
    private List<Integer> acceptedForceNumbers;
    private String[] resourceForces;
    private Level level;



    public QuizFactory(Context context, int quanityBlocks)
    {
        this.context=context;
        this.quanityBlocks = quanityBlocks;
        random = new Random();
        dataQuestionAnwser = new ArrayList<>();
        acceptedForceNumbers = new ArrayList<>();
        resourceForces = context.getResources().getStringArray(R.array.force_for_quizzes);
    }

    private Question getQuestion()
    {
        int randomNumber;

        while(true)
        {
            randomNumber = random.nextInt(QUANITY_FORTUNE);
            if(acceptedForceNumbers.contains(randomNumber))
            {
                break;
            }
        }

        switch(randomNumber)
        {
            case 0:
            {
                return new FallDown(context,quanityBlocks,level);
            }
            case 1:
            {
                return new ThrowUp(context,quanityBlocks,level);
            }
            case 2:
            {
                return new ObliqueProjection(context,quanityBlocks,level);
            }
            case 3:
            {
                return new HorizontalProjection(context,quanityBlocks,level);
            }
            default:
                return new FallDown(context,quanityBlocks,level);

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

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<Question> getDataQuestionAnwser() {
        return dataQuestionAnwser;
    }


}
