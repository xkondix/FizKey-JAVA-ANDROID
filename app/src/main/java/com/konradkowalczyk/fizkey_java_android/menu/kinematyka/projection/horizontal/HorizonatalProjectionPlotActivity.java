package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.horizontal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.plot.PlotFragment;

import java.util.ArrayList;
import java.util.List;

public class HorizonatalProjectionPlotActivity extends AppCompatActivity {

    public static final String CALCUALTIONS = "Calcualtions";

    private HorizontalCalculations horizontalCalculations;
    private List<List<Double>> calculations;
    private int numberPhenomenonOne, numberPhenomenonTwo;
    private Spinner spinnerOne, spinnerTwo;
    private  String[] phenomenonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizonatl_projection_plot);

        horizontalCalculations = (HorizontalCalculations) getIntent().getExtras().getSerializable(CALCUALTIONS);
        phenomenonNames = getResources().getStringArray(R.array.horizontal_projection);


        spinnerOne = findViewById(R.id.phenomenon_one_horizontal_projection_plot_activity);
        spinnerOne.setSelection(2);
        numberPhenomenonOne = 2;

        spinnerTwo = findViewById(R.id.phenomenon_two_horizontal_projection_plot_activity);
        spinnerTwo.setSelection(3);
        numberPhenomenonTwo = 3;


        Fragment fragment = new PlotFragment(horizontalCalculations.getPostionsY(),horizontalCalculations.getPositionsX()
                ,phenomenonNames[numberPhenomenonOne]
                ,phenomenonNames[numberPhenomenonTwo]);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.plot_frame_layout_horizontal_projection_plot_activity, fragment);
        ft.commit();

        setCalculations();

        if (savedInstanceState != null) {
            int  position = savedInstanceState.getInt("Key");
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
        calculations.add(horizontalCalculations.getTimes());
        calculations.add(horizontalCalculations.getAccelerations());
        calculations.add(horizontalCalculations.getPostionsY());
        calculations.add(horizontalCalculations.getPositionsX());
        calculations.add(horizontalCalculations.getVelocityiesY());
        calculations.add(horizontalCalculations.getVelocityiesX());
        calculations.add(horizontalCalculations.getKineticEnergies());
        calculations.add(horizontalCalculations.getPotentialEnergies());
        calculations.add(horizontalCalculations.getTotalEnergies());

    }

    private void setPlot()
    {
        Fragment fragment = new PlotFragment(calculations.get(numberPhenomenonOne)
                ,calculations.get(numberPhenomenonTwo)
                ,phenomenonNames[numberPhenomenonOne]
                ,phenomenonNames[numberPhenomenonTwo]);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.plot_frame_layout_horizontal_projection_plot_activity, fragment);
        ft.commit();
    }

    public void onClickChange(View view) {
        setPlot();
    }
}
