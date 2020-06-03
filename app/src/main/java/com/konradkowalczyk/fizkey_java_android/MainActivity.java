package com.konradkowalczyk.fizkey_java_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import  com.konradkowalczyk.fizkey_java_android.Menu.MenuGlowneActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ustawienie paska
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ustawienie na całą aplikacje statycznych wartości ekranu
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH = dm.widthPixels;
    }

    public void onClickStart(android.view.View view) {
        Intent intent = new Intent(this, MenuGlowneActivity.class);
        startActivity(intent);
    }

    public void onClickGame(android.view.View view) {

    }


    public void onClickExit(android.view.View view) {
        finish();
    }

    public void onClickEnglish(android.view.View view) {
        setLocale("en-rGB");
    }


    public void setLocale(String lang) {
        java.util.Locale locale;
        if(lang.equals("not-set")){ //use any value for default
            locale = java.util.Locale.getDefault();
        }
        else {
            locale = new java.util.Locale(lang);
        }
        java.util.Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }


}

