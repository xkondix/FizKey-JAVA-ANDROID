package com.konradkowalczyk.fizkey_java_android.plot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;
import com.konradkowalczyk.fizkey_java_android.simulation.ScreenScaleValueEquation;

import java.util.List;

public class PlotView extends BasicSimulation {


    private Paint paint;
    private SurfaceHolder holder = getHolder();
    private ScreenScaleValueEquation screenScaleValueEquation;
    private int height,width;



    private OnTouchPointValue onTouchPointValue;



   public PlotView(Context context, AttributeSet attrs) {
       super(context, attrs,Type.GRAPHS);
   }

   public void setArray(List<Double> firstList, List<Double> secoundList)
   {
       holder.addCallback(this);

       paint = new Paint();
       paint.setTextSize(30);
       paint.setColor(android.graphics.Color.rgb(22, 155, 222));

       height = (int) (300 * getContext().getResources().getDisplayMetrics().density);
       width =  (int) Constants.SCREEN_WIDTH;

       screenScaleValueEquation = new ScreenScaleValueEquation
               .Builder(firstList,secoundList)
               .height(height).width(width).build();
   }

    public void setOnTouchPointValue(OnTouchPointValue onTouchPointValue)
    {
        this.onTouchPointValue=onTouchPointValue;
    }



    @Override
    public void draw(Canvas canvas) {
            super.draw(canvas);
            canvas.drawRGB(255, 255, 255);


            //draw  X-axis
            for(int i = 0; i<screenScaleValueEquation.getLenY();i++)
            {
                canvas.drawLine(screenScaleValueEquation.getChangeX()
                        , i*50+screenScaleValueEquation.getChangeY()
                        , width-screenScaleValueEquation.getChangeX()
                        ,i*50+screenScaleValueEquation.getChangeY()
                        , paint);

            }

            //draw Y-axis
            for(int i = 0; i<screenScaleValueEquation.getLenX();i++)
            {
                canvas.drawLine( i*50+screenScaleValueEquation.getChangeX()
                        ,screenScaleValueEquation.getChangeY()
                        ,i*50+screenScaleValueEquation.getChangeX()
                        , height - screenScaleValueEquation.getChangeY()
                        , paint);
            }

            paint.setColor(Color.rgb(255, 0, 0));

            //draw values of scale Y
            for(int i = 0; i<screenScaleValueEquation.getLenY();i++)
            {
                canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleY().get(i))
                        , 30
                        , i*50+screenScaleValueEquation.getChangeY()
                        , paint);
            }

            //draw values of scale X
            for(int i = 0; i<screenScaleValueEquation.getLenX();i++)
            {
                if(i % 2 != 0) {
                    canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleX().get(i))
                            , i * 50 + screenScaleValueEquation.getChangeX()
                            , height - 80
                            , paint);
                }
                else{
                    canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleX().get(i))
                            , i * 50 + screenScaleValueEquation.getChangeX()
                            , height - 30
                            , paint);
                }
            }

            //draw points X Y
            for(int i = 0; i<screenScaleValueEquation.getValuesScaledFirstListY().size();i++)
            {
                canvas.drawCircle(screenScaleValueEquation.getValuesScaledSecoundListX().get(i)
                        ,screenScaleValueEquation.getValuesScaledFirstListY().get(i)
                        , 10, paint);

            }



    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float x = screenScaleValueEquation.fromScaleXToRealValue(event.getX());
        float y = screenScaleValueEquation.fromScaleYToRealValue(event.getY());


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(onTouchPointValue!=null)
                    onTouchPointValue.respondData(x,y);
                break;

        }

        return super.onTouchEvent(event);

    }

    @Override
    public void pause() {
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
        throw new RuntimeException("not to implement");
    }


}
