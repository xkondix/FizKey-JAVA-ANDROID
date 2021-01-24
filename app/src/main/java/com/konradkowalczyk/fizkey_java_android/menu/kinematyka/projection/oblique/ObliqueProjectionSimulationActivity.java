package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.oblique;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.ProjectionViewModel;

public class ObliqueProjectionSimulationActivity extends AppCompatActivity {

    public static final String CALCUALTIONS = "Calcualtions";

    private TextView timeTextView, xTextView, yTextView, angleTextView, velocityXTextView, velocityYTextView;
    private ObliqueCalculations obliqueCalculations;
    private Fragment obliqueSimulationFragment;

    private ProjectionViewModel projectionViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oblique_projection_simulation);

        obliqueCalculations = (ObliqueCalculations) getIntent().getExtras().getSerializable(CALCUALTIONS);

        timeTextView = findViewById(R.id.time_oblique_projection_simulation_activity);
        xTextView = findViewById(R.id.x_oblique_projection_simulation_activity);
        yTextView = findViewById(R.id.y_oblique_projection_simulation_activity);
        angleTextView = findViewById(R.id.angle_oblique_projection_simulation_activity);
        velocityXTextView = findViewById(R.id.velocity_x_oblique_projection_simulation_activity);
        velocityYTextView = findViewById(R.id.velocity_y_oblique_projection_simulation_activity);


        projectionViewModel = new ViewModelProvider(this).get(ProjectionViewModel.class);
        projectionViewModel.init();

        projectionViewModel.getTimeMutableLiveData().observe(this, time -> {
            timeTextView.setText( "t : "+ time);
        });

        projectionViewModel.getPositionXMutableLiveData().observe(this, x -> {
            xTextView.setText( "x : "+ x);
        });

        projectionViewModel.getPositionYMutableLiveData().observe(this, y -> {
            yTextView.setText( "y : "+ y);
        });

        projectionViewModel.getVelocityXMutableLiveData().observe(this, vX -> {
            velocityXTextView.setText( "vX : "+ vX);
        });

        projectionViewModel.getVelocityYMutableLiveData().observe(this, vY -> {
            velocityYTextView.setText( "vY : "+ vY);
        });

        projectionViewModel.getAngleMutableLiveData().observe(this, angle -> {
            angleTextView.setText( "angle : "+ angle);
        });

        View simulationFrameView = findViewById(R.id.oblique_simulation_frame_projection_simulation_activity);

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

        obliqueSimulationFragment = ObliqueSimulationFragment.newInstance(width, height, obliqueCalculations);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.oblique_simulation_frame_projection_simulation_activity, obliqueSimulationFragment);
        ft.commit();
    }


    public void onClickPlay(View view) {
        ((ObliqueSimulationFragment) obliqueSimulationFragment).start();
    }

    public void onClickStop(View view) {
        ((ObliqueSimulationFragment) obliqueSimulationFragment).pause();
    }

    public void onClickRestart(View view) {
        ((ObliqueSimulationFragment) obliqueSimulationFragment).restart();
    }
}
