package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class AbstractQuestion implements Question {

    public static final Random RANDOM = new Random();

    //private double[] converters = new double[]{1000,3.6,3600};
    private List<String> answers;
    private int positivNumber;
    private int countQuestion;


    public AbstractQuestion(int countQuestion)
    {
        this.countQuestion = countQuestion;
    }




    protected String replaceZeroToValue(String quest, List<String> forces)
    {
        StringBuilder newQuestion = new StringBuilder();
        int counter = 0;

        for(String word : quest.split(""))
        {
            if(word.equals("0"))
            {
                newQuestion.append(forces.get(counter));
                counter++;
            }
            else
            {
                newQuestion.append(word);
            }
        }

        return newQuestion.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void createAnwsers(double anwser, String unit)
    {
        int convertAnwser = (int) Math.abs(anwser);

        String array[] = new String[countQuestion];
        positivNumber = RANDOM.nextInt(countQuestion);
        String fakeNumber;

        for(int i = 0; i< array.length; i++)
        {
           while(true)
           {
               fakeNumber = createFakeAnswer(convertAnwser);
               if(!(fakeNumber.equals(Integer.toString(convertAnwser)))  && !(containsValueOfArray(fakeNumber,array,i)))
               {
                   break;
               }
           }
            array[i] = createFakeAnswer(convertAnwser) + " " + unit;

        }

        array[positivNumber] = Integer.toString(convertAnwser)+ " " + unit;
        answers = Arrays.asList(array);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected String createFakeAnswer(int anwser)
    {
        int min = anwser - RANDOM.nextInt( (anwser / 2) == 0 ? 1 : (anwser / 2));
        int max = anwser + RANDOM.nextInt(anwser + min )+3;

        return Integer.toString(RANDOM.nextInt(max - min + 1) + min);
    }



    public static final String doubleToString(double force)
    {
        return String.format("%.1f",force);
    }


    private boolean containsValueOfArray(String value, String[] array, int range)
    {
        for(int i = 0; i < range; i++)
        {
            if(array[i].equals(value))
            {
                return true;
            }
        }

        return false;
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
