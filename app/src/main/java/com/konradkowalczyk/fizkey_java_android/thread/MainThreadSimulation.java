package com.konradkowalczyk.fizkey_java_android.thread;

import android.view.SurfaceHolder;

import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;

public class MainThreadSimulation extends MainThread {



    public MainThreadSimulation(SurfaceHolder mSH, BasicSimulation basicSimulation) {
        super(mSH, basicSimulation);
    }

    @Override
    public void run() {
        while(running)
        {
            canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    basicSimulation.draw(canvas);
                    basicSimulation.update();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }




    }
}
