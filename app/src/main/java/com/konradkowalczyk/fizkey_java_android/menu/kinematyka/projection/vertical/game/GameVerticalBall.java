package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.R;

public class GameVerticalBall {

    private final static float MOVE_LEFT = 15.0f;
    private final static float MOVE_RIGHT = 15.0f;
    private boolean isGoingLeft,isGoingRight;
    private int  width, height, numberOfBall = 1;
    private Bitmap ball1;
    private Bitmap ball2;
    private Bitmap ball3;
    private float x, y;
    private GameVerticalFall gameVerticalFall;


    public GameVerticalBall(Resources res, float x, float y)
    {
        ball1 = BitmapFactory.decodeResource(res, R.drawable.ball1);
        ball2 = BitmapFactory.decodeResource(res, R.drawable.ball2);
        ball3 = BitmapFactory.decodeResource(res, R.drawable.ball3);

        this.x=x;
        this.y=y;

        isGoingLeft = false;
        isGoingRight = false;

        gameVerticalFall = new GameVerticalFall(y);

        setSize(100);
        scaledBitmap();

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
    }

    public void setGoingLeft(boolean goingLeft) {
        isGoingLeft = goingLeft;
    }

    public void setGoingRight(boolean goingRight) {
        isGoingRight = goingRight;
    }

    public void updateFall(float time)
    {
        gameVerticalFall.update(time);
    }

    public int getFallY()
    {
        return (int) gameVerticalFall.getY();
    }


    public void setSize(int size)
    {
        this.width=size;
        this.height=size;
    }

    private void scaledBitmap()
    {
        ball1 = Bitmap.createScaledBitmap(ball1, width, height, false);
        ball2 = Bitmap.createScaledBitmap(ball2, width, height, false);
        ball3 = Bitmap.createScaledBitmap(ball3, width, height, false);
    }

    private void moveBallLeft()
    {
        if(x>MOVE_LEFT) {
            this.x -= MOVE_LEFT;
        }

    }

    private void moveBallRight()
    {
        if(x+MOVE_RIGHT+width <= Constants.SCREEN_WIDTH)
        {
            this.x+=MOVE_RIGHT;
        }
    }

    public void moveBallHorizontal()
    {
        if(isGoingRight)
        {
            moveBallRight();
        }
        else if(isGoingLeft)
        {
            moveBallLeft();
        }
    }

    public Bitmap getBall()
    {
        switch(gameVerticalFall.getVelocityLevel())
        {
            case 1:
                return ball1;
            case 2:
                return ball2;
            case 3:
                return ball3;
            default:
                throw new NullPointerException("velocity not found");
        }
    }

}
