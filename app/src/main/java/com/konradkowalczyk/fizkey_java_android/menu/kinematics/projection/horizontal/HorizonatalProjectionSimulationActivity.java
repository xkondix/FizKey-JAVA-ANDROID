package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.horizontal;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionCalculation;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionViewModel;

public class HorizonatalProjectionSimulationActivity extends AppCompatActivity {

    public static final String CALCUALTIONS = "Calcualtions";

    private TextView timeTextView, xTextView, yTextView, angleTextView, velocityXTextView, velocityYTextView;
    private ProjectionCalculation projectionCalculations;
    private Fragment horizontalSimulationFragment;

    private ProjectionViewModel projectionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizonatl_projection_simulation);
        projectionCalculations = (ProjectionCalculation) getIntent().getExtras().getSerializable(CALCUALTIONS);

        timeTextView = findViewById(R.id.time_horizontal_projection_simulation_activity);
        xTextView = findViewById(R.id.x_horizontal_projection_simulation_activity);
        yTextView = findViewById(R.id.y_horizontal_projection_simulation_activity);
        angleTextView = findViewById(R.id.angle_horizontal_projection_simulation_activity);
        velocityXTextView = findViewById(R.id.velocity_x_horizontal_projection_simulation_activity);
        velocityYTextView = findViewById(R.id.velocity_y_horizontal_projection_simulation_activity);


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
            angleTextView.setText( "Ω : "+ angle);
        });

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

        horizontalSimulationFragment = HorizontalSimulationFragment.newInstance(width, height, projectionCalculations);
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


