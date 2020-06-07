package com.konradkowalczyk.fizkey_java_android;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class WykresFragment extends androidx.fragment.app.Fragment {

    java.util.List<Float> another;
    java.util.List<Float> time;

    public WykresFragment() {

    }


    public WykresFragment(List<Float> firstList, List<Float> secoundList) {
        this.another=firstList;
        this.time=secoundList;

    }
        private WykresView wykresView; // custom view

        // metoda wywoływana w przypadku konieczności utworzenia widoku w obiekcie Fragment
        @Override
        public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                              android.os.Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            // przygotuj do wyświetlenia rozkład fragment_main.xml
            android.view.View view =
                    inflater.inflate(R.layout.fragment_wykres, container, false);

            // uzyskaj odwołanie do CannonView
            wykresView = (WykresView) view.findViewById(R.id.wykresView);
            wykresView.setArray(another,time);
            return view;
        }

        // przygotuj możliwość sterowania głośnością po utworzeniu obiektu Activity
        @Override
        public void onActivityCreated(android.os.Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        // zatrzymaj grę w przypadku wstrzymania MainActivity
        @Override
        public void onPause() {
            super.onPause();
        }

        // w przypadku wstrzymania MainActivity MainActivityFragment zwalnia zasoby
        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }



    }