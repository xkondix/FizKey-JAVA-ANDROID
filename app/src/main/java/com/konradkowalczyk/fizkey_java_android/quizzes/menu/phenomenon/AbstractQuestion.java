package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class AbstractQuestion implements Question {

    public static final Random RANDOM = new Random();

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
//                   System.out.println(fakeNumber.equals(Integer.toString(convertAnwser)));
//                   System.out.println((containsValueOfArray(fakeNumber,array,i)));
//                   System.out.println(fakeNumber);
                   break;
               }
           }
            array[i] = createFakeAnswer(convertAnwser) + " " + unit;

        }

        array[positivNumber] = Integer.toString(convertAnwser)+ " " + unit;
        answers = Arrays.asList(array);

    }


    protected String createFakeAnswer(int anwser)
    {

        int min = anwser - RANDOM.nextInt( (anwser / 2) == 0 ? 1 : (anwser / 2));
        int max = anwser + RANDOM.nextInt((anwser * 2) == 0 ? min+10 : (anwser * 2) )+10;

        return Integer.toString(RANDOM.nextInt(max - min + 1) + min);
    }



    public static final String doubleToString(double force)
    {
        return String.format("%.2f",force);
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

    protected void changeValueAnswes(double unitValue, String unitName)
    {
        int quantityNumbersToChange = RANDOM.nextInt(answers.size());
        List<Integer> listOfIndexes = fortuneIndexes(quantityNumbersToChange);



        for(int i = 0; i < listOfIndexes.size(); i++)
        {
            double value = Integer.parseInt(answers.get(listOfIndexes.get(i)).split(" ")[0]) * unitValue;
            answers.set(
                    listOfIndexes.get(i)
                    ,(unitName.equals("m/s") ? doubleToString(value) : (int) value)+" "+ unitName);
        }


    }

    private List<Integer> fortuneIndexes(int quanity)
    {
        List<Integer> list = new ArrayList<>();
        int index;
        for(int i = 0; i < quanity; i++)
        {
            while(true){
                index = RANDOM.nextInt(quanity);
                if(!list.contains(index))
                {
                    break;
                }
            }
            list.add(index);
        }

        return list;
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
