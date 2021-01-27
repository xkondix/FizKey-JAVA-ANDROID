package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics;

import android.content.Context;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizFactory;

import java.util.ArrayList;
import java.util.List;

public class ObliqueProjection extends AbstractKinematics{

    private static final int NUMBER_QUESTIONS = 7;


    private double time, yVelocityStart;
    private int convertNumber, heightStart, velocityStart, angleStart;
    private String question;

    private List<String> unitsName, helpList;

    private Context context;

    public ObliqueProjection(Context context, int countQuestion, QuizFactory.Level level) {
        super(countQuestion);
        this.context = context;

        setConstans();
        selectQuestion(level);
    }

    private void setConstans()
    {
        this.unitsName = getUnitBase();

        this.velocityStart =  RANDOM.nextInt(100)+1;
        this.heightStart = RANDOM.nextInt(1) == 0 ? 0 : RANDOM.nextInt(101);
        this.angleStart =  RANDOM.nextInt(89) + 1;
        this.yVelocityStart = velocityStart *  Math.sin((Math.PI* angleStart)/180);
    }

    private double countFinalTimeMove()
    {
        return (2 * yVelocityStart) / ACCELERATION;
    }

    private double countFinalTimeRise()
    {
        return yVelocityStart / ACCELERATION;
    }

    private double countMaxHeight()
    {
        return (yVelocityStart*yVelocityStart)/(2 * ACCELERATION);
    }

    private double countFinalRange()
    {
        return (velocityStart * velocityStart) * Math.sin(2 * (Math.PI* angleStart)/180) / ACCELERATION;
    }

    private double countVelocityYFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeMove());
        return  yVelocityStart - ACCELERATION * time;
    }

    private double countHeightFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeMove());
        return yVelocityStart * time - ((ACCELERATION /2)*(time*time));
    }

    private double countAngleFromTime()
    {
        time = randomBeetwen(1,(int) countFinalTimeMove());
        double vx = velocityStart *  Math.cos((Math.PI* angleStart)/180);
        double vy = yVelocityStart - ACCELERATION * time;
        return (Math.atan2(vy,vx) / Math.PI) * 180;
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
                question = replaceZeroToValue(context.getResources().getString(R.string.oblique_projection_one),helpList);
                createAnwsers(countFinalTimeMove(),unitsName.get(convertNumber));
                break;
            }
            case 1:
            {
                convertNumber = 0;
                question = replaceZeroToValue(context.getResources().getString(R.string.oblique_projection_two),helpList);
                createAnwsers(countMaxHeight(),unitsName.get(convertNumber));
                break;
            }
            case 2:
            {

                convertNumber = 0;
                question = replaceZeroToValue(context.getResources().getString(R.string.oblique_projection_three),helpList);
                createAnwsers(countFinalRange(),unitsName.get(convertNumber));
                break;
            }
            case 3:
            {

                convertNumber = 2;
                question = replaceZeroToValue(context.getResources().getString(R.string.oblique_projection_four),helpList);
                createAnwsers(countFinalTimeRise(),unitsName.get(convertNumber));
                break;
            }
            case 4:
            {
                convertNumber = 1;
                double velocityFromTime = countVelocityYFromTime();

                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));

                question = replaceZeroToValue(context.getResources().getString(R.string.oblique_projection_five),helpList);
                createAnwsers(velocityFromTime,unitsName.get(convertNumber));
                break;
            }
            case 5:
            {

                convertNumber = 0;
                double heightFromTime = countHeightFromTime();

                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));

                question = replaceZeroToValue(context.getResources().getString(R.string.oblique_projection_six),helpList);
                createAnwsers(heightFromTime,unitsName.get(convertNumber));
                break;
            }

            case 6:
            {
                convertNumber = 3;
                double angleFromTime = countAngleFromTime();

                helpList.add(Integer.toString((int) time));
                helpList.add(unitsName.get(2));

                question = replaceZeroToValue(context.getResources().getString(R.string.oblique_projection_seven),helpList);
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
        helpList.add(Integer.toString(angleStart));
        helpList.add(Integer.toString(heightStart));
        helpList.add(unitsName.get(0));
    }

    @Override
    public String getQuestion() {
        return question;
    }


}

