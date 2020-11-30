package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThrowUp extends AbstractQuestion{

    private static final double ACCELERATION = 9.81;
    private static final double ACCELERATION_KM = 35.31;

    private double velocity,height,time;
    private List<String> unitsAnother, unitsBase, helpList;
    private double[] converters = new double[]{1000,3.6,3600};
    private int convertNumber,heightStart,velocityStart;
    private String question;


    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ThrowUp(Context context, int countQuestion)
    {
        super(countQuestion);
        this.context = context;

        this.velocityStart =  RANDOM.nextInt(1000)+1;
        this.heightStart = 0;

        this.unitsAnother = new ArrayList<>(Arrays.asList(new String[]{"km","km/h","h","km/h^2","kg"}));
        this.unitsBase = new ArrayList<>(Arrays.asList(new String[]{"m","m/s","s","m/s^2","kg"}));

        selectQuestion();
    }


    private double countFinalTimeMove()
    {
        return (2 * velocityStart) / ACCELERATION_KM;
    }

    private double countFinalTimeRise()
    {
        return velocityStart / ACCELERATION_KM;
    }

    private double countMaxHeight()
    {
        return (velocityStart*velocityStart)/(2 * ACCELERATION_KM);
    }

    private double countFinalVelocityFall()
    {
        time = countFinalTimeMove();
        return velocity = velocityStart - ACCELERATION_KM * time;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void countVelocityFromTime()
    {
        int min = 1;
        int max = (int)countFinalTimeMove() - 1;
        time = RANDOM.nextInt(max - min + 1 > 0 ? max - min + 1 : 1) + min;
        velocity = velocityStart - ACCELERATION_KM * time;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void countHeightFromTime()
    {
        int min = 1;
        int max = (int)countFinalTimeMove() - 1;
        time = RANDOM.nextInt(max - min + 1 > 0 ? max - min + 1 : 1) + min;
        height = velocityStart * time - ((ACCELERATION_KM /2)*(time*time));
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
                helpList.add(unitsAnother.get(2));
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_one),helpList);
                createAnwsers(countFinalTimeMove(),unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
            case 1:
            {
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_two),helpList);
                createAnwsers(countFinalVelocityFall(),unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
            case 2:
            {
                countVelocityFromTime();
                helpList.add(Integer.toString((int) time));
                helpList.add(unitsAnother.get(2));
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_three),helpList);
                createAnwsers(velocity,unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
            case 3:
            {
                countHeightFromTime();
                helpList.add(Integer.toString((int) time));
                helpList.add(unitsAnother.get(2));
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_four),helpList);
                createAnwsers(height,unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
            case 4:
            {
                helpList.add(unitsAnother.get(2));
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_five),helpList);
                createAnwsers(countFinalTimeRise(),unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
            case 5:
            {
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.throwup_six),helpList);
                createAnwsers(countMaxHeight(),unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
        }
    }



    private void createSkeatch()
    {
        helpList.add(Integer.toString(velocityStart));
        helpList.add(unitsAnother.get(1));
        if(RANDOM.nextInt(2) == 0) {
            helpList.add(Double.toString(ACCELERATION));
            helpList.add(unitsBase.get(3));
        }
        else
        {
            helpList.add(Double.toString(ACCELERATION_KM));
            helpList.add(unitsAnother.get(3));
        }
        helpList.add(Integer.toString(heightStart));
        helpList.add(unitsAnother.get(0));
    }

    @Override
    public String getQuestion() {
        return question;
    }


}
