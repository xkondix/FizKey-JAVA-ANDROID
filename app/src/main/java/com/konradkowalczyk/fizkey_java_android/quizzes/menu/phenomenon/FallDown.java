package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FallDown extends AbstractQuestion {

    private static double acceleration = 9.81;
    private int countQuestion;

    private double heightStart,velocity,height,time;
    private List<String> units;
    private double[] converters = new double[]{1000,3.6,3600};
    private int convertNumber;
    private String question;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FallDown(Context context, int countQuestion)
    {
        super(countQuestion);
        this.context = context;
        heightStart = RANDOM.nextInt(10000)+10;
        randUnits();
        selectQuestion();
    }

    private void countLocationFromRandomTime()
    {

        time = RANDOM.nextInt();
    }

    private double countFinalTimeFall()
    {
        return Math.sqrt((2*heightStart)/acceleration);
    }

    private double countFinalVelocityFall()
    {
        return  Math.sqrt(2 * heightStart * acceleration);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void countVelocityFromTime()
    {
        time = ThreadLocalRandom.current().nextInt(1, (int)countFinalTimeFall() - 1);
        velocity = acceleration * time  ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void countHeightFromTime()
    {
        time = ThreadLocalRandom.current().nextInt(1, (int)countFinalTimeFall() - 1);
        height = heightStart - ((acceleration/2)*(time*time));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void selectQuestion()
    {
        List<String> helpList = new ArrayList<>();

        switch(RANDOM.nextInt(4))
        {
            case 0:
            {
                helpList.add(doubleToString(heightStart));
                helpList.add(units.get(0));
                helpList.add(doubleToString(acceleration));
                helpList.add(units.get(3));
                helpList.add("0");
                helpList.add(units.get(2));
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_one),helpList);
                createAnwsers(countFinalTimeFall(),"s");
                break;
            }
            case 1:
            {
                helpList.add(doubleToString(heightStart));
                helpList.add(units.get(0));
                helpList.add(doubleToString(acceleration));
                helpList.add(units.get(3));
                helpList.add("0");
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_two),helpList);
                createAnwsers(countFinalVelocityFall(), "m/s");
                break;
            }
            case 2:
            {
                countVelocityFromTime();
                helpList.add(doubleToString(heightStart));
                helpList.add(units.get(0));
                helpList.add(doubleToString(acceleration));
                helpList.add(units.get(3));
                helpList.add("0");
                helpList.add(doubleToString(time));
                helpList.add(units.get(2));
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_three),helpList);
                createAnwsers(velocity, "m/s");
                break;
            }
            case 3:
            {
                countHeightFromTime();
                helpList.add(doubleToString(heightStart));
                helpList.add(units.get(0));
                helpList.add(doubleToString(acceleration));
                helpList.add(units.get(3));
                helpList.add("0");
                helpList.add(doubleToString(time));
                helpList.add(units.get(2));
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_four),helpList);
                createAnwsers(height, "m");
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
        if(RANDOM.nextInt(2) == 0)
        {
            units = new ArrayList<>(Arrays.asList(new String[]{"m","m/s","s","m/s^2","kg"}));
        }
        else
        {
            units = new ArrayList<>(Arrays.asList(new String[]{"km","km/h","h","km/h^2","kg"}));

        }
    }

    @Override
    public String getQuestion() {
        return question;
    }

}
