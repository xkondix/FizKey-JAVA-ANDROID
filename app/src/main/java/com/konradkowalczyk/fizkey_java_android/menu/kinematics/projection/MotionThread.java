package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection;

import java.util.List;

public class MotionThread extends Thread {

    private volatile double time = 0.0;
    private static int counter;
    private MotionBall motionBall;

    private Object pauseLock;
    private boolean paused;
    private boolean finished;


    public MotionThread(MotionBall motionBall) {

        this.motionBall = motionBall;
        this.pauseLock = new Object();
        this.paused = true;
        this.finished = false;
        this.counter = 0;

    }

    public void run() {

        List<Float> y = motionBall.getScala().getValuesScaledFirstListY();
        List<Float> x = motionBall.getScala().getValuesScaledSecoundListX();


        while (!finished) {

            synchronized (pauseLock) {
                while (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }


            if(counter < y.size())
            {
                motionBall.setY(y.get(counter));
                motionBall.setX(x.get(counter));

                time+=0.01;
                counter++;
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                onFinish();
            }

        }


    }

    public int getTimeInt()
    {
        return (int)time;
    }

    public double getTimeDouble()
    {
        return time;
    }

    public int getCounter()
    {
        return counter;
    }



    public void onPause() {
        synchronized (pauseLock) {
            paused = true;
        }
    }


    public void onResume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public void onFinish()
    {
        finished = true;
    }

    public boolean getStatus(){
        return paused;
    }


}



