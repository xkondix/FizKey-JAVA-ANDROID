package com.konradkowalczyk.fizkey_java_android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.konradkowalczyk.fizkey_java_android.menu.MainMenuActivity;
import com.konradkowalczyk.fizkey_java_android.quizzes.QuizMenuActivity;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Resources resources;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ustawienie paska
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setScreenDimension();
        isNetworkConnected();


    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public void onClickQuizy(View view) {
        Intent intent = new Intent(this, QuizMenuActivity.class);
        startActivity(intent);
    }

    public void onClickExit(View view) {
        finish();
    }

    public void onClickEnglish(View view) {
        changeContext(view,"en");
    }

    public void onClickPolish(View view) {changeContext(view,"pl"); }


    private void changeContext(View view, String language)
    {
        context = LocaleHelper.setLocale(MainActivity.this, language);
        resources = context.getResources();
        finish();
        startActivity(new Intent(this, getClass()));
        view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation_in));
        view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation_out));
    }

    private void setScreenDimension()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH = dm.widthPixels;
    }

    private void isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Constants.isInternet = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        Constants.isInternetMutableLiveData.postValue(Constants.isInternet);
    }
}
