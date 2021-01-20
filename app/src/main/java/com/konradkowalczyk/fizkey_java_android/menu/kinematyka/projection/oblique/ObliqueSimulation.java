package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.oblique;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.SubjectFall;
import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;
import com.konradkowalczyk.fizkey_java_android.simulation.ScreenScaleValueEquation;

public class ObliqueSimulation extends BasicSimulation {

    private Paint paint = null;
    private ObliqueCalculations obliqueCalculations;
    private SubjectFall ball;
    private ScreenScaleValueEquation screenScaleValueEquation;


    public ObliqueSimulation(Context context, ObliqueCalculations obliqueCalculations) {
        super(context,Type.SIMULATION);
        setFocusable(true);
        this.obliqueCalculations=obliqueCalculations;
        this.screenScaleValueEquation = new ScreenScaleValueEquation.Builder
                (obliqueCalculations.getyPostions(),obliqueCalculations.getxPositions()).spaceBetweenUnits(100).build();
        setConstans();
    }



    public void setConstans() {

        //thread od interfejsu głównego (ekran)
        getHolder().addCallback(this);

        //Paint
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.rgb(22, 155, 222));

        //tworzenie obiektu, który się porusza
        ball = new SubjectFall(400, 475, screenScaleValueEquation, new Boolean[]{true, true});


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //napisy
        canvas.drawRGB(255, 255, 255);
        paint.setColor(Color.rgb(22, 155, 222));

//        canvas.drawText("time = "+String.format("%.2f", ball.getTime()),20, Constants.SCREEN_HEIGHT-90,paint);
//        canvas.drawText("y = "+String.format("%.2f", ball.getBottom()),20,Constants.SCREEN_HEIGHT-30,paint);

        //scala
        int i = 1;
        for(Long p: screenScaleValueEquation.getPointsScaleY())
        {
            canvas.drawText(String.valueOf(p), getWidth()-150, i*100, paint);
            i++;
        }

        i = 1;
        for(Long p: screenScaleValueEquation.getPointsScaleX())
        {
            if(i%2 != 0) {
                canvas.drawText(String.valueOf(p), i * 100, getHeight() - 70, paint);
            }
            else {
                canvas.drawText(String.valueOf(p), i * 100, getHeight()-20, paint);
            }
            i++;
        }


        //obiekt
        canvas.drawOval(ball.left,ball.top,ball.right,ball.bottom,paint);

        paint.setColor(Color.rgb(0, 255, 0));

        for(int j = 0; j<10; j++) {
            canvas.drawLine(0
                    , screenScaleValueEquation.getValuesScaledFirstListY().get(
                            screenScaleValueEquation.getValuesScaledFirstListY().size()-1)+j
                    , Constants.SCREEN_WIDTH
                    , screenScaleValueEquation.getValuesScaledFirstListY().get(
                            screenScaleValueEquation.getValuesScaledFirstListY().size()-1)+j
                    , paint);
        }






    }




    @Override
    public void update() {

    }
}
