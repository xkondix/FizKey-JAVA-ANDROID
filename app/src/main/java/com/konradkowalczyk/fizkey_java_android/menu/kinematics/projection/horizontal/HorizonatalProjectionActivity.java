package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.horizontal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.PhysicalFormulasDialogFragment;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionCalculation;

public class HorizonatalProjectionActivity extends AppCompatActivity {

    private EditText heightEditText,startVelocityEditText,accelerationEditText, resistanceEditText,massEditText, angleEditText;
    private Button simulateButton, plotButton, sendScoreButton;
    private TextView scoreTextView;
    private Spinner multiScoreSpinner;

    private String[] formulasHorizontal;


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
        multiScoreSpinner = findViewById(R.id.score_per_secound_horizontal_projection_activity);


        scoreTextView = findViewById(R.id.score_horizontal_projection_activity);

        formulasHorizontal = new String[]{getResources().getString(R.string.horizontal_projection)
                , "vx = v0"
                , "vy = g * t"
                , "x = v0 * t"
                , "y = g / 2 * t^2"
                , "t =  √(2 * y / g)"
                , "Z = v0 * t = v0 * √(2 * y / g)"};


    }

    public  boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)    {
        switch(item.getItemId())
        {
            case R.id.action_wzory:
                PhysicalFormulasDialogFragment dialog = new PhysicalFormulasDialogFragment(formulasHorizontal);
                dialog.show(getSupportFragmentManager(), "Projection Oblique Formulas");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 1)
                .acceleration(getAcceleration())
                .mass(getMass())
                .resistance(getResistance())
                .angle(0)
                .build();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1
                ,projectionCalculation.getHorizontals());

        multiScoreSpinner.setAdapter(adapter);
        scoreTextView.setText(projectionCalculation.getHorizontalScore());
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