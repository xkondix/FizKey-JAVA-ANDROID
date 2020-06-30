package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.Rzuty;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;
import  com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.thread.MainThreadSimulation;

import java.util.ArrayList;


public class RzutySymulacja extends BasicSimulation {

    private Paint paint = null;
    private RzutyAtrybuty atrybuty = null;
    private long scala;
    private java.util.List<Long> punkt = new ArrayList<Long>();
    private SubjectFall ball;


        public RzutySymulacja(Context context,RzutyAtrybuty atrybuty) {
            super(context);
            setFocusable(true);
            this.atrybuty=atrybuty;
            setConstans();
        }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThreadSimulation(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }




        @Override
        public void setConstans() {

            //thread od interfejsu głównego (ekran)
            thread = new MainThreadSimulation(getHolder(),this);
            getHolder().addCallback(this);

            //Paint
            paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(android.graphics.Color.rgb(22, 155, 222));

            //stworzenie skali dla ekranu
            scale();

            //tworzenie obiektu, który się porusza
            System.out.println("rzuty + " + atrybuty.getY());
            ball = new SubjectFall(400,(float)(Constants.SCREEN_HEIGHT-((atrybuty.getY()/scala*100))),475,(float)(Constants.SCREEN_HEIGHT-(atrybuty.getY()/scala*100)-75),scala);







        }

        @Override
        public void scale()
        {

            int len = (Constants.SCREEN_HEIGHT/100); // jest to utworzenie ilości przedziałek w skali
            long bufor = 0l;
            long difference;
            scala=1l;

            //szukanie skali
            while(true)
            {
                if(atrybuty.getY()>=bufor && atrybuty.getY() < scala*len)
                {
                    break;
                }
                bufor = scala * len;
                scala = scala * 2;
            }


            bufor = scala * (len);
            difference = bufor / len;
            for(int i = 0; i<len+1;i++)
            {
                punkt.add(bufor);
                bufor = bufor - difference;
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);

            //napisy
            canvas.drawRGB(255, 255, 255);
            canvas.drawText("time = "+Double.toString(ball.getTime()),20,Constants.SCREEN_HEIGHT-90,paint);
            canvas.drawText("y = "+Double.toString(ball.getPosY()),20,Constants.SCREEN_HEIGHT-30,paint);





            //scala
            int i = 1;
            for(Long p: punkt)
            {
                canvas.drawText(String.valueOf(p), getWidth()-150, i*100, paint);
                i++;
            }

            //obiekt
            canvas.drawOval(ball.left,ball.top,ball.right,ball.bottom,paint);

        }

        @Override
        public boolean onTouchEvent(MotionEvent e)
        {
            return true;
        }






}

