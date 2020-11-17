//package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;
//
//import android.content.Context;
//import android.graphics.Paint;
//import android.view.MotionEvent;
//
//import com.konradkowalczyk.fizkey_java_android.simulation.BasicSimulation;
//
//import java.util.ArrayList;
//
//public class RzutySymulacja extends BasicSimulation {
//
//    private Paint paint = null;
//    private WykresyObliczenia atrybuty = null;
//    private long scala;
//    private java.util.List<Long> punkt = new ArrayList<Long>();
//    private SubjectFall ball;
//
//
//        public RzutySymulacja(Context context, WykresyObliczenia atrybuty) {
//            super(context,Type.SIMULATION);
//            setFocusable(true);
//            this.atrybuty=atrybuty;
//            setConstans();
//        }
//
//
//
//
//
//
//
//        public void setConstans() {
//
//            //thread od interfejsu głównego (ekran)
//            getHolder().addCallback(this);
//
//            //Paint
//            paint = new Paint();
//            paint.setTextSize(50);
//            paint.setColor(android.graphics.Color.rgb(22, 155, 222));
//
//            //stworzenie skali dla ekranu
//            scale();
//
//            //tworzenie obiektu, który się porusza
//            ball = new SubjectFall(400,(float)(Constants.SCREEN_HEIGHT-((atrybuty.getY()/scala*100))),475,(float)(Constants.SCREEN_HEIGHT-(atrybuty.getY()/scala*100)-75),scala);
//
//
//
//
//
//
//
//        }
//
//        public void scale()
//        {
//
//            int len = (Constants.SCREEN_HEIGHT/100); // jest to utworzenie ilości przedziałek w skali
//            long bufor = 0l;
//            long difference;
//            scala=1l;
//
//            //szukanie skali
//            while(true)
//            {
//                if(atrybuty.getY()>=bufor && atrybuty.getY() < scala*len)
//                {
//                    break;
//                }
//                bufor = scala * len;
//                scala = scala * 2;
//            }
//
//
//            bufor = scala * (len);
//            difference = bufor / len;
//            for(int i = 0; i<len+1;i++)
//            {
//                punkt.add(bufor);
//                bufor = bufor - difference;
//            }
//
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//        @Override
//        public void draw(Canvas canvas) {
//            super.draw(canvas);
//
//            //napisy
//            canvas.drawRGB(255, 255, 255);
//            canvas.drawText("time = "+Double.toString(ball.getTime()),20,Constants.SCREEN_HEIGHT-90,paint);
//            canvas.drawText("y = "+Double.toString(ball.getPosY()),20,Constants.SCREEN_HEIGHT-30,paint);
//
//
//
//
//
//            //scala
//            int i = 1;
//            for(Long p: punkt)
//            {
//                canvas.drawText(String.valueOf(p), getWidth()-150, i*100, paint);
//                i++;
//            }
//
//            //obiekt
//            canvas.drawOval(ball.left,ball.top,ball.right,ball.bottom,paint);
//
//        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent e)
//        {
//            return true;
//        }
//
//
//    @Override
//    public void update() {
//
//    }
//}

