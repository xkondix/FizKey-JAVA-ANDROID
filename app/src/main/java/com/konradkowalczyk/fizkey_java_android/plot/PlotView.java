package com.konradkowalczyk.fizkey_java_android.plot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlotView extends BasicSimulation {

    private List<Double> firstList;
    private List<Double> secoundList;
    private List<Long> pointsScaleX = new ArrayList<Long>();
    private List<Long> pointsScaleY = new ArrayList<Long>();
    private ArrayList<Float> valuesScaledFirstListY = new ArrayList<Float>();
    private ArrayList<Float> valuesScaledSecoundListX = new ArrayList<Float>();

    private long scalaY,scalaX,buforX,buforY;
    private Paint paint;
    private SurfaceHolder holder = getHolder();
    private int width,heigh,changeX,changeY,lenX,lenY;

    private final static double SPACE_BETWEEN_UNITS = 50.0;

    private OnTouchPointValue onTouchPointValue;



   public PlotView(Context context, AttributeSet attrs) {
       super(context, attrs,Type.GRAPHS);

   }

   public void setArray(List<Double> firstList, List<Double> secoundList)
   {
       this.firstList= getAbs(firstList);
       this.secoundList=getAbs(secoundList);
       setConstans();
   }

    public void setOnTouchPointValue(OnTouchPointValue onTouchPointValue)
    {
        this.onTouchPointValue=onTouchPointValue;
    }


    private void setConstans() {


        holder.addCallback(this);


        paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(android.graphics.Color.rgb(22, 155, 222));


        heigh = (int) (300 * getContext().getResources().getDisplayMetrics().density);
        width =  (int) Constants.SCREEN_WIDTH;
        changeX = 100 + (width%100 > 0 ? (width%100)/2 : 0);
        changeY = 100 + (heigh%100 > 0 ? (heigh%100)/2 : 0);
        lenX = (width-(changeX*2))/ ((int) SPACE_BETWEEN_UNITS)+1;
        lenY = (heigh-(changeY*2))/ ((int) SPACE_BETWEEN_UNITS)+1;
        buforX = 0l;
        buforY = 0l;
        scalaX=1l;
        scalaY=1L;


        scale();
    }

    private void scale() {
       createScaleX();
       createScaleY();

       createAxisX();
       createAxisY();

       createScaledValuesX();
       createScaledValuesY();
    }

    private List<Double> getAbs(List<Double> list)
    {
        ArrayList<Double> d = new ArrayList<>();
        for(int i = 0; i<list.size();i++)
        {
            d.add(Math.abs(list.get(i)));
        }

        return d;
    }


    private void createScaleY()
    {
        while(true)
        {
            if(Collections.max(firstList)>=buforY && Collections.max(firstList) < scalaY*lenY)
            {
                break;
            }
            buforY = scalaY * lenY;
            scalaY = scalaY * 2;
        }
    }

    private void createScaleX()
    {
        while(true)
        {
            if(Collections.max(secoundList)>=buforX && Collections.max(secoundList)< scalaX*lenX)
            {
                break;
            }
            buforX = scalaX * lenX;
            scalaX = scalaX * 2;
        }
    }

    private void createAxisY()
    {
        long differenceY;

        buforY = scalaY * (lenY);
        differenceY = buforY / lenY;
        for(int i = 0; i<lenY;i++)
        {
            pointsScaleY.add(buforY);
            buforY = buforY - differenceY;
        }
        pointsScaleY.add(0l);
        pointsScaleY.remove(0);

    }

    private void createAxisX()
    {
        long differenceX;

        buforX = scalaX * (lenX);
        differenceX = buforX / lenX;
        for(int i = 0; i<lenX;i++)
        {
            pointsScaleX.add(buforX);
            buforX = buforX - differenceX;
        }
        pointsScaleX.add(0l);
        Collections.reverse(pointsScaleX);
    }

    private void createScaledValuesX()
    {
        for(int i = 0; i<secoundList.size();i++)
        {
            valuesScaledSecoundListX.add(fromRealValueToScaleX(secoundList.get(i)));
        }
    }

    private void createScaledValuesY()
    {
        for(int i = 0; i<firstList.size();i++)
        {
            valuesScaledFirstListY.add(fromRealValueToScaleY(firstList.get(i)));
        }
    }

    private float fromScaleXToRealValue(double x)
    {
        return (float) (((x - changeX) *scalaX)/SPACE_BETWEEN_UNITS);
    }

    private float fromScaleYToRealValue(double y)
    {
        return (float) (((heigh - y -changeY) *scalaY)/SPACE_BETWEEN_UNITS);
    }

    private float fromRealValueToScaleX(double x)
    {
        return (float) (((x *(SPACE_BETWEEN_UNITS/scalaX))+changeX));
    }

    private float fromRealValueToScaleY(double y)
    {
        return (float) (heigh-((y *(SPACE_BETWEEN_UNITS/scalaY))+changeY));
    }





    @Override
    public void draw(Canvas canvas) {
            super.draw(canvas);
            canvas.drawRGB(255, 255, 255);


            //draw  X-axis
            for(int i = 0; i<lenY;i++)
            {
                canvas.drawLine(changeX, i*50+changeY, width-changeX,i*50+changeY, paint);

            }

            //draw Y-axis
            for(int i = 0; i<lenX;i++)
            {
                canvas.drawLine( i*50+changeX,changeY,i*50+changeX,heigh-changeY, paint);
            }

            paint.setColor(android.graphics.Color.rgb(255, 0, 0));

            //draw values of scale Y
            for(int i = 0; i<lenY;i++)
            {
                canvas.drawText(String.valueOf(pointsScaleY.get(i)), 30, i*50+changeY, paint);
            }

            //draw values of scale X
            for(int i = 0; i<lenX;i++)
            {
                canvas.drawText(String.valueOf(pointsScaleX.get(i)), i*50+changeX, heigh-30, paint);
            }

            //draw points X Y
            for(int i = 0; i<valuesScaledFirstListY.size();i++)
            {
                canvas.drawCircle(valuesScaledSecoundListX.get(i),valuesScaledFirstListY.get(i), 10, paint);

            }



    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float x = fromScaleXToRealValue(event.getX());
        float y = fromScaleYToRealValue(event.getY());


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(onTouchPointValue!=null)
                    onTouchPointValue.respondData(x,y);
                break;

        }

        return super.onTouchEvent(event);

    }

    @Override
    public void update() {
        throw new RuntimeException("not to implement");
    }


}
