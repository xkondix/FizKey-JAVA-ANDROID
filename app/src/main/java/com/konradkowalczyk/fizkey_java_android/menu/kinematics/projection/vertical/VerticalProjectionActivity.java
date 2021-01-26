package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.vertical;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.PhysicalFormulasDialogFragment;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionCalculation;

public class VerticalProjectionActivity extends AppCompatActivity {

    //variables
    private EditText heightEditText,velocityEditText,accelerationEditText, resistanceEditText,massEditText;
    private TextView scoreTextView;
    private Spinner multiScoreSpinner;
    private String[] formulasVertical;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_projection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //EditText
        heightEditText = findViewById(R.id.height_vertical_projection_activity);
        velocityEditText = findViewById(R.id.start_velocity_vertical_projection_activity);
        accelerationEditText = findViewById(R.id.acceleration_vertical_projection_activity);
        resistanceEditText = findViewById(R.id.resistance_vertical_projection_activity);
        massEditText = findViewById(R.id.mass_vertical_projection_activity);


        //Spinner
        multiScoreSpinner = findViewById(R.id.score_per_secound_vertical_projection_activity);

        //TextView
        scoreTextView = findViewById(R.id.score_vertical_projection_activity);

        formulasVertical = new String[]{getResources().getString(R.string.velocity_projection)
                , "v = v0 - g * t"
                , "h = v0 * t - 1/2 * g * t^2"
                , "Hmax = v0^2 / 2 * g"
                , "tw = v0 / g"
                , "ts = 2 * v0 / g"
                , "------------------"
                , getResources().getString(R.string.free_fall)
                , "y =  h0 - g / 2 * t^2"
                , "v = g - t"
                , "vk = √(2 * g * h0)"};

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
                PhysicalFormulasDialogFragment dialog = new PhysicalFormulasDialogFragment(formulasVertical);
                dialog.show(getSupportFragmentManager(), "Projection Vertical Formulas");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickPlot(View view)
    {

        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 1)
                .acceleration(getAcceleration())
                .mass(getMass())
                .resistance(getResistance())
                .build();

        Intent intent = new Intent(this, VerticalProjectionPlotActivity.class);
        intent.putExtra(VerticalProjectionPlotActivity.CALCULATIONS, projectionCalculation);
        startActivity(intent);
    }

    public void onClickSimulation(View view)
    {
        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 0.01)
                .acceleration(getAcceleration())
                .mass(getMass())
                .resistance(getResistance())
                .build();


        Intent intent = new Intent(this, VerticalProjectionSimulationActivity.class);
        intent.putExtra(VerticalProjectionSimulationActivity.CALCULATIONS, projectionCalculation);
        startActivity(intent);

    }

    public void onClickSend(android.view.View view)
    {

        String text = scoreTextView.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        String title = getString(R.string.tittle);
        Intent chosenIntent = Intent.createChooser(intent,title);
        startActivity(chosenIntent);
    }

    public void onClickCalculate(View view)
    {

        ProjectionCalculation projectionCalculation = new ProjectionCalculation.Builder(
                getHeight(),getVelocity(), 1)
                .acceleration(getAcceleration())
                .mass(getMass())
                .resistance(getResistance())
                .build();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1
                ,projectionCalculation.getVelocities());

        multiScoreSpinner.setAdapter(adapter);
        scoreTextView.setText(projectionCalculation.getVelocityScore());

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
        if(checkLength(velocityEditText.getText().toString()))
        {
            return Double.parseDouble(velocityEditText.getText().toString());
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
