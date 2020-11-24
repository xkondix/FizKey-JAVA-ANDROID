package com.konradkowalczyk.fizkey_java_android.quizzes.mainly;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FallDown implements Question {

    private static double acceleration = 9.81;
    private static int countQuestion =  4;

    private Random random = new Random();
    private double heightStart,velocity,height,time,mass,potencialEnergy,kineticEnergy,totalEnergy;
    private List<String> units;
    private List<String> answers;
    private String question;
    private int positivNumber;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FallDown(Context context)
    {
        heightStart = random.nextInt(10000)+10;
        mass = random.nextInt(100)+5;
        this.context = context;
        randUnits();
        selectQuestion();
    }

    private void countLocationFromRandomTime()
    {

        time = random.nextInt();
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

        switch(random.nextInt(countQuestion))
        {
            case 0:
            {
                helpList.add(doubleToString(heightStart));
                helpList.add(units.get(0));
                helpList.add(doubleToString(acceleration));
                helpList.add(units.get(1));
                helpList.add(units.get(2));
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_one),helpList);
                createAnwsers(countFinalTimeFall());
                break;
            }
            case 1:
            {
                helpList.add(doubleToString(heightStart));
                helpList.add(units.get(0));
                helpList.add(doubleToString(acceleration));
                helpList.add(units.get(1));
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_two),helpList);
                createAnwsers(countFinalVelocityFall());
                break;
            }
            case 2:
            {
                countVelocityFromTime();
                helpList.add(doubleToString(heightStart));
                helpList.add(units.get(0));
                helpList.add(doubleToString(acceleration));
                helpList.add(units.get(1));
                helpList.add(doubleToString(time));
                helpList.add(units.get(2));
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_three),helpList);
                createAnwsers(velocity);
                break;
            }
            case 3:
            {
                countHeightFromTime();
                helpList.add(doubleToString(heightStart));
                helpList.add(units.get(0));
                helpList.add(doubleToString(acceleration));
                helpList.add(units.get(1));
                helpList.add(doubleToString(height));
                helpList.add(units.get(0));
                question = replaceZeroToValue(context.getResources().getString(R.string.falldown_four),helpList);
                createAnwsers(height);
                break;
            }
        }
    }

    private String replaceZeroToValue(String quest, List<String> forces)
    {
        StringBuilder newQuestion = new StringBuilder();
        int counter = 0;

        for(String word : quest.split(" "))
        {
            if(word.equals("0"))
            {
                newQuestion.append(forces.get(counter));
                counter++;
            }
            else
            {
                newQuestion.append(word);
                newQuestion.append(" ");
            }
        }

        return newQuestion.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createAnwsers(double anwser)
    {
        String array[] = new String[4];
        positivNumber = random.nextInt(4);
        array[0] = String.format("%.1f",
                ThreadLocalRandom.current().nextDouble(
                        anwser-random.nextInt(5), anwser+random.nextInt(5)));
        array[1] = String.format("%.1f",
                ThreadLocalRandom.current().nextDouble(
                        anwser-random.nextInt(5), anwser+random.nextInt(5)));

        array[2] = String.format("%.1f",
                ThreadLocalRandom.current().nextDouble(
                        anwser-random.nextInt(5), anwser+random.nextInt(5)));
        array[3] = String.format("%.1f",
                ThreadLocalRandom.current().nextDouble(
                        anwser-random.nextInt(5), anwser+random.nextInt(5)));

        array[positivNumber] = doubleToString(anwser);
        answers = Arrays.asList(array);

    }



    private String doubleToString(double force)
    {
        return String.format("%.1f",force);
    }

    private void randUnits()
    {
        if(random.nextInt(1) == 0)
        {
            units = new ArrayList<>(Arrays.asList(new String[]{"m","m/s","s","kg"}));
        }
        else
        {
            units = new ArrayList<>(Arrays.asList(new String[]{"km","km/h","h","kg"}));

        }
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public int getPositiveNumber() {
        return positivNumber;
    }

    @Override
    public List<String> getAnswers() {
        return answers;
    }
}
