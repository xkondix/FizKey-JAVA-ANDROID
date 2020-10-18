package com.konradkowalczyk.fizkey_java_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import  com.konradkowalczyk.fizkey_java_android.menu.MenuGlowneActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Resources resources;

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
        context = LocaleHelper.setLocale(MainActivity.this, "en");
        resources = context.getResources();
        finish();
        startActivity(new Intent(this, getClass()));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void onClickPolish(android.view.View view) {
        context = LocaleHelper.setLocale(MainActivity.this, "pl");
        resources = context.getResources();
        finish();
        startActivity(new Intent(this, getClass()));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }





}

