package com.konradkowalczyk.fizkey_java_android.games.kinematics.projection.vertical;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.RectF;

import com.konradkowalczyk.fizkey_java_android.Constants;

import java.util.Random;

import static com.konradkowalczyk.fizkey_java_android.Constants.*;

public class GameVerticalOpponent {

    private float x, y, width, height;
    private final static Random RANDOM = new Random();



    public GameVerticalOpponent() {
        randmomPosition();
    }



    public void randmomPosition()
    {
        int randomNumber = RANDOM.nextInt(50) + 10;

        this.x = RANDOM.nextInt(SCREEN_WIDTH-150);
        this.y = RANDOM.nextInt(SCREEN_HEIGHT + randomNumber);
        this.width = randomNumber + this.x;
        this.height = randomNumber + this.y;

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
