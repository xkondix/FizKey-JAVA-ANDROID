package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;

import android.graphics.RectF;

import com.konradkowalczyk.fizkey_java_android.simulation.ScreenScaleValueEquation;

public class MotionBall extends RectF {

    private MotionThread motionThread;
    private ScreenScaleValueEquation scala;
    private Boolean[] changingParemeters;

    public MotionBall(float left, float right, ScreenScaleValueEquation scala, Boolean[] changingParemeters) {

        // size and dimision of ball
        this.left = left;
        this.top = scala.getValuesScaledFirstListY().get(0)-40 ;
        this.right = right;
        this.bottom =  scala.getValuesScaledFirstListY().get(0);
        this.scala = scala;

        this.changingParemeters = changingParemeters;


        //left  The X coordinate of the left side of the rectangle
        //top The Y coordinate of the top of the rectangle
        //right The X coordinate of the right side of the rectangle
        //bottom The Y coordinate of the bottom of the rectangle

    }

    public void createThread()
    {
        if(motionThread == null) {
            motionThread = new MotionThread(this);
        }
        else {
            motionThread.onFinish();
            motionThread = new MotionThread(this);
        }

        startThread();
    }

    public void startThread()
    {
        motionThread.start();
    }

    public void onPause() {
        motionThread.onPause();
    }

    public void onResume() {
        motionThread.onResume();
    }

    public void onFinish() {
        motionThread.onFinish();
    }


    public double getTime()
    {
        return motionThread.getTimeDouble();
    }

    public ScreenScaleValueEquation getScala() {
        return scala;
    }

    public void setY(float y)
    {
        if(changingParemeters[0] == true) {
            this.bottom = y;
            this.top = y - 40;
        }
    }

    public void setX(float x)
    {
        if(changingParemeters[1] == true) {
            this.left = x;
            this.right = x + 40;
        }
    }

    public int getCounter()
    {
        return motionThread.getCounter();
    }

    public boolean getStatus(){
        return motionThread.getStatus();
    }


    public float getBottom()
    {
        return scala.fromScaleYToRealValue(bottom);
    }


}
