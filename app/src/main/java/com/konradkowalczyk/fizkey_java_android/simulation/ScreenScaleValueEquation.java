package com.konradkowalczyk.fizkey_java_android.simulation;

import com.konradkowalczyk.fizkey_java_android.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScreenScaleValueEquation {

    private List<Double> firstList,secoundList;
    private List<Long> pointsScaleX,pointsScaleY;
    private List<Float> valuesScaledFirstListY ,valuesScaledSecoundListX;

    private long scalaY,scalaX,buforX,buforY;
    private int width,height,changeX,changeY,lenX,lenY;
    private double spaceBetweenUnits;

    public static class Builder
    {
        //required parameters
        private final List<Double> firstList,secoundList;

        //optional parameters
        private double spaceBetweenUnits = 50.0;
        private int width = (int) Constants.SCREEN_WIDTH;;
        private int height = (int) Constants.SCREEN_HEIGHT;;


        public Builder(List<Double> firstList, List<Double> secoundList)
        {
            this.firstList=getAbs(firstList);
            this.secoundList=getAbs(secoundList);
        }

        public Builder width(int width)
        {
            this.width = width;
            return this;
        }

        public Builder height(int height)
        {
            this.height = height;
            return this;
        }

        public Builder spaceBetweenUnits(int spaceBetweenUnits)
        {
            this.spaceBetweenUnits = spaceBetweenUnits;
            return this;
        }


        public ScreenScaleValueEquation build()
        {
            return new ScreenScaleValueEquation(this);
        }

    }

    private ScreenScaleValueEquation(Builder builder)
    {

        this.firstList = builder.firstList;
        this.secoundList = builder.secoundList;
        this.spaceBetweenUnits = builder.spaceBetweenUnits;
        this.height = builder.height;
        this.width = builder.width;

        pointsScaleX = new ArrayList<>();
        pointsScaleY = new ArrayList<>();
        valuesScaledFirstListY = new ArrayList<>();
        valuesScaledSecoundListX = new ArrayList<>();

        changeX = 100 + (width%100 > 0 ? (width%100)/2 : 0);
        changeY = 100 + (height%100 > 0 ? (height%100)/2 : 0);
        lenX = (width-(changeX*2))/ ((int) spaceBetweenUnits)+1;
        lenY = (height-(changeY*2))/ ((int) spaceBetweenUnits)+1;
        buforX = 0l;
        buforY = 0l;
        scalaX=1l;
        scalaY=1L;

        scale();
    }



    private void scale() {
        createScaleX();
        createScaleY();

        createAxisX();
        createAxisY();

        createScaledValuesX();
        createScaledValuesY();
    }


    private void createScaleY()
    {
        while(true)
        {
            if(Collections.max(firstList)>=buforY && Collections.max(firstList) < scalaY*(lenY-1))
            {

                break;
            }
            buforY = scalaY * (lenY-1);
            scalaY = scalaY * 2;
        }
    }

    private void createScaleX()
    {
        while(true)
        {
            if(Collections.max(secoundList)>=buforX && Collections.max(secoundList) < scalaX*(lenX-1))
            {
                break;
            }
            buforX = scalaX * (lenX-1);
            scalaX = scalaX * 2;
        }
    }

    private void createAxisY()
    {
        long differenceY;

        buforY = scalaY * (lenY);
        differenceY = buforY / lenY;
        for(int i = 0; i<lenY;i++)
        {
            pointsScaleY.add(buforY);
            buforY = buforY - differenceY;
        }
        pointsScaleY.add(0l);
        pointsScaleY.remove(0);

    }

    private void createAxisX()
    {
        long differenceX;

        buforX = scalaX * (lenX);
        differenceX = buforX / lenX;
        for(int i = 0; i<lenX;i++)
        {
            pointsScaleX.add(buforX);
            buforX = buforX - differenceX;
        }
        pointsScaleX.add(0l);
        Collections.reverse(pointsScaleX);
    }

    private void createScaledValuesX()
    {
        for(int i = 0; i<secoundList.size();i++)
        {
            valuesScaledSecoundListX.add(fromRealValueToScaleX(secoundList.get(i)));
        }
    }

    private void createScaledValuesY()
    {
        for(int i = 0; i<firstList.size();i++)
        {
            valuesScaledFirstListY.add(fromRealValueToScaleY(firstList.get(i)));
        }
    }

    private float fromRealValueToScaleX(double x)
    {
        return (float) (((x *(spaceBetweenUnits /scalaX))+changeX));
    }

    private float fromRealValueToScaleY(double y)
    {
        return (float) (height-((y *(spaceBetweenUnits /scalaY))+changeY));
    }

    public float fromScaleXToRealValue(double x)
    {
        return (float) (((x - changeX) *scalaX)/ spaceBetweenUnits);
    }

    public float fromScaleYToRealValue(double y)
    {
        return (float) (((height - y -changeY) *scalaY)/ spaceBetweenUnits);
    }

    public List<Long> getPointsScaleX() {
        return pointsScaleX;
    }

    public List<Long> getPointsScaleY() {
        return pointsScaleY;
    }

    public List<Float> getValuesScaledFirstListY() {
        return valuesScaledFirstListY;
    }

    public List<Float> getValuesScaledSecoundListX() {
        return valuesScaledSecoundListX;
    }

    public int getChangeX() {
        return changeX;
    }

    public int getChangeY() {
        return changeY;
    }

    public int getLenX() {
        return lenX;
    }

    public int getLenY() {
        return lenY;
    }

    public double getSpaceBetweenUnits() {
        return spaceBetweenUnits;
    }

    public static List<Double> getAbs(List<Double> list)
    {
        ArrayList<Double> d = new ArrayList<>();
        for(int i = 0; i<list.size();i++)
        {
            d.add(Math.abs(list.get(i)));
        }

        return d;
    }

}
