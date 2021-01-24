package com.konradkowalczyk.fizkey_java_android.simulation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.konradkowalczyk.fizkey_java_android.thread.MainThread;
import com.konradkowalczyk.fizkey_java_android.thread.MainThreadGraphs;
import com.konradkowalczyk.fizkey_java_android.thread.MainThreadSimulation;

public abstract class BasicSimulation extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private boolean retry;
    private Type type;
    public enum Type {SIMULATION,GRAPHS};


    public BasicSimulation(Context context, AttributeSet attrs,Type type) {
        super(context, attrs);
        getHolder().addCallback(this);
        this.type=type;
    }

    public BasicSimulation(Context context,Type type) {
        super(context);
        getHolder().addCallback(this);
        this.type=type;

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        createThread();
        thread.setRunning(true);
        thread.start();
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        retry = true;
        thread.setRunning(false);
        pause();

        while (retry) {
            try {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {
                Log.e("surfaceDestroyed", "Thread interrupted", e);
            }
        }
    }


    private void createThread()
    {
        if(type.equals(Type.GRAPHS))
        {
            createGraphsThread();
        }
        else
        {
            createSimulationThread();
        }
    }

    abstract public void pause();

    abstract public void start();

    private void createSimulationThread()
    {
        thread = new MainThreadSimulation(getHolder(), this);
    }

    private void createGraphsThread()
    {
        thread = new MainThreadGraphs(getHolder(), this);
    }

    public abstract void update();



}
