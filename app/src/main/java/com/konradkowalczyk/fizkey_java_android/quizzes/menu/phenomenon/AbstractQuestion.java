package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        positivNumber = RANDOM.nextInt(countQuestion);
        List<Integer> array = getFakerList(anwser,countQuestion);

        answers = addUnitToValue(array, unit);
        answers.set(positivNumber,(doubleToString(anwser)+ " " + unit));


    }

    protected void changeValueAnswes(Map<String,Double> unitNameAndValue)
    {
        int randomIndex, quantityNumbersToChange;
        double valueOfIndex, newValue;
        String nameOfIndex;

        quantityNumbersToChange = RANDOM.nextInt(answers.size());
        List<Integer> listOfIndexes = fortuneIndexes(quantityNumbersToChange);

        for(int i = 0; i < listOfIndexes.size(); i++)
        {
            randomIndex = RANDOM.nextInt(unitNameAndValue.size());
            nameOfIndex = (String) unitNameAndValue.keySet().toArray()[randomIndex];
            valueOfIndex = unitNameAndValue.get(nameOfIndex);

            newValue = Double.parseDouble(answers.get(listOfIndexes.get(i)).split(" ")[0]) * valueOfIndex;

            answers.set(listOfIndexes.get(i) , newValue +" "+ nameOfIndex );
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

    private  List<Integer> getFakerList(double anwser, int countQuestion)
    {

        List<Integer> fakeValues = new ArrayList<>();

        int convertAnwser = (int) Math.abs(anwser);
        int fakeNumber;

        for(int i = 0; i < countQuestion; i++)
        {
            while(true)
            {
                fakeNumber = createFakeAnswer(convertAnwser);
                if( ((fakeNumber !=convertAnwser) && !(fakeValues.contains(fakeNumber))))
                {
                    break;
                }
            }
            fakeValues.add(fakeNumber);

        }

        return fakeValues;
    }

    private  List<String> addUnitToValue(List<Integer> valuesInt, String unit)
    {
        List<String> valuesString = new ArrayList<>();

        for(int value : valuesInt)
        {
            valuesString.add(addRandomDouble(value) + " " + unit);
        }

        return valuesString;
    }

    private String addRandomDouble(int value)
    {
        return doubleToString((RANDOM.nextDouble() + value));
    }

    public static int createFakeAnswer(int anwser)
    {
        int min = anwser - RANDOM.nextInt((anwser / 2) == 0 ? 1 : (anwser / 2));
        int max = anwser + RANDOM.nextInt((anwser * 2) == 0 ? min+10 : (anwser * 2) )+10;

        return randomBeetwen(min,max);
    }

    protected static int randomBeetwen(int min, int max) {
        return RANDOM.nextInt( max - min + 1 > 0 ? max - min + 1 : 1) + min;
    }

    protected static final String doubleToString(double force) {
        return String.format("%.2f",force);
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
