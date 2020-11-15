package com.konradkowalczyk.fizkey_java_android.plot;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.List;

public class PlotFragment extends Fragment {

    private List<Double> another;
    private List<Double> time;

    public PlotFragment() {}

    public PlotFragment(List<Double> anotherList, List<Double> timeList) {
        this.another=anotherList;
        this.time=timeList;

    }
        private PlotView wykresView; // custom view

        // metoda wywoływana w przypadku konieczności utworzenia widoku w obiekcie Fragment
        @Override
        public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                              android.os.Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            // przygotuj do wyświetlenia rozkład fragment_main.xml
            android.view.View view =
                    inflater.inflate(R.layout.fragment_wykres, container, false);

            // uzyskaj odwołanie do CannonView
            wykresView = (PlotView) view.findViewById(R.id.wykresView);
            wykresView.setArray(another,time);

            setRetainInstance(true);

            return view;
        }

        // przygotuj możliwość sterowania głośnością po utworzeniu obiektu Activity
        @Override
        public void onActivityCreated(android.os.Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }





    }