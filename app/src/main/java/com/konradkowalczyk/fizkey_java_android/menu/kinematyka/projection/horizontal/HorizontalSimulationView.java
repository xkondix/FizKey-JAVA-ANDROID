package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.horizontal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.SubjectFall;
import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;
import com.konradkowalczyk.fizkey_java_android.simulation.ScreenScaleValueEquation;

public class HorizontalSimulationView extends BasicSimulation {

    private Paint paint = null;
    private HorizontalCalculations horizontalCalculations;
    private SubjectFall ball;
    private ScreenScaleValueEquation screenScaleValueEquation;
    private int height,width;


    public HorizontalSimulationView(Context context, AttributeSet attrs) {
        super(context, attrs, Type.SIMULATION);
    }


    public void setConstans(HorizontalCalculations horizontalCalculations, int width, int height) {

        this.height = height;
        this.width = width;

        this.horizontalCalculations = horizontalCalculations;
        this.screenScaleValueEquation = new ScreenScaleValueEquation.Builder
                (horizontalCalculations.getPostionsY(),horizontalCalculations.getPositionsX())
                .height(height)
                .width(width)
                .spaceBetweenUnits(100)
                .build();

        //thread od interfejsu głównego (ekran)
        getHolder().addCallback(this);

        //Paint
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.rgb(22, 155, 222));

        //tworzenie obiektu, który się porusza
        ball = new SubjectFall(400, 475, screenScaleValueEquation, new Boolean[]{true, true});
        ball.createThread();


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //napisy
        canvas.drawRGB(255, 255, 255);
        paint.setColor(Color.rgb(22, 155, 222));
          //arctan(vy/vx)
//        canvas.drawText("time = "+String.format("%.2f", ball.getTime()),20, Constants.SCREEN_HEIGHT-90,paint);
//        canvas.drawText("y = "+String.format("%.2f", ball.getBottom()),20,Constants.SCREEN_HEIGHT-30,paint);


        //draw values of scale X
        for(int i = 0; i<screenScaleValueEquation.getLenX();i++)
        {
            if(i % 2 != 0) {
                canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleX().get(i))
                        , i * 120 + screenScaleValueEquation.getChangeX()
                        , height - 30
                        , paint);
            }
            else{
                canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleX().get(i))
                        , i * 120 + screenScaleValueEquation.getChangeX()
                        , height - 70
                        , paint);
            }
        }

        //draw values of scale Y
        for(int i = 0; i<screenScaleValueEquation.getLenY();i++)
        {
            canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleY().get(i))
                    , 30
                    , i*100+screenScaleValueEquation.getChangeY()
                    , paint);
        }

        paint.setColor(Color.rgb(0, 255, 0));



        for(int j = 0; j<10; j++) {
            canvas.drawLine(screenScaleValueEquation.getValuesScaledSecoundListX().get(0)
                    , screenScaleValueEquation.getValuesScaledFirstListY().get(
                            screenScaleValueEquation.getValuesScaledFirstListY().size()-1)+j
                    , width
                    , screenScaleValueEquation.getValuesScaledFirstListY().get(
                            screenScaleValueEquation.getValuesScaledFirstListY().size()-1)+j
                    , paint);
        }

        for(int j = 0; j<10; j++) {
            canvas.drawLine(screenScaleValueEquation.getValuesScaledSecoundListX().get(0)+j
                    , 0
                    , screenScaleValueEquation.getValuesScaledSecoundListX().get(0)+j
                    , screenScaleValueEquation.getValuesScaledFirstListY().get(
                            screenScaleValueEquation.getValuesScaledFirstListY().size()-1)
                    , paint);
        }

        paint.setColor(Color.rgb(255, 0, 0));


        canvas.drawOval(ball.left,ball.top,ball.right,ball.bottom,paint);


    }


    @Override
    public void pause() {
        ball.onPause();
    }

    @Override
    public void start() {
        ball.onResume();
    }


    @Override
    public void update() {
    }
}


