package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;

import android.graphics.RectF;

import  com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.physics.Vector2D;
import static java.lang.Math.abs;

public class SubjectFall extends RectF {

    private Vector2D a;
    private Vector2D v;
    private Vector2D pos;
    private double scala;
    private float difference;
    private double startPosX;
    private double startPosY;
    private ThreadFall threadFall;

    public SubjectFall(float left, float top, float right, float bottom, float scala) {
        // size and dimision of ball
        this.left = left;
        this.top = Constants.SCREEN_HEIGHT-top ;
        this.right = right;
        this.bottom =  Constants.SCREEN_HEIGHT-bottom;
        this.scala = scala;


        //left  The X coordinate of the left side of the rectangle
        //top The Y coordinate of the top of the rectangle
        //right The X coordinate of the right side of the rectangle
        //bottom The Y coordinate of the bottom of the rectangle

        difference = abs(bottom - top);
        a = new Vector2D(0, 9.81);
        pos = new Vector2D(centerX(), bottom);
        v = new Vector2D(0, 0);
        startPosX = pos.getX();
        startPosY = pos.getY();

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


    public double getScala() {
        return scala;
    }

    public double getStartPosX() {
        return startPosX;
    }

    public double getStartPosY() {
        return startPosY;
    }

    public float getDifference() {
        return difference;
    }

    public double getPosX() {
        return pos.getX();
    }

    public void setPosX(double x) {
        pos.setX(x);
    }

    public double getPosY() {
        return pos.getY();
    }

    public void setPosY(double y) {
        pos.setY(y);
    }

    public double getV() {
        return v.getY();
    }

    public void setV(double vY) {
        v.setY(vY);
    }

    public double getA() {
        return a.getY();
    }

    public double getTime()
    {
        return threadFall.getTimeDouble();
    }







}
