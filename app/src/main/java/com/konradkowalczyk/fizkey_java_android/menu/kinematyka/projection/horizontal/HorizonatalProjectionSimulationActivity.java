package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.horizontal;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;

public class HorizonatalProjectionSimulationActivity extends AppCompatActivity {

    public static final String CALCUALTIONS = "Calcualtions";

    private TextView timeTextView, xTextView, yTextView, angleTextView;
    private HorizontalCalculations horizontalCalculations;
    private Fragment horizontalSimulationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizonatl_projection_simulation);
        horizontalCalculations = (HorizontalCalculations) getIntent().getExtras().getSerializable(CALCUALTIONS);

        timeTextView = findViewById(R.id.time_horizontal_projection_simulation_activity);
        xTextView = findViewById(R.id.x_horizontal_projection_simulation_activity);
        yTextView = findViewById(R.id.y_horizontal_projection_simulation_activity);
        angleTextView = findViewById(R.id.angle_horizontal_projection_simulation_activity);


        View simulationFrameView = findViewById(R.id.horizontal_simulation_frame_projection_simulation_activity);

        simulationFrameView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (simulationFrameView.getHeight() > 0 && simulationFrameView.getWidth() > 0) {
                    initSimulationView(simulationFrameView.getWidth(), simulationFrameView.getHeight());
                    simulationFrameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

            }
        });



    }

    private void initSimulationView(int width, int height) {

        horizontalSimulationFragment = HorizontalSimulationFragment.newInstance(width, height, horizontalCalculations);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.horizontal_simulation_frame_projection_simulation_activity, horizontalSimulationFragment);
        ft.commit();
    }


    public void onClickPlay(View view) {
        ((HorizontalSimulationFragment) horizontalSimulationFragment).start();
    }

    public void onClickStop(View view) {
        ((HorizontalSimulationFragment) horizontalSimulationFragment).pause();
    }

    public void onClickRestart(View view) {
        ((HorizontalSimulationFragment) horizontalSimulationFragment).restart();
    }
}
