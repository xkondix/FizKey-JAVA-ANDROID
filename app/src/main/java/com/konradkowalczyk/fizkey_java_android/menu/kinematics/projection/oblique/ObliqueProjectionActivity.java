package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.oblique;

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

public class ObliqueProjectionActivity extends AppCompatActivity {


    private EditText heightEditText,startVelocityEditText,accelerationEditText, resistanceEditText,massEditText, angleEditText;
    private Button simulateButton, plotButton, sendScoreButton;
    private TextView scoreTextView;
    private Spinner multiScoreSpinner;

    private String[] formulasOblique;


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
        multiScoreSpinner = findViewById(R.id.score_per_secound_oblique_projection_activity);


        scoreTextView = findViewById(R.id.score_oblique_projection_activity);

        formulasOblique = new String[]{getResources().getString(R.string.oblique_projection)
                , "v0x = v0 * cos α"
                , "v0y = v0 * sin α"
                , "vx = v0 * cos α"
                , "vy = v0 * sin α - g * t"
                , "x = v0x * t = v0 * t * cos α"
                , "y = v0y * t - g / 2 * t^2"
                , "y = v0 * t * sin α - g / 2 * t^2"
                , "ts =  2 * v0y / g"
                , "ts =  2 * v0y * sin α / g"
                , "tw = v0y /  g"
                , "tw = v0y * sin α /  g"
                , "Z = v0^2 / g * sin 2 * α "
                , "Hmax = v0y^2 / 2 * g"
                , "Hmax = v0^2 * sin^2  α  / 2 * g"};


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
                PhysicalFormulasDialogFragment dialog = new PhysicalFormulasDialogFragment(formulasOblique);
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

        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 1)
                .acceleration(getAcceleration())
                .angle(getAngle())
                .mass(getMass())
                .resistance(getResistance())
                .build();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1
                ,projectionCalculation.getObliques());

        multiScoreSpinner.setAdapter(adapter);
        scoreTextView.setText(projectionCalculation.getObliqueScore());
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