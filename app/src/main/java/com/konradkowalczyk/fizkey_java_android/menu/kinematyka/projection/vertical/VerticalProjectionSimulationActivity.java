package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.ProjectionCalculation;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.ProjectionViewModel;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.oblique.ObliqueSimulationFragment;


public class VerticalProjectionSimulationActivity extends AppCompatActivity {

    public static final String CALCULATIONS = "Calcualtions";

    private TextView timeTextView, yTextView, velocityTextView;
    private ProjectionCalculation projectionCalculation;
    private Fragment verticalSimulationFragment;

    private ProjectionViewModel projectionViewModel;


    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_projection_simulation);


        projectionCalculation = (ProjectionCalculation) getIntent().getExtras().getSerializable(CALCULATIONS);

        timeTextView = findViewById(R.id.time_vertical_projection_simulation_activity);
        yTextView = findViewById(R.id.y_vertical_projection_simulation_activity);
        velocityTextView = findViewById(R.id.velocity_vertical_projection_simulation_activity);


        projectionViewModel = new ViewModelProvider(this).get(ProjectionViewModel.class);
        projectionViewModel.init();

        projectionViewModel.getTimeMutableLiveData().observe(this, time -> {
            timeTextView.setText("t : " + time);
        });

        projectionViewModel.getPositionYMutableLiveData().observe(this, y -> {
            yTextView.setText("y : " + y);
        });


        projectionViewModel.getVelocityYMutableLiveData().observe(this, vY -> {
            velocityTextView.setText("v : " + vY);
        });


        View simulationFrameView = findViewById(R.id.vertical_simulation_frame_projection_simulation_activity);

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

        verticalSimulationFragment = VerticalSimulationFragment.newInstance(width, height, projectionCalculation);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.vertical_simulation_frame_projection_simulation_activity, verticalSimulationFragment);
        ft.commit();
    }


    public void onClickPlay(View view) {
        ((VerticalSimulationFragment) verticalSimulationFragment).start();
    }

    public void onClickStop(View view) {
        ((VerticalSimulationFragment) verticalSimulationFragment).pause();
    }

    public void onClickRestart(View view) {
        ((VerticalSimulationFragment) verticalSimulationFragment).restart();
    }
}