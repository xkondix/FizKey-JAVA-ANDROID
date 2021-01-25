package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.oblique;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionCalculation;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionViewModel;


public class ObliqueSimulationFragment extends Fragment {

    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String CALCULATIONS = "calculations";

    private int width, height;
    private ProjectionCalculation projectionCalculation;
    private ObliqueSimulationView obliqueSimulationView;
    private ProjectionViewModel projectionViewModel;

    public ObliqueSimulationFragment() {
        // Required empty public constructor
    }

    public static ObliqueSimulationFragment newInstance(int width, int height, ProjectionCalculation projectionCalculations) {
        ObliqueSimulationFragment fragment = new ObliqueSimulationFragment();
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
        View view =  inflater.inflate(R.layout.fragment_oblique_simulation, container, false);

        projectionViewModel = new ViewModelProvider(getActivity()).get(ProjectionViewModel.class);


        obliqueSimulationView = (ObliqueSimulationView) view.findViewById(R.id.oblique_simulation_view);
        obliqueSimulationView.setConstans(projectionCalculation, width, height);
        obliqueSimulationView.setViewModel(projectionViewModel);

        return view;

    }

    public void start() {
        obliqueSimulationView.start();
    }

    public void pause() {
        obliqueSimulationView.pause();
    }

    public void restart() {
        obliqueSimulationView.finish();
        obliqueSimulationView.setConstans(projectionCalculation, width, height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}