package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.horizontal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionCalculation;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.ProjectionViewModel;


public class HorizontalSimulationFragment extends Fragment {

    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String CALCULATIONS = "calculations";

    private int width, height;
    private ProjectionCalculation projectionCalculation;
    private HorizontalSimulationView horizontalSimulationView;
    private ProjectionViewModel projectionViewModel;



    public HorizontalSimulationFragment() {
        // Required empty public constructor
    }

    public static HorizontalSimulationFragment newInstance(int width, int height, ProjectionCalculation projectionCalculation) {
        HorizontalSimulationFragment fragment = new HorizontalSimulationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CALCULATIONS, projectionCalculation);
        bundle.putInt(WIDTH, width);
        bundle.putInt(HEIGHT, height);
        fragment.setArguments(bundle);
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
        View view =  inflater.inflate(R.layout.fragment_horizontal_simulation, container, false);

        projectionViewModel = new ViewModelProvider(getActivity()).get(ProjectionViewModel.class);


        horizontalSimulationView = (HorizontalSimulationView) view.findViewById(R.id.horizontal_simulation_view);
        horizontalSimulationView.setConstans(projectionCalculation, width, height);
        horizontalSimulationView.setViewModel(projectionViewModel);

        return view;

    }

    public void start() {
        horizontalSimulationView.start();
    }

    public void pause() {
        horizontalSimulationView.pause();
    }

    public void restart() {
        horizontalSimulationView.finish();
        horizontalSimulationView.setConstans(projectionCalculation, width, height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}