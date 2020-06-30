package com.konradkowalczyk.fizkey_java_android.thread;

import android.os.Build;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;


public class MainThreadGraphs extends MainThread {

    public MainThreadGraphs(SurfaceHolder mSH, BasicSimulation basicSimulation) {
        super(mSH, basicSimulation);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void run()
    {
        canvas = null;

        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                basicSimulation.draw(canvas);
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
