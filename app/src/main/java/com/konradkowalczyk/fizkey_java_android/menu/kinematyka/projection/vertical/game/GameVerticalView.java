package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.Constants;
import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;

public class GameVerticalView extends BasicSimulation {

    private Activity activity;
    private Paint paint;
    private double time;
    private GameVerticalBall gameVerticalBall;
    private GameVerticalOpponent gameVerticalOpponent;

    public GameVerticalView(Context context, AttributeSet attrs) {
        super(context,attrs,Type.SIMULATION);
        activity = (Activity) context;
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(android.graphics.Color.rgb(22, 155, 222));
        setConstans();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void draw(Canvas canvas) {
        super.draw(canvas);


        //napisy
        canvas.drawRGB(255, 255, 255);
        canvas.drawText(String.valueOf(gameVerticalBall.getFallY()),Constants.SCREEN_WIDTH-150,100,paint);
        canvas.drawBitmap(gameVerticalBall.getBall(),gameVerticalBall.getX(),gameVerticalBall.getY(),paint);
        canvas.drawOval(gameVerticalOpponent.getX(),gameVerticalOpponent.getY(),gameVerticalOpponent.getWidth(),gameVerticalOpponent.getHeight(),paint);


    }


    public void update()
    {
       // gameVerticalBall.setY(gameVerticalBall.getY()+15);
        gameVerticalOpponent.setY(gameVerticalOpponent.getY()+5);
        if(gameVerticalOpponent.ifPositionFromGreaterDimensions())
        {
            gameVerticalOpponent.randmomPosition();
        }
        gameVerticalBall.updateFall((float) time);
        incerementTime();
        sleep();
        moveBall();

    }

    public void moveBall()
    {
        gameVerticalBall.moveBallHorizontal();
    }


    private void incerementTime()
    {
        time+=0.01;
    }

    private void sleep()
    {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setConstans()
    {
        time=0.0;
        gameVerticalBall = new GameVerticalBall(activity.getResources(),500,1500);
        gameVerticalOpponent = new GameVerticalOpponent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() <= Constants.SCREEN_WIDTH / 2) {
                    gameVerticalBall.setGoingLeft(true);
                }
                else
                {
                    gameVerticalBall.setGoingRight(true);

                }
                break;

            case MotionEvent.ACTION_UP:
                gameVerticalBall.setGoingLeft(false);
                gameVerticalBall.setGoingRight(false);
                break;
        }

        return true;
    }




//    private void showGameOverDialog(final int messageId) {
//        // obiekt DialogFragment wyświetlający wynik gry i mogący uruchomić kolejną grę
//        final DialogFragment gameResult =
//                new DialogFragment() {
//                    // utwórz obiekt AlertDialog i go zwróć
//                    @Override
//                    public Dialog onCreateDialog(Bundle bundle) {
//                        // utwórz okno wyświetlające łańcuch messageId
//                        AlertDialog.Builder builder =
//                                new AlertDialog.Builder(getActivity());
//                        builder.setTitle(getResources().getString(messageId));
//
//                        // wyświetl liczbę oddanych strzałów i całkowity czas gry
//                        builder.setMessage(getResources().getString(
//                                R.string.results_format, shotsFired, totalElapsedTime));
//                        builder.setPositiveButton(R.string.reset_game,
//                                new DialogInterface.OnClickListener() {
//                                    // metoda wywoływana po wciśnięciu przez użytkownika przycisku Uruchom ponownie
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        dialogIsDisplayed = false;
//                                        newGame(); // przygotuj i uruchom nową grę
//                                    }
//                                }
//                        );
//
//                        return builder.create(); // zwróć obiekt AlertDialog
//                    }
//                };
//
//        // wyświetl obiekt DialogFragment za pomocą menedżera FragmentManager w wątku interfejsu użytkownika
//        activity.runOnUiThread(
//                new Runnable() {
//                    public void run() {
//                        final AlertDialog gameResult = builder.create();
//                        showSystemBars();
//                        dialogIsDisplayed = true;
//                        gameResult.setCancelable(false); // modal dialog
//                        // gameResult.show(activity.getFragmentManager(), "results");
//                        gameResult.show();
//                    }
//                }
//        );
//    }


}
