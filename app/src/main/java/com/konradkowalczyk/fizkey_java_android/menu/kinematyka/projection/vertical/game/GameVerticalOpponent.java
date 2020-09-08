package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.RectF;

import com.konradkowalczyk.fizkey_java_android.Constants;

import java.util.Random;

import static com.konradkowalczyk.fizkey_java_android.Constants.*;

public class GameVerticalOpponent {

    private float x, y, width, height;
    private RectF opponent;
    private final static Random RANDOM = new Random();



    public GameVerticalOpponent() {

        this.x=x;
        this.y=y;
        randmomPosition();
//        this.opponent = new RectF(x,y,width,height);
    }



    public void randmomPosition()
    {
        this.x = RANDOM.nextInt(SCREEN_WIDTH-150);
        this.y = RANDOM.nextInt(SCREEN_HEIGHT-150) * (-1);
        this.width = RANDOM.nextInt(SCREEN_WIDTH-150) + this.x;
        this.height = RANDOM.nextInt(SCREEN_HEIGHT-150) + this.y;

    }



    public boolean ifPositionFromGreaterDimensions()
    {
        return y + height > SCREEN_HEIGHT;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        this.height = y + height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
