package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.WykresyObliczenia;
import com.konradkowalczyk.fizkey_java_android.plot.PlotFragment;

import java.util.ArrayList;
import java.util.List;

public class RzutPionowyWykresActivity extends AppCompatActivity {

    private ViewPager pager;
    private WykresyObliczenia wykresyObliczenia;
    private List<List<Double>> calculations;
    private int numberPhenomenonOne, numberPhenomenonTwo;
    private Spinner spinnerOne, spinnerTwo;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rzut_pionowy_wykres);

        wykresyObliczenia = (WykresyObliczenia) getIntent().getExtras().getSerializable("OBLICZENIA");


        spinnerOne = findViewById(R.id.phenomenon_one);
        spinnerOne.setSelection(2);
        numberPhenomenonOne = 2;
        spinnerTwo = findViewById(R.id.phenomenon_two);
        spinnerTwo.setSelection(0);
        numberPhenomenonTwo = 0;



        //Dodanie paska aktywności
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new PlotFragment(wykresyObliczenia.getListHeight(),wykresyObliczenia.getListTime());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.plot_frame_layout, fragment);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        int position = pager.getCurrentItem();
        savedInstanceState.putInt("Key", position );
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Przygotowanie menu i dodanie elementów do paska aplikacji
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.action_wzory:
            //kod po kliknieciu w wzory
                //return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onClickChange(android.view.View view) {
        setPlot();
    }


    private void setCalculations()
    {
        calculations = new ArrayList<>();
        calculations.add(wykresyObliczenia.getListTime());
        calculations.add(wykresyObliczenia.getListAcceleration());
        calculations.add(wykresyObliczenia.getListHeight());
        calculations.add(wykresyObliczenia.getListVelocity());
        calculations.add(wykresyObliczenia.getListKineticEnergy());
        calculations.add(wykresyObliczenia.getListPotentialEnergy());
        calculations.add(wykresyObliczenia.getListTotalEnergy());

    }

    private void setPlot()
    {
        Fragment fragment = new PlotFragment(calculations.get(numberPhenomenonOne)
                ,calculations.get(numberPhenomenonTwo));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.plot_frame_layout, fragment);
        ft.commit();
    }






}