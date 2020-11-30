package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ThrowUp extends AbstractQuestion{

    private static double acceleration = 9.81;
    private int countQuestion;

    private double velocity,height,time;
    private List<String> units, helpList;
    private double[] converters = new double[]{1000,3.6,3600};
    private int convertNumber,heightStart,velocityStart;

    private String question;


    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ThrowUp(Context context, int countQuestion)
    {
        super(countQuestion);
        this.context = context;
        velocityStart =  RANDOM.nextInt(100)+1;
        heightStart = 0;

        randUnits();
        selectQuestion();
    }


    private double countFinalTimeMove()
    {
        return (2 * velocityStart) / acceleration;
    }

    private double countFinalTimeRise()
    {
        return velocityStart / acceleration;
    }

    private double countMaxHeight()
    {
        return (velocityStart*velocityStart)/(2 * acceleration);
    }

    private double countFinalVelocityFall()
    {
        time = countFinalTimeMove();
        return velocity = velocityStart - acceleration * time;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void countVelocityFromTime()
    {
        time = ThreadLocalRandom.current().nextInt(1, (int)countFinalTimeMove() - 1);
        velocity = velocityStart - acceleration * time;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void countHeightFromTime()
    {
        time = ThreadLocalRandom.current().nextInt(1, (int)countFinalTimeMove() - 1);
        height = velocityStart * time - ((acceleration/2)*(time*time));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void selectQuestion()
    {

        helpList = new ArrayList<>();
        createSkeatch();

        switch(RANDOM.nextInt(6))
        {
            case 0:
            {
                helpList.add(units.get(2));
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_one),helpList);
                createAnwsers(countFinalTimeMove(),"s");
                break;
            }
            case 1:
            {
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_two),helpList);
                createAnwsers(countFinalVelocityFall(),"m/s");
                break;
            }
            case 2:
            {
                countVelocityFromTime();
                helpList.add(doubleToString(time));
                helpList.add(units.get(2));
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_three),helpList);
                createAnwsers(velocity,"m/s");
                break;
            }
            case 3:
            {
                countHeightFromTime();
                helpList.add(doubleToString(time));
                helpList.add(units.get(2));
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_four),helpList);
                createAnwsers(height,"m");
                break;
            }
            case 4:
            {
                helpList.add(units.get(2));
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_five),helpList);
                createAnwsers(countFinalTimeRise(),"s");
                break;
            }
            case 5:
            {
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_six),helpList);
                createAnwsers(countMaxHeight(),"m");
                break;
            }
        }
    }



    private int convertValueToUnit(double anwser)
    {
        if(units.get(0).equals("km"))
        {
            return (int) (anwser / converters[convertNumber]);
        }

        return (int) anwser;
    }


    private void randUnits()
    {
        if(RANDOM.nextInt(1) == 0)
        {
            units = new ArrayList<>(Arrays.asList(new String[]{"m","m/s","s","m/s^2","kg"}));
        }
        else
        {
            units = new ArrayList<>(Arrays.asList(new String[]{"km","km/h","h","km/h^2","kg"}));

        }
    }

    private void createSkeatch()
    {
        helpList.add(doubleToString(velocityStart));
        helpList.add(units.get(1));
        helpList.add(Double.toString(acceleration));
        helpList.add(units.get(3));
        helpList.add(Integer.toString(heightStart));
        helpList.add(units.get(0));
    }

    @Override
    public String getQuestion() {
        return question;
    }


}
