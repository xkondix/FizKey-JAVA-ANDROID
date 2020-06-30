package com.konradkowalczyk.fizkey_java_android.simulation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.konradkowalczyk.fizkey_java_android.thread.MainThread;

public abstract class BasicSimulation extends SurfaceView implements SurfaceHolder.Callback, SimulationInteface
{

    protected MainThread thread=null;


    public BasicSimulation(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public BasicSimulation(Context context) {
        super(context);
    }




    @Override  //onResume()
    abstract public void surfaceCreated(SurfaceHolder holder);

    @Override //onResume()
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override //onPause()
    public void surfaceDestroyed(SurfaceHolder holder) {

        thread.setRunning(false);


        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
