package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.horizontal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionCalculation;

public class HorizonatalProjectionActivity extends AppCompatActivity {

    private EditText heightEditText,startVelocityEditText,accelerationEditText, resistanceEditText,massEditText, angleEditText;
    private Button simulateButton, plotButton, sendScoreButton;
    private TextView scoreTextView;
    private Spinner multiScoreSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizonatl_projection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //EditText
        heightEditText = findViewById(R.id.height_horizontal_projection_activity);
        startVelocityEditText = findViewById(R.id.start_velocity_horizontal_projection_activity);
        accelerationEditText = findViewById(R.id.acceleration_horizontal_projection_activity);
        resistanceEditText = findViewById(R.id.resistance_horizontal_projection_activity);
        massEditText = findViewById(R.id.mass_horizontal_projection_activity);


        //Button
        simulateButton = findViewById(R.id.simulation_horizontal_projection_activity);
        plotButton = findViewById(R.id.plot_horizontal_projection_activity);
        sendScoreButton = findViewById(R.id.send_horizontal_projection_activity);

        //Spinner
        multiScoreSpinner = findViewById(R.id.spinner);

        //TextView
        //scoreTextView = findViewById(R.id.wyniki);

    }

    public void onClickPlot(View view) {
        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 1)
                .acceleration(getAcceleration())
                .mass(getMass())
                .resistance(getResistance())
                .angle(0)
                .build();

        Intent intent = new Intent(this, HorizonatalProjectionPlotActivity.class);
        intent.putExtra(HorizonatalProjectionPlotActivity.CALCUALTIONS,  projectionCalculation);
        startActivity(intent);
    }

    public void onClickSimulation(View view) {
        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 0.01)
                .acceleration(getAcceleration())
                .mass(getMass())
                .resistance(getResistance())
                .angle(0)
                .build();

        Intent intent = new Intent(this, HorizonatalProjectionSimulationActivity.class);
        intent.putExtra(HorizonatalProjectionSimulationActivity.CALCUALTIONS,  projectionCalculation);
        startActivity(intent);
    }

    public void onClickSend(View view) {

        String text = scoreTextView.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        String title = getString(R.string.tittle);
        Intent chosenIntent = Intent.createChooser(intent,title);
        startActivity(chosenIntent);
    }

    public void onClickCalculate(View view) {
    }


    private double getHeight()
    {
        if(checkLength(heightEditText.getText().toString()))
        {
            return Double.parseDouble(heightEditText.getText().toString());
        }

        return 0;
    }

    private double getVelocity()
    {
        if(checkLength(startVelocityEditText.getText().toString()))
        {
            return Double.parseDouble(startVelocityEditText.getText().toString());
        }

        return 0;
    }

    private double getAcceleration()
    {
        if(checkLength(accelerationEditText.getText().toString()))
        {
            return Double.parseDouble(accelerationEditText.getText().toString());
        }

        return 9.81;
    }

    private double getResistance()
    {
        if(checkLength(resistanceEditText.getText().toString()))
        {
            return Double.parseDouble(resistanceEditText.getText().toString());
        }

        return 0;
    }


    private double getMass()
    {
        if(checkLength(massEditText.getText().toString()))
        {
            return Double.parseDouble(massEditText.getText().toString());
        }

        return 1;
    }

    private boolean checkLength(String word)
    {
        if(word.length()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}