package com.konradkowalczyk.fizkey_java_android.games.kinematics.projection.vertical;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konradkowalczyk.fizkey_java_android.R;


public class GameVerticalFragment extends Fragment {


    private GameVerticalView gameVerticalView;

    public GameVerticalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_game_vertical,container,false);
        gameVerticalView = (GameVerticalView) view.findViewById(R.id.gameVerticalView);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        gameVerticalView.pause();
    }


}