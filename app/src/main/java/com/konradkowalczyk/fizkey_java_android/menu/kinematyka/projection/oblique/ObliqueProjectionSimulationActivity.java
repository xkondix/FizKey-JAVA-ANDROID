package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.oblique;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.konradkowalczyk.fizkey_java_android.R;

public class ObliqueProjectionSimulationActivity extends AppCompatActivity {

    public static final String CALCUALTIONS = "Calcualtions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oblique_projection_simulation);

        ObliqueCalculations obliqueCalculations = (ObliqueCalculations) getIntent().getExtras().getSerializable(CALCUALTIONS);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); //landspace

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getActionBar().hide();

        setContentView(new ObliqueSimulation(this, obliqueCalculations));
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
