package com.konradkowalczyk.fizkey_java_android.menu.kinematyka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.horizontal.HorizonatalProjectionActivity;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.oblique.ObliqueProjectionActivity;
import com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection.vertical.VerticalProjectionActivity;
import  com.konradkowalczyk.fizkey_java_android.R;
import  com.konradkowalczyk.fizkey_java_android.RecyclerViewFragment;

public class KinematykaMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinematyka_menu);

        //pasek zadań
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //tworzenie fragmentu
        Fragment fragment = new RecyclerViewFragment(new String[]{"Rzut pionowy","Rzut ukosny","Rzut poziomy"},
                new Class[]{VerticalProjectionActivity.class, ObliqueProjectionActivity.class, HorizonatalProjectionActivity.class});
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame_kinematyka, fragment);
        ft.commit();

    }


}
