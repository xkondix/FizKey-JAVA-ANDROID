package com.konradkowalczyk.fizkey_java_android.games.kinematics.projection.vertical;

public class GameVerticalFall {

    private static final float A = 9.81f;
    private static final int SCALE = 1000;

    private float y;
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
        velocity = (A*time)/1000;
        y = 1500 - (A*(time*time)/2);
        System.out.println(time);

    }

    public float getVelocity()
    {
        return velocity;
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
