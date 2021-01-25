package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.oblique;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.ProjectionCalculation;
import com.konradkowalczyk.fizkey_java_android.plot.PlotFragment;

import java.util.ArrayList;
import java.util.List;

public class ObliqueProjectionPlotActivity extends AppCompatActivity {

    public static final String CALCUALTIONS = "Calcualtions";

    private ProjectionCalculation projectionCalculation;
    private List<List<Double>> calculations;
    private int numberPhenomenonOne, numberPhenomenonTwo;
    private Spinner spinnerOne, spinnerTwo;
    private  String[] phenomenonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oblique_projection_plot);

        projectionCalculation = (ProjectionCalculation) getIntent().getExtras().getSerializable(CALCUALTIONS);
        phenomenonNames = getResources().getStringArray(R.array.oblique_projection);


        spinnerOne = findViewById(R.id.phenomenon_one_oblique_projection_plot_activity);
        spinnerOne.setSelection(2);
        numberPhenomenonOne = 2;

        spinnerTwo = findViewById(R.id.phenomenon_two_oblique_projection_plot_activity);
        spinnerTwo.setSelection(3);
        numberPhenomenonTwo = 3;


        Fragment fragment = new PlotFragment(projectionCalculation.getPostionsY(), projectionCalculation.getPositionsX()
                ,phenomenonNames[numberPhenomenonOne]
                ,phenomenonNames[numberPhenomenonTwo]);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.plot_frame_layout_oblique_projection_plot_activity, fragment);
        ft.commit();

        setCalculations();

        if (savedInstanceState != null) {
            int  position= savedInstanceState.getInt("Key");
        }

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                numberPhenomenonOne = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                numberPhenomenonTwo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }



    private void setCalculations()
    {
        calculations = new ArrayList<>();
        calculations.add(projectionCalculation.getTimes());
        calculations.add(projectionCalculation.getAccelerations());
        calculations.add(projectionCalculation.getPostionsY());
        calculations.add(projectionCalculation.getPositionsX());
        calculations.add(projectionCalculation.getVelocityies());
        calculations.add(projectionCalculation.getVelocityiesY());
        calculations.add(projectionCalculation.getVelocityiesX());
        calculations.add(projectionCalculation.getKineticEnergies());
        calculations.add(projectionCalculation.getPotentialEnergies());
        calculations.add(projectionCalculation.getTotalEnergies());

    }

    private void setPlot()
    {
        Fragment fragment = new PlotFragment(calculations.get(numberPhenomenonOne)
                ,calculations.get(numberPhenomenonTwo)
                ,phenomenonNames[numberPhenomenonOne]
                ,phenomenonNames[numberPhenomenonTwo]);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.plot_frame_layout_oblique_projection_plot_activity, fragment);
        ft.commit();
    }

    public void onClickChange(View view) {
        setPlot();
    }
}