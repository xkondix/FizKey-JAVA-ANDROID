package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.horizontal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;


public class HorizontalSimulationFragment extends Fragment {

    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String CALCULATIONS = "calculations";

    private int width, height;
    private HorizontalCalculations horizontalCalculations;
    private HorizontalSimulationView horizontalSimulationView;

    private Object pauseLock;
    private boolean pause;


    public HorizontalSimulationFragment() {
        // Required empty public constructor
    }

    public static HorizontalSimulationFragment newInstance(int width, int height, HorizontalCalculations horizontalCalculations) {
        HorizontalSimulationFragment fragment = new HorizontalSimulationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CALCULATIONS, horizontalCalculations);
        bundle.putInt(WIDTH, width);
        bundle.putInt(HEIGHT, height);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            horizontalCalculations = (HorizontalCalculations) getArguments().getSerializable(CALCULATIONS);
            height = getArguments().getInt(HEIGHT);
            width = getArguments().getInt(WIDTH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_horizontal_simulation, container, false);

        horizontalSimulationView = (HorizontalSimulationView) view.findViewById(R.id.horizontal_simulation_view);
        horizontalSimulationView.setConstans(horizontalCalculations, width, height);

        return view;

    }

    public void start() {
        horizontalSimulationView.start();
    }

    public void pause() {
        horizontalSimulationView.pause();
    }

    public void restart() {
        horizontalSimulationView.setConstans(horizontalCalculations, width, height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}