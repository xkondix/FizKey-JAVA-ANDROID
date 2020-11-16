package com.konradkowalczyk.fizkey_java_android.plot;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.List;

public class PlotFragment extends Fragment implements OnTouchPointValue {

    private List<Double> another;
    private List<Double> time;
    private TextView firstNameOfPhenomenon,secondNameOfPhenomenon
            ,firstValueOfPhenomenon,secondValueOfPhenomenon;
    private String firstName, secoundName;


    public PlotFragment() {}

    public PlotFragment(List<Double> anotherList, List<Double> timeList) {
        this.another=anotherList;
        this.time=timeList;

    }

    public PlotFragment(List<Double> anotherList, List<Double> timeList, String firstName, String secoundName) {
        this.another=anotherList;
        this.time=timeList;
        this.firstName=firstName;
        this.secoundName=secoundName;




    }
        private PlotView wykresView; // custom view

        // metoda wywoływana w przypadku konieczności utworzenia widoku w obiekcie Fragment
        @Override
        public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                              android.os.Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            // przygotuj do wyświetlenia rozkład fragment_main.xml
            View view =
                    inflater.inflate(R.layout.fragment_wykres, container, false);


            firstNameOfPhenomenon = view.findViewById(R.id.first_name_of_phenomenon);
            secondNameOfPhenomenon = view.findViewById(R.id.second_name_of_phenomenon);
            firstValueOfPhenomenon = view.findViewById(R.id.first_value_of_phenomenon);
            secondValueOfPhenomenon = view.findViewById(R.id.second_value_of_phenomenon);

            if(secoundName!=null)
            {
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(firstName!=null)
                        {
                            firstNameOfPhenomenon.setText(firstName);
                            secondNameOfPhenomenon.setText(secoundName);
                        }
                    }
                });

            }



            // uzyskaj odwołanie do CannonView
            wykresView = (PlotView) view.findViewById(R.id.wykresView);
            wykresView.setArray(another,time);
            wykresView.setOnTouchPointValue(this);



            setRetainInstance(true);

            return view;
        }


        private void setText(String text, TextView textView) {
            textView.setText(text);
        }

        // przygotuj możliwość sterowania głośnością po utworzeniu obiektu Activity
        @Override
        public void onActivityCreated(android.os.Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }


        @Override
        public void respondData(final float valueX, final float valueY) {
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(firstName!=null)
                    {
                        firstValueOfPhenomenon.setText(String.format("%.2f", valueY));
                        secondValueOfPhenomenon.setText(String.format("%.2f", valueX));
                    }
                }
            });

        }
}