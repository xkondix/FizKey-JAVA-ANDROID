package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.WykresyObliczenia;
import com.konradkowalczyk.fizkey_java_android.plot.PlotFragment;


import java.util.List;

public class RzutPionowyWykresActivity extends AppCompatActivity {

    private ViewPager pager;
    private WykresyObliczenia wykresyObliczenia;
    private List<List<Double>> calculations;
    private int numberPhenomenonOne, numberPhenomenonTwo;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rzut_pionowy_wykres);

        wykresyObliczenia = (WykresyObliczenia) getIntent().getExtras().getSerializable("OBLICZENIA");


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
            pager.setCurrentItem(position);
        }

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

    private void setCalculations()
    {
        calculations.add(wykresyObliczenia.getListTime());
        calculations.add(wykresyObliczenia.getListAcceleration());
        calculations.add(wykresyObliczenia.getListHeight());
        calculations.add(wykresyObliczenia.getListVelocity());
        calculations.add(wykresyObliczenia.getListKineticEnergy());
        calculations.add(wykresyObliczenia.getListPotentialEnergy());
        calculations.add(wykresyObliczenia.getListTotalEnergy());

    }

    private void setPlot(int number, int numberTwo)
    {
        Fragment fragment = new PlotFragment(calculations.get(numberPhenomenonOne)
                ,calculations.get(numberPhenomenonTwo));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.plot_frame_layout, fragment);
        ft.commit();
    }






}