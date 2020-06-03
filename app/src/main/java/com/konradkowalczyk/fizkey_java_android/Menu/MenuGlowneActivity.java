package com.konradkowalczyk.fizkey_java_android.Menu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import  com.konradkowalczyk.fizkey_java_android.Menu.Kinematyka.KinematykaMenuActivity;
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


        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);




    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public androidx.fragment.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    //tworzenie fragmentu
                    return new RecyclerViewFragment(list
                            ,classes);

            }
            return null;
        }

    }





    }
