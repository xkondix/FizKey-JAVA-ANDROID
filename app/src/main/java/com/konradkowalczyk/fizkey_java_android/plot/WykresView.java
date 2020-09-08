package com.konradkowalczyk.fizkey_java_android.plot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;
import com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.thread.MainThreadGraphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WykresView extends BasicSimulation {

    private java.util.List<Float> firstList,secoundList;
    private java.util.List<Long> punktX = new ArrayList<Long>();
    private java.util.List<Long> punktY = new ArrayList<Long>();
    private ArrayList<Float> punktXX = new ArrayList<Float>();
    private ArrayList<Float> punktYY = new ArrayList<Float>();

    private long scalaY,scalaX;
    private Paint paint;
    private SurfaceHolder holder = getHolder();
    private int width,heigh,changeX,changeY,lenX,lenY;

   public WykresView(Context context, AttributeSet attrs) {
       super(context, attrs,Type.GRAPHS); // wywołaj konstruktora klasy nadrzędnej

   }

   public void setArray(List<Float> firstList,List<Float> secoundList)
   {
       this.firstList= firstList;
       this.secoundList=secoundList;
       setConstans();
   }



    public void setConstans() {

        //thread od interfejsu głównego (ekran)
       holder.addCallback(this);

        //Paint
        paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(android.graphics.Color.rgb(22, 155, 222));


        heigh = (int) (400 * getContext().getResources().getDisplayMetrics().density);
        width =  (int) Constants.SCREEN_WIDTH;
        changeX = 100 + (width%100 > 0 ? width%100/2 : 0);
        changeY = 100 + (heigh%100 > 0 ? heigh%100/2 : 0);
        lenX = (width-(changeX*2))/50+1;
        lenY = (heigh-(changeY*2))/50+1;


        //stworzenie skali dla ekranu
        scale();

    }

    public void scale() {
        long buforX = 0l;
        long buforY = 0l;
        scalaX=1l;
        scalaY=1L;
        long differenceX,differenceY;

        //szukanie skali dla y
        while(true)
        {
            if(firstList.get(firstList.size()-1)>=buforY && firstList.get(firstList.size()-1) < scalaY*lenY)
            {
                break;
            }
            buforY = scalaY * lenY;
            scalaY = scalaY * 2;
        }

        //szukanie skali dla x
        while(true)
        {
            if(secoundList.get(secoundList.size()-1)>=buforX && secoundList.get(secoundList.size()-1)< scalaX*lenX)
            {
                break;
            }
            buforX = scalaX * lenX;
            scalaX = scalaX * 2;
        }

        //tworzenie x zmiennych skali
        buforX = scalaX * (lenX);
        differenceX = buforX / lenX;
        for(int i = 0; i<lenX;i++)
        {
            punktX.add(buforX);
            buforX = buforX - differenceX;
        }
        punktX.add(0l);
        Collections.reverse(punktX);
        System.out.println(punktX);






        //tworzenie y zmiennych skali
        buforY = scalaY * (lenY);
        differenceY = buforY / lenY;
        for(int i = 0; i<lenY;i++)
        {
            punktY.add(buforY);
            buforY = buforY - differenceY;
        }
        punktY.add(0l);
        punktY.remove(0);


        //tworzenie wspolrzednych dla y osi
        for(int i = 0; i<firstList.size();i++)
        {
            punktYY.add((float) ((firstList.get(i)/scalaY*50)+changeX));
        }

        //tworzenie wspolrzednych dla x osi
        for(int i = 0; i<secoundList.size();i++)
        {
            punktXX.add((float) (secoundList.get(i)/scalaX*50)+changeY);
        }




    }




    @Override
    public void draw(Canvas canvas) {
            super.draw(canvas);
            canvas.drawRGB(255, 255, 255);



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


            for(int i = 0; i<punktYY.size();i++)
            {
                System.out.println(punktXX.get(i)+"    "+punktYY.get(i));
                canvas.drawCircle(punktYY.get(i),heigh-punktXX.get(i), 10, paint);
            }



    }

    @Override
    public void update() {

    }
}
