package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics;

import android.content.Context;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizFactory;

import java.util.ArrayList;
import java.util.List;

public class ThrowUp extends AbstractKinematics {

    private static final int NUMBER_QUESTIONS = 6;


    private double velocity,height,time;
    private int convertNumber,heightStart,velocityStart;
    private String question;

    private List<String> unitsName, helpList;

    private Context context;

    public ThrowUp(Context context, int countQuestion, QuizFactory.Level level)
    {
        super(countQuestion);
        this.context = context;

        setConstans();
        selectQuestion(level);
    }

    private void setConstans()
    {
        this.unitsName = getUnitBase();

        this.velocityStart =  RANDOM.nextInt(300)+2;
        this.heightStart = 0;
    }

    private double countFinalTimeMove()
    {
        return (2 * velocityStart) / ACCELERATION;
    }

    private double countFinalTimeRise()
    {
        return velocityStart / ACCELERATION;
    }

    private double countMaxHeight()
    {
        return (velocityStart*velocityStart)/(2 * ACCELERATION);
    }

    private double countFinalVelocityFall()
    {
        time = countFinalTimeMove();
        return velocity = velocityStart - ACCELERATION * time;
    }

    private void countVelocityFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeMove());
        velocity = velocityStart - ACCELERATION * time;
    }

    private void countHeightFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeMove());
        height = velocityStart * time - ((ACCELERATION /2)*(time*time));
    }


    private void selectQuestion(QuizFactory.Level level)
    {

        helpList = new ArrayList<>();
        createSkeatch();

        switch(RANDOM.nextInt(NUMBER_QUESTIONS))
        {
            case 0:
            {
                helpList.add(unitsName.get(2));
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_one),helpList);
                createAnwsers(countFinalTimeMove(),unitsName.get(convertNumber));
                break;
            }
            case 1:
            {
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_two),helpList);
                createAnwsers(countFinalVelocityFall(),unitsName.get(convertNumber));
                break;
            }
            case 2:
            {
                countVelocityFromTime();
                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_three),helpList);
                createAnwsers(velocity,unitsName.get(convertNumber));
                break;
            }
            case 3:
            {
                countHeightFromTime();
                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_four),helpList);
                createAnwsers(height,unitsName.get(convertNumber));
                break;
            }
            case 4:
            {
                helpList.add(unitsName.get(2));
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_five),helpList);
                createAnwsers(countFinalTimeRise(),unitsName.get(convertNumber));
                break;
            }
            case 5:
            {
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_six),helpList);
                createAnwsers(countMaxHeight(),unitsName.get(convertNumber));
                break;
            }
        }

        if(level.equals(QuizFactory.Level.HARD)) {
            changeValueAnswes(getConverts(convertNumber));
        }

    }



    private void createSkeatch()
    {
        helpList.add(Integer.toString(velocityStart));
        helpList.add(unitsName.get(1));
        helpList.add(Double.toString(ACCELERATION));
        helpList.add(unitsName.get(3));
        helpList.add(Integer.toString(heightStart));
        helpList.add(unitsName.get(0));
    }

    @Override
    public String getQuestion() {
        return question;
    }


}
