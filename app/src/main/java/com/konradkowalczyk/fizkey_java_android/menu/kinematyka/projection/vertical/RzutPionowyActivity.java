package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.WykresyObliczenia;

public class RzutPionowyActivity extends AppCompatActivity {

    //variables
    private EditText heightEditText,velocityEditText,accelerationEditText, resistanceEditText,massEditText;
    private Button simulateButton, plotButton, sendScoreButton;
    private TextView scoreTextView;
    private Spinner multiScoreSpinner;



    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rzut_pionowy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //EditText
        heightEditText = findViewById(R.id.wysokosc);
        velocityEditText = findViewById(R.id.predkoscPoczatkowa);
        accelerationEditText = findViewById(R.id.przyspieszenie);
        resistanceEditText = findViewById(R.id.opor);
        massEditText = findViewById(R.id.sprezystosc);

        //Button
        simulateButton = findViewById(R.id.symulacja);
        plotButton = findViewById(R.id.wykresy);
        sendScoreButton = findViewById(R.id.wyslij);

        //Spinner
        multiScoreSpinner = findViewById(R.id.spinner);

        //TextView
        scoreTextView = findViewById(R.id.wyniki);

        //add listener to EditText
        heightEditText.addTextChangedListener(generalTextWatcher);
    }

    public  boolean onCreateOptionsMenu(Menu menu)
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

    public void onClickWykresy(View view)
    {
        WykresyObliczenia wykresyObliczenia = new WykresyObliczenia(
                getHeight(),
                getVelocity(),
                getAcceleration(),
                getResistance());

        Intent intent = new Intent(this, RzutPionowyWykresActivity.class);
        intent.putExtra("OBLICZENIA", wykresyObliczenia);
        startActivity(intent);
    }

    public void onClickSymulacja(View view)
    {
//        atrybuty = new RzutyAtrybuty();
//        atrybuty.setY(Double.parseDouble(h.getText().toString()));
//        atrybuty.setX(Constants.SCREEN_HEIGHT/2);
//        Intent intent = new Intent(this, GameVerticalActivity.class);
//        //intent.putExtra("atrybuty",atrybuty);
//        startActivity(intent);

    }

    public void onClickWyslij(android.view.View view)
    {

        String text = scoreTextView.getText().toString();
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

        switch(view.getId()) {
            case R.id.checkBoxOpor:
                if (checked) {
                    resistanceEditText.setEnabled(true);
                } else {
                    resistanceEditText.setEnabled(false);

                }
                break;
        }
    }

    public void onClickOblicz(View view)
    {

        WykresyObliczenia wykresyObliczenia = new WykresyObliczenia(
                getHeight(),
                getVelocity(),
                getAcceleration(),
                getResistance());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1
                ,wykresyObliczenia.getListOfPhenomeno());

        multiScoreSpinner.setAdapter(adapter);
        scoreTextView.setText(
                wykresyObliczenia.getListOfPhenomeno()[wykresyObliczenia.getListOfPhenomeno().length-1]);
    }


    //zmiany związane z EdidText
    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            int i = -1;
            try {

                i = Integer.parseInt(heightEditText.getText().toString());

            }catch(NumberFormatException e){}

            if(i >= 0)
            {
                plotButton.setEnabled(true);
                sendScoreButton.setEnabled(true);
                simulateButton.setEnabled(true);
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


    private void radioBox(String type)
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


        this.heightEditText.setText(String.valueOf(Double.parseDouble(heightEditText.getText().toString())*s));
        if(!velocityEditText.getText().equals("")) {
            this.velocityEditText.setText(String.valueOf(Double.parseDouble(heightEditText.getText().toString()) * s * v));
        }
        if(!accelerationEditText.getText().equals("")) {
            this.accelerationEditText.setText(String.valueOf(Double.parseDouble(heightEditText.getText().toString()) * s * v * v));
        }


    }


}
