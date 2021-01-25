package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.vertical;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionCalculation;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionViewModel;


public class VerticalSimulationFragment extends Fragment {

    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String CALCULATIONS = "calculations";

    private int width, height;
    private ProjectionCalculation projectionCalculation;
    private VerticalSimulationView verticalSimulationView;
    private ProjectionViewModel projectionViewModel;

    public VerticalSimulationFragment() {
        // Required empty public constructor
    }

    public static VerticalSimulationFragment newInstance(int width, int height, ProjectionCalculation projectionCalculations) {
        VerticalSimulationFragment fragment = new VerticalSimulationFragment();
        Bundle args = new Bundle();
        args.putSerializable(CALCULATIONS, projectionCalculations);
        args.putInt(WIDTH, width);
        args.putInt(HEIGHT, height);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectionCalculation = (ProjectionCalculation) getArguments().getSerializable(CALCULATIONS);
            height = getArguments().getInt(HEIGHT);
            width = getArguments().getInt(WIDTH);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_vertical_simulation, container, false);

        projectionViewModel = new ViewModelProvider(getActivity()).get(ProjectionViewModel.class);


        verticalSimulationView = (VerticalSimulationView) view.findViewById(R.id.vertical_simulation_view);
        verticalSimulationView.setConstans(projectionCalculation, width, height);
        verticalSimulationView.setViewModel(projectionViewModel);

        return view;

    }

    public void start() {
        verticalSimulationView.start();
    }

    public void pause() {
        verticalSimulationView.pause();
    }

    public void restart() {
        verticalSimulationView.finish();
        verticalSimulationView.setConstans(projectionCalculation, width, height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}