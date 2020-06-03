package com.konradkowalczyk.fizkey_java_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WykresView extends BasicSimulation{

    private java.util.List<Double> firstList,secoundList;
    private java.util.List<Long> punktX = new ArrayList<Long>();
    private java.util.List<Long> punktY = new ArrayList<Long>();

    private String first,secound;
    private long scalaY,scalaX;
    private Paint paint;
    private SurfaceHolder holder = getHolder();

   public WykresView(Context context, AttributeSet attrs) {
       super(context, attrs); // wywołaj konstruktora klasy nadrzędnej

       setConstans();


   }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThreadGraphs(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }



    @Override
    public void setConstans() {

        //thread od interfejsu głównego (ekran)
       thread = new MainThreadGraphs(holder,this);
       holder.addCallback(this);

        //Paint
        paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(android.graphics.Color.rgb(22, 155, 222));

        firstList = Arrays.asList(1.0,2.0,3.0,4.0,5.0);
        secoundList = Arrays.asList(1.0,2.0,3.0,4.0,5.0);
        first="m/s";
        secound = "m";


        //stworzenie skali dla ekranu
        scale();

    }

    @Override
    public void scale() {
        int heigh = (int) (400 * getContext().getResources().getDisplayMetrics().density);
        int width =  (int) Constants.SCREEN_WIDTH;
        int changeX = 100 + ((width%100)/2);
        int changeY = 100 + ((heigh%100)/2);
        int lenX = (width-(changeX*2))/50+1;
        int lenY = (heigh-(changeY*2))/50+1;
        long buforX = 0l;
        long buforY = 0l;
        long differenceX,differenceY;
        scalaX=1l;
        scalaY=1L;
        firstList = Arrays.asList(new Double[]{0.0,10.0,22.0,34.0,48.0,60.0,100.0});
        secoundList = Arrays.asList(new Double[]{1.0,2.0,3.0,4.0,5.0,6.0,7.0});

        //szukanie skali
        while(true)
        {
            if(firstList.get(firstList.size()-1)>=buforY && firstList.get(firstList.size()-1) < scalaY*lenY)
            {
                break;
            }
            buforY = scalaY * lenY;
            scalaY = scalaY * 2;
        }

        while(true)
        {
            if(secoundList.get(secoundList.size()-1)>=buforX && secoundList.get(secoundList.size()-1)< scalaX*lenX)
            {
                break;
            }
            buforX = scalaX * lenX;
            scalaX = scalaX * 2;
        }


        buforX = scalaX * (lenX);
        differenceX = buforX / lenX;
        for(int i = 0; i<lenX-1;i++)
        {
            punktX.add(buforX);
            buforX = buforX - differenceX;
        }
        Collections.reverse(punktX);

        punktX.add(0,0l);





        buforY = scalaY * (lenY);
    differenceY = buforY / lenY;
        for(int i = 0; i<lenY-1;i++)
    {
        punktY.add(buforY);
        buforY = buforY - differenceY;
    }
        punktY.add(0l);




    }




    @Override
    public void draw(Canvas canvas) {
            super.draw(canvas);


            canvas.drawRGB(255, 255, 255);
            int heigh = (int) (400 * getContext().getResources().getDisplayMetrics().density);
            int width =  (int) Constants.SCREEN_WIDTH;
            int changeX = 100 + ((width%100)/2);
            int changeY = 100 + ((heigh%100)/2);
            int lenX = (width-(changeX*2))/50+1;
            int lenY = (heigh-(changeY*2))/50+1;


;
        for(int i = 0; i<lenY;i++)
            {
                canvas.drawLine(changeX, i*50+changeY, width-changeX,i*50+changeY, paint);

            }

            for(int i = 0; i<lenX;i++)
            {
                canvas.drawLine( i*50+changeX,changeY,i*50+changeX,heigh-changeY, paint);
            }

            paint.setColor(android.graphics.Color.rgb(255, 0, 0));

            for(int i = 0; i<lenY;i++)
            {
                canvas.drawText(String.valueOf(punktY.get(i)), 30, i*50+changeY, paint);
            }

            for(int i = 0; i<lenX;i++)
            {
                canvas.drawText(String.valueOf(punktX.get(i)), i*50+changeX, heigh-30, paint);
            }



    }
}
