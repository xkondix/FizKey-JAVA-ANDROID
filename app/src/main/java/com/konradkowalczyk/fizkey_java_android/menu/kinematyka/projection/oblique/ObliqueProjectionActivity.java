package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.oblique;

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
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.ProjectionCalculation;

public class ObliqueProjectionActivity extends AppCompatActivity {


    private EditText heightEditText,startVelocityEditText,accelerationEditText, resistanceEditText,massEditText, angleEditText;
    private Button simulateButton, plotButton, sendScoreButton;
    private TextView scoreTextView;
    private Spinner multiScoreSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oblique_projection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //EditText
        heightEditText = findViewById(R.id.height_oblique_projection_activity);
        startVelocityEditText = findViewById(R.id.start_velocity_oblique_projection_activity);
        accelerationEditText = findViewById(R.id.acceleration_oblique_projection_activity);
        resistanceEditText = findViewById(R.id.resistance_oblique_projection_activity);
        massEditText = findViewById(R.id.mass_oblique_projection_activity);
        angleEditText = findViewById(R.id.angle_oblique_projection_activity);


        //Button
        simulateButton = findViewById(R.id.simulation_oblique_projection_activity);
        plotButton = findViewById(R.id.plot_oblique_projection_activity);
        sendScoreButton = findViewById(R.id.send_oblique_projection_activity);

        //Spinner
        multiScoreSpinner = findViewById(R.id.spinner);

        //TextView
        //scoreTextView = findViewById(R.id.wyniki);

    }


    public void onClickPlot(View view) {
        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 1)
                .acceleration(getAcceleration())
                .angle(getAngle())
                .mass(getMass())
                .resistance(getResistance())
                .build();

        Intent intent = new Intent(this, ObliqueProjectionPlotActivity.class);
        intent.putExtra(ObliqueProjectionPlotActivity.CALCUALTIONS, projectionCalculation);
        startActivity(intent);
    }

    public void onClickSimulation(View view) {
        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 0.01)
                .acceleration(getAcceleration())
                .angle(getAngle())
                .mass(getMass())
                .resistance(getResistance())
                .build();


        Intent intent = new Intent(this, ObliqueProjectionSimulationActivity.class);
        intent.putExtra(ObliqueProjectionSimulationActivity.CALCUALTIONS, projectionCalculation);
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

    private double getAngle()
    {
        if(checkLength(angleEditText.getText().toString()))
        {
            return Double.parseDouble(angleEditText.getText().toString());
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