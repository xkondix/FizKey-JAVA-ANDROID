package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.konradkowalczyk.fizkey_java_android.Constants;
import  com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.RzutyAtrybuty;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.WykresyObliczenia;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical.game.GameVerticalActivity;

public class RzutPionowyActivity extends AppCompatActivity {

    //variables
    EditText h,v0,a,opor,sprezystosc;
    Button symuluj,wykresy,wyslijWynik;
    android.widget.TextView wynik;
    android.widget.Spinner spinner;
    RzutyAtrybuty atrybuty;



    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rzut_pionowy);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //variables

        //EditText
        h = findViewById(R.id.wysokosc);
        v0 = findViewById(R.id.predkoscPoczatkowa);
        a = findViewById(R.id.przyspieszenie);
        opor = findViewById(R.id.opor);
        sprezystosc = findViewById(R.id.sprezystosc);

        //Button
        symuluj = findViewById(R.id.symulacja);
        wykresy = findViewById(R.id.wykresy);
        wyslijWynik = findViewById(R.id.wyslij);

        //Spinner
        spinner = findViewById(R.id.spinner);

        //TextView
        wynik = findViewById(R.id.wyniki);

        //add listener to EditText
        h.addTextChangedListener(generalTextWatcher);
            }

    public  boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)    {
        switch(item.getItemId())
        {
           // case R.id.action_wzory:
                //Intent intent = new Intent(this,OrderActivity.class);
                //startActivity(intent);
                //return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickWykresy(android.view.View view)
    {
        WykresyObliczenia ob = new WykresyObliczenia(Float.parseFloat(h.getText().toString()),0f,9.81f);
        Intent intent = new Intent(this, RzutPionowyWykresActivity.class);
        intent.putExtra("OBLICZENIA", ob);
        startActivity(intent);
    }

    public void onClickSymulacja(android.view.View view)
    {
        atrybuty = new RzutyAtrybuty();
        atrybuty.setY(Double.parseDouble(h.getText().toString()));
        atrybuty.setX(Constants.SCREEN_HEIGHT/2);
        Intent intent = new Intent(this, GameVerticalActivity.class);
        //intent.putExtra("atrybuty",atrybuty);
        startActivity(intent);

    }

    public void onClickWyslij(android.view.View view)
    {

        String text = wynik.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        String title = getString(R.string.tittle);
        Intent chosenIntent = Intent.createChooser(intent,title);
        startActivity(chosenIntent);
    }

    public void onClickRadioButton(android.view.View view)
    {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int id = radioGroup.getCheckedRadioButtonId();

        switch(id)
        {
            case R.id.m:
                radioBox("km");
                break;

            case R.id.km:
                radioBox("m");

                break;
        }

    }

    public void onClickCheckbox(android.view.View view)
    {

        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId())
        {
            case R.id.checkBoxOpor:
                if(checked)
                {
                    opor.setEnabled(true);
                }
                else
                {
                    opor.setEnabled(false);

                }
                break;
            case R.id.checkBoxSprezystosc:
                if(checked)
                {
                    sprezystosc.setEnabled(true);

                }
                else
                {
                    sprezystosc.setEnabled(false);

                }
                break;
        }
    }

    public void onClickOblicz(android.view.View view)
    {
        RzutyAtrybuty a1 = new RzutyAtrybuty();
        a1.setY(Double.parseDouble(h.getText().toString()));
        try {
            a1.setAy(Double.parseDouble(a.getText().toString()));
            System.out.println(a1.getAy());

        }catch (Exception e){}
        a1.wypelnij();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a1.getList());
        spinner.setAdapter(adapter);
        wynik.setText(a1.getList()[a1.getList().length-1]);
    }


    //zmiany związane z EdidText
    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            int i = -1;
            try {

                i = Integer.parseInt(h.getText().toString());

            }catch(NumberFormatException e){}

            if(i >= 0)
            {
                wykresy.setEnabled(true);
                wyslijWynik.setEnabled(true);
                symuluj.setEnabled(true);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    public void radioBox(String type)
    {
        double s;
        double v;

        if(type.equals("km"))
        {
            s = 1000;
            v = 3600;
        }
        else
        {
            s = 1/1000;
            v = 1/3600;
        }


        this.h.setText(String.valueOf(Double.parseDouble(h.getText().toString())*s));
        if(!v0.getText().equals("")) {
            this.v0.setText(String.valueOf(Double.parseDouble(h.getText().toString()) * s * v));
        }
        if(!a.getText().equals("")) {
            this.a.setText(String.valueOf(Double.parseDouble(h.getText().toString()) * s * v * v));
        }


    }


}
