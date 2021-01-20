package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical;

import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.RzutySymulacja;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.WykresyObliczenia;

//import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.RzutySymulacja;


public class VerticalProjectionSimulationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WykresyObliczenia atrybuty = (WykresyObliczenia) getIntent().getExtras().getSerializable("atrybuty");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getActionBar().hide();

        setContentView(new RzutySymulacja(this, atrybuty));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }




}
