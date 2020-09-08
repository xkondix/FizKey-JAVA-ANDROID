package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;

import  com.konradkowalczyk.fizkey_java_android.Constants;
import static java.lang.Math.sqrt;

public class ThreadFall extends Thread {

    private volatile double time = 0.0;
    private boolean simulate = true;
    private SubjectFall subjectFall;
    private double v;


    public ThreadFall(SubjectFall subjectFall) {

        this.subjectFall=subjectFall;
        System.out.println("rzuty thrad+ " + subjectFall.getPosY());

    }

    public void run() {

        while(simulate)
        {
            updateVector();
            updateSubject();
            time+=0.001;
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(getTimeDouble()>=sqrt(((subjectFall.getStartPosY())*2/9.81)))
            {
                simulate = false;
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

    public void updateVector()
    {
        double v0 = subjectFall.getV() + subjectFall.getA()*0.001;
        double y0 = subjectFall.getPosY() - v0 * 0.001;
        subjectFall.setV(v0);
        subjectFall.setPosY(y0);

    }

    public void updateSubject()
    {
        subjectFall.bottom = (float) (Constants.SCREEN_HEIGHT-((subjectFall.getPosY())));
        subjectFall.top = subjectFall.bottom - subjectFall.getDifference();
    }

    public void setRunning(boolean run)
    {
        simulate=run;
    }






}



