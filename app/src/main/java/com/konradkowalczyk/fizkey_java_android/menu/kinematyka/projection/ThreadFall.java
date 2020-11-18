package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;

public class ThreadFall extends Thread {

    private volatile double time = 0.0;
    private boolean simulate = true;
    private SubjectFall subjectFall;
    private double v;


    public ThreadFall(SubjectFall subjectFall) {

        this.subjectFall=subjectFall;

    }

    public void run() {

        for(Float point : subjectFall.scala.getValuesScaledFirstListY())
        {
            subjectFall.setHeight(point);

            time+=0.01;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    public void setRunning(boolean run)
    {
        simulate=run;
    }






}



