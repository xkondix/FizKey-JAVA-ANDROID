package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical.game;

public class GameVerticalFall {

    private float y;
    private static final float A = 9.81f;
    private float velocity;

    public GameVerticalFall(float y) {
        this.y = y ;
    }

    public float getY()
    {
       return y;
    }

    public void update(float time)
    {
        velocity = (A*time);
        y = 1500 - (A*(time*time)/2);
        System.out.println(time);

    }


    public int getVelocityLevel() {


        if(velocity<10)
        {
            return 1;
        }
        else if(velocity >= 10 && velocity <50)
        {
            return 2;
        }
        else
        {
            return 3;
        }
    }
}
