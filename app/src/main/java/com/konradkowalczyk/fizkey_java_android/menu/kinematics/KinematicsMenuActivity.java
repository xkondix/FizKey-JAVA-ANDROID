package com.konradkowalczyk.fizkey_java_android.menu.kinematics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.horizontal.HorizonatalProjectionActivity;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.oblique.ObliqueProjectionActivity;
import com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection.vertical.VerticalProjectionActivity;
import  com.konradkowalczyk.fizkey_java_android.R;
import  com.konradkowalczyk.fizkey_java_android.RecyclerViewFragment;

public class KinematicsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinematyka_menu);

        //pasek zadań
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //tworzenie fragmentu
        Fragment fragment = new RecyclerViewFragment(new String[]{
                  getResources().getString(R.string.vertical_projection)
                , getResources().getString(R.string.oblique_projection)
                , getResources().getString(R.string.horizontal_projection)},
                new Class[]{
                          VerticalProjectionActivity.class
                        , ObliqueProjectionActivity.class
                        , HorizonatalProjectionActivity.class});

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame_kinematyka, fragment);
        ft.commit();

    }


}
