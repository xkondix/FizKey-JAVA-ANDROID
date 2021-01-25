package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.horizontal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.MotionBall;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.ProjectionCalculation;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.ProjectionViewModel;
import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;
import com.konradkowalczyk.fizkey_java_android.simulation.ScreenScaleValueEquation;

public class HorizontalSimulationView extends BasicSimulation {

    private Paint paint = null;
    private ProjectionCalculation projectionCalculations;
    private MotionBall ball;
    private ScreenScaleValueEquation screenScaleValueEquation;
    private int height,width;

    private ProjectionViewModel projectionViewModel;


    public HorizontalSimulationView(Context context, AttributeSet attrs) {
        super(context, attrs, Type.SIMULATION);
    }


    public void setConstans(ProjectionCalculation projectionCalculation, int width, int height) {

        this.height = height;
        this.width = width;

        this.projectionCalculations = projectionCalculation;
        this.screenScaleValueEquation = new ScreenScaleValueEquation.Builder
                (projectionCalculation.getPostionsY(),projectionCalculation.getPositionsX())
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
        ball = new MotionBall(screenScaleValueEquation.getPointsScaleY().get(0)
                , screenScaleValueEquation.getPointsScaleY().get(0) + 40
                ,screenScaleValueEquation
                ,new Boolean[]{true, true});
        ball.createThread();


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //napisy
        canvas.drawRGB(255, 255, 255);
        paint.setColor(Color.rgb(22, 155, 222));


        //draw values of scale X
        for(int i = 0; i<screenScaleValueEquation.getLenX();i++)
        {
            if(i % 2 != 0) {
                canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleX().get(i))
                        , i * 100 + screenScaleValueEquation.getChangeX()
                        , height - 10
                        , paint);
            }
            else{
                canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleX().get(i))
                        , i * 100 + screenScaleValueEquation.getChangeX()
                        , height - 50
                        , paint);
            }
        }

        //draw values of scale Y
        for(int i = 0; i<screenScaleValueEquation.getLenY();i++)
        {
            canvas.drawText(String.valueOf(screenScaleValueEquation.getPointsScaleY().get(i))
                    , 10
                    , i*100+screenScaleValueEquation.getChangeY()
                    , paint);
        }

        paint.setColor(Color.rgb(0, 255, 0));

        //draw x-axis

        for(int j = 0; j<10; j++) {
            canvas.drawLine(screenScaleValueEquation.getValuesScaledSecoundListX().get(0)
                    , screenScaleValueEquation.getValuesScaledFirstListY().get(
                            screenScaleValueEquation.getValuesScaledFirstListY().size()-1)+j
                    , width
                    , screenScaleValueEquation.getValuesScaledFirstListY().get(
                            screenScaleValueEquation.getValuesScaledFirstListY().size()-1)+j
                    , paint);
        }

        //draw y-axis

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

    public void finish()
    {
        ball.onFinish();
    }

    public void setViewModel(ViewModel projectionViewModel)
    {
        this.projectionViewModel = (ProjectionViewModel) projectionViewModel;
    }

    @Override
    public void update() {
        if(!ball.getStatus()) {
            int counter = ball.getCounter() - 1;
            projectionViewModel.setAngleMutableLiveData(
                    projectionCalculations.getDegrees().get(counter));
            projectionViewModel.setPositionXMutableLiveData(
                    projectionCalculations.getPositionsX().get(counter));
            projectionViewModel.setPositionYMutableLiveData(
                    projectionCalculations.getPostionsY().get(counter));
            projectionViewModel.setVelocityXMutableLiveData(
                    projectionCalculations.getVelocityiesX().get(counter));
            projectionViewModel.setVelocityYMutableLiveData(
                    projectionCalculations.getVelocityiesY().get(counter));
            projectionViewModel.setTimeMutableLiveData(
                    projectionCalculations.getTimes().get(counter));
        }
    }
}


