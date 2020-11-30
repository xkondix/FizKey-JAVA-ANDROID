package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon;

import android.content.Context;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FallDown extends AbstractQuestion {

    private static final double ACCELERATION = 9.81;
    private static final double ACCELERATION_KM = 35.31;


    private double velocity,height,time;
    private List<String> helpList, unitsBase, unitsAnother;
    private double[] converters = new double[]{1000,0.27777,3600};
    private int convertNumber, heightStart;
    private String question;
    private Context context;

    public FallDown(Context context, int countQuestion)
    {
        super(countQuestion);
        this.context = context;
        this.heightStart = RANDOM.nextInt(200)+1;
        this.unitsAnother = new ArrayList<>(Arrays.asList(new String[]{"km","km/h","h","km/h^2","kg"}));
        this.unitsBase = new ArrayList<>(Arrays.asList(new String[]{"m","m/s","s","m/s^2","kg"}));

        selectQuestion();
    }

    private void countLocationFromRandomTime()
    {

        time = RANDOM.nextInt();
    }

    private double countFinalTimeFall()
    {
        return Math.sqrt((2*heightStart)/ ACCELERATION_KM);
    }

    private double countFinalVelocityFall()
    {
        return  Math.sqrt(2 * heightStart * ACCELERATION_KM);
    }

    private void countVelocityFromTime()
    {
        int min = 1;
        int max = (int)countFinalTimeFall() - 1;
        time = RANDOM.nextInt(max - min + 1 > 0 ? max - min + 1 : 1) + min;
        velocity = ACCELERATION_KM * time  ;
    }

    private void countHeightFromTime()
    {
        int min = 1;
        int max = (int)countFinalTimeFall() - 1;
        time = RANDOM.nextInt(max - min + 1 > 0 ? max - min + 1 : 1) + min;
        height = heightStart - ((ACCELERATION_KM /2)*(time*time));
    }


    private void selectQuestion()
    {
        helpList = new ArrayList<>();

        switch(RANDOM.nextInt(4))
        {
            case 0:
            {
                createSkeatch();
                convertNumber=2;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_one),helpList);
                createAnwsers(countFinalTimeFall(),unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
            case 1:
            {
                createSkeatch();
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_two),helpList);
                createAnwsers(countFinalVelocityFall(),unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
            case 2:
            {
                countVelocityFromTime();
                createSkeatch();

                helpList.add(Integer.toString((int) time));
                helpList.add(unitsAnother.get(2));
                convertNumber=1;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_three),helpList);
                createAnwsers(velocity,unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
            case 3:
            {
                countHeightFromTime();
                createSkeatch();
                helpList.add(Integer.toString((int) time));
                helpList.add(unitsAnother.get(2));
                convertNumber=0;
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_four),helpList);
                createAnwsers(height,unitsAnother.get(convertNumber));
                changeValueAnswes(converters[convertNumber],unitsBase.get(convertNumber));
                break;
            }
        }
    }








    private void createSkeatch()
    {
        helpList.add(Integer.toString(heightStart));
        helpList.add(unitsAnother.get(0));
        if(RANDOM.nextInt(2) == 0) {
            helpList.add(Double.toString(ACCELERATION));
            helpList.add(unitsBase.get(3));
        }
        else
        {
            helpList.add(Double.toString(ACCELERATION_KM));
            helpList.add(unitsAnother.get(3));
        }
        helpList.add("0");
    }

    @Override
    public String getQuestion() {
        return question;
    }

}
