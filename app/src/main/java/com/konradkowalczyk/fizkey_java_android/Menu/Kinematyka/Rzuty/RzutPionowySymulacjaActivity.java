package com.konradkowalczyk.fizkey_java_android.Menu.Kinematyka.Rzuty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;


public class RzutPionowySymulacjaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RzutyAtrybuty atrybuty = (RzutyAtrybuty) getIntent().getExtras().getSerializable("atrybuty");
        System.out.println("to jest y w atrybutach "+atrybuty.getY());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getActionBar().hide();

        setContentView(new RzutySymulacja(this, atrybuty));
    }






    //przycisk wyjścia
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }




}
