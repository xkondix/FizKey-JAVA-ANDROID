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

    private List<Double> firstList,secoundList;

    private TextView firstNameOfPhenomenon,secondNameOfPhenomenon
            ,firstValueOfPhenomenon,secondValueOfPhenomenon;
    private String firstName, secoundName;
    private PlotView wykresView;


    public PlotFragment() {}

    public PlotFragment(List<Double> firstList, List<Double> secoundList) {
        this.firstList=firstList;
        this.secoundList=secoundList;
    }

    public PlotFragment(List<Double> firstList, List<Double> secoundList, String firstName, String secoundName) {
        this.firstList=firstList;
        this.secoundList=secoundList;
        this.firstName=firstName;
        this.secoundName=secoundName;
    }

        @Override
        public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                              android.os.Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

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

                            firstNameOfPhenomenon.setText(firstName);
                            secondNameOfPhenomenon.setText(secoundName);

                    }
                });

            }

            wykresView = (PlotView) view.findViewById(R.id.wykresView);
            wykresView.setArray(firstList,secoundList);
            wykresView.setOnTouchPointValue(this);


            return view;
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