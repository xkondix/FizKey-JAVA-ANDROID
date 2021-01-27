package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics;

import android.content.Context;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizFactory;

import java.util.ArrayList;
import java.util.List;

public class HorizontalProjection extends AbstractKinematics {


    private static final int NUMBER_QUESTIONS = 5;


    private double time, yVelocityStart;
    private int convertNumber, heightStart, velocityStart, angleStart;
    private String question;

    private List<String> unitsName, helpList;

    private Context context;

    public HorizontalProjection(Context context, int countQuestion, QuizFactory.Level level) {
        super(countQuestion);
        this.context = context;

        setConstans();
        selectQuestion(level);
    }

    private void setConstans()
    {
        this.unitsName = getUnitBase();

        this.velocityStart =  RANDOM.nextInt(100)+1;
        this.heightStart =  RANDOM.nextInt(101) + 10;
        this.angleStart =  0;
    }

    private double countFinalTimeMove()
    {
        return Math.sqrt(2 * heightStart / ACCELERATION);
    }

    private double countFinalRange()
    {
        return velocityStart * Math.sqrt(2 * heightStart / ACCELERATION);
    }

    private double countVelocityYFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeMove());
        return  ACCELERATION * time;
    }

    private double countHeightFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeMove());
        return  ((ACCELERATION /2)*(time*time));
    }

    private double countAngleFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeMove());
        return (Math.atan2(ACCELERATION * time, velocityStart) / Math.PI) * 180;
    }

    private void selectQuestion(QuizFactory.Level level)
    {

        helpList = new ArrayList<>();
        createSkeatch();

        switch(RANDOM.nextInt(NUMBER_QUESTIONS))
        {
            case 0:
            {
                convertNumber = 2;
                question = replaceZeroToValue(context.getResources().getString(R.string.horizontal_projection_one),helpList);
                createAnwsers(countFinalTimeMove(),unitsName.get(convertNumber));
                break;
            }
            case 1:
            {

                convertNumber = 0;
                question = replaceZeroToValue(context.getResources().getString(R.string.horizontal_projection_two),helpList);
                createAnwsers(countFinalRange(),unitsName.get(convertNumber));
                break;
            }
            case 2:
            {
                convertNumber = 1;
                double velocityFromTime = countVelocityYFromTime();

                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));

                question = replaceZeroToValue(context.getResources().getString(R.string.horizontal_projection_three),helpList);
                createAnwsers(velocityFromTime,unitsName.get(convertNumber));
                break;
            }
            case 3:
            {

                convertNumber = 0;
                double heightFromTime = countHeightFromTime();

                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));

                question = replaceZeroToValue(context.getResources().getString(R.string.horizontal_projection_four),helpList);
                createAnwsers(heightFromTime,unitsName.get(convertNumber));
                break;
            }

            case 4:
            {
                convertNumber = 3;
                double angleFromTime = countAngleFromTime();

                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));

                question = replaceZeroToValue(context.getResources().getString(R.string.horizontal_projection_five),helpList);
                createAnwsers(angleFromTime,unitsName.get(4));
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


