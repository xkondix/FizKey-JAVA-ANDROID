package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics;

import android.content.Context;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizFactory;

import java.util.ArrayList;
import java.util.List;

public class FallDown extends AbstractKinematics {

    private static final int NUMBER_QUESTIONS = 4;

    private int convertNumber, heightStart;
    private double velocity,height,time;
    private String question;
    private List<String> helpList, unitsName;
    private Context context;

    public FallDown(Context context, int countQuestion, QuizFactory.Level level)
    {
        super(countQuestion);
        this.context = context;

        setConstans();
        selectQuestion(level);
    }

    private void setConstans()
    {
        this.unitsName = getUnitBase();
        this.heightStart = RANDOM.nextInt(1500) + 10;
    }

    private double countFinalTimeFall()
    {
        return Math.sqrt((2*heightStart)/ ACCELERATION);
    }

    private double countFinalVelocityFall()
    {
        return  Math.sqrt(2 * heightStart * ACCELERATION);
    }

    private void countVelocityFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeFall());
        velocity = ACCELERATION * time  ;
    }

    private void countHeightFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeFall());
        height = heightStart - ((ACCELERATION /2)*(time*time));
    }


    private void selectQuestion(QuizFactory.Level level)
    {
        helpList = new ArrayList<>();
        createSkeatch();

        switch(RANDOM.nextInt(NUMBER_QUESTIONS))
        {
            case 0:
            {
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_one),helpList);
                createAnwsers(countFinalTimeFall(),unitsName.get(convertNumber));
                break;
            }
            case 1:
            {
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_two),helpList);
                createAnwsers(countFinalVelocityFall(),unitsName.get(convertNumber));
                break;
            }
            case 2:
            {
                countVelocityFromTime();
                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_three),helpList);
                createAnwsers(velocity,unitsName.get(convertNumber));
                break;
            }
            case 3:
            {
                countHeightFromTime();
                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_four),helpList);
                createAnwsers(height,unitsName.get(convertNumber));
                break;
            }
        }

        if(level.equals(QuizFactory.Level.HARD)) {
            changeValueAnswes(getConverts(convertNumber));
        }

    }



    private void createSkeatch()
    {
         helpList.add(Integer.toString(heightStart));
         helpList.add(unitsName.get(0));
         helpList.add(Double.toString(ACCELERATION));
         helpList.add(unitsName.get(3));
         helpList.add("0");
    }

    @Override
    public String getQuestion() {
        return question;
    }

}
