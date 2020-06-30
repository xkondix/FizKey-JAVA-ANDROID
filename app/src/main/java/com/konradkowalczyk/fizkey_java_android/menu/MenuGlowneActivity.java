package com.konradkowalczyk.fizkey_java_android.menu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import  com.konradkowalczyk.fizkey_java_android.menu.kinematyka.KinematykaMenuActivity;
import  com.konradkowalczyk.fizkey_java_android.R;
import  com.konradkowalczyk.fizkey_java_android.RecyclerViewFragment;

public class MenuGlowneActivity extends AppCompatActivity {

    static String[] list = {"Kinematyka","Mechanika","Ruch Falowy","Fizyka Jądrowa","Zjawisko Fotoelektryczne"};
    static Class[] classes = {KinematykaMenuActivity.class};
    @Override
    protected void
    onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_glowne);


        //pasek zadań
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //przycisk cofnij
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Fragment fragment = new RecyclerViewFragment(list
                ,classes);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame_glowne, fragment);
        ft.commit();


    }








    }
