package com.konradkowalczyk.fizkey_java_android.thread;

import android.graphics.Canvas;
import android.os.Build;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;

abstract public class MainThread extends Thread
{
    protected SurfaceHolder surfaceHolder;
    protected BasicSimulation basicSimulation;
    protected boolean running;
    protected static Canvas canvas;


    public void setRunning(boolean running)
    {
        this.running=running;
    }

    public MainThread(SurfaceHolder mSH, BasicSimulation basicSimulation){
      this.basicSimulation=basicSimulation;
      this.surfaceHolder=mSH;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    abstract public void run();


}
