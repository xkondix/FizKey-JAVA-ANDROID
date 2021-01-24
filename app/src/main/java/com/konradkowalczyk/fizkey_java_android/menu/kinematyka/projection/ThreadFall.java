package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;

import java.util.List;

public class ThreadFall extends Thread {

    private volatile double time = 0.0;
    private static int counter;
    private SubjectFall subjectFall;

    private Object pauseLock;
    private boolean paused;
    private boolean finished;


    public ThreadFall(SubjectFall subjectFall) {

        this.subjectFall = subjectFall;
        this.pauseLock = new Object();
        this.paused = true;
        this.finished = false;
        this.counter = 0;

    }

    public void run() {

        List<Float> y = subjectFall.getScala().getValuesScaledFirstListY();
        List<Float> x = subjectFall.getScala().getValuesScaledSecoundListX();


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
                subjectFall.setY(y.get(counter));
                subjectFall.setX(x.get(counter));

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



