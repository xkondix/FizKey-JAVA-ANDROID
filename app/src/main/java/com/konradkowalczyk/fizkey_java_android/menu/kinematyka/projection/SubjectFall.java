package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;

import android.graphics.RectF;

import com.konradkowalczyk.fizkey_java_android.simulation.ScreenScaleValueEquation;

public class SubjectFall extends RectF {

    private ThreadFall threadFall;
    private ScreenScaleValueEquation scala;
    private Boolean[] changingParemeters;

    public SubjectFall(float left, float right, ScreenScaleValueEquation scala, Boolean[] changingParemeters) {

        // size and dimision of ball
        this.left = left;
        this.top = scala.getValuesScaledFirstListY().get(0)-75 ;
        this.right = right;
        this.bottom =  scala.getValuesScaledFirstListY().get(0);
        this.scala = scala;

        this.changingParemeters = changingParemeters;


        //left  The X coordinate of the left side of the rectangle
        //top The Y coordinate of the top of the rectangle
        //right The X coordinate of the right side of the rectangle
        //bottom The Y coordinate of the bottom of the rectangle



        threadFall = new ThreadFall(this);
        threadFall.start();


    }

    public void createThread()
    {
        threadFall = new ThreadFall(this);
    }

    public void setRunning(boolean run)
    {
        threadFall.setRunning(false);
    }

    public void startThread()
    {
        threadFall.start();
    }

    public double getTime()
    {
        return threadFall.getTimeDouble();
    }

    public ScreenScaleValueEquation getScala() {
        return scala;
    }

    public void setY(float y)
    {
        if(changingParemeters[0] == true) {
            this.bottom = y;
            this.top = y - 75;
        }
    }

    public void setX(float x)
    {
        if(changingParemeters[1] == true) {
            this.left = x;
            this.right = x + 75;
        }
    }

    public float getBottom()
    {
        return scala.fromScaleYToRealValue(bottom);
    }







}
