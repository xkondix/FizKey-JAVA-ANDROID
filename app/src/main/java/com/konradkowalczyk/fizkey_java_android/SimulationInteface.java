package com.konradkowalczyk.fizkey_java_android;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface SimulationInteface
{
    public void setConstans();
    public void draw(Canvas canvas);
    public boolean onTouchEvent(MotionEvent e);
    public void scale();
}
