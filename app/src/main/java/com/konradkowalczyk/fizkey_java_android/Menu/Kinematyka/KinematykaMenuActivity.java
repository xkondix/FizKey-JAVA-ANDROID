package com.konradkowalczyk.fizkey_java_android.Menu.Kinematyka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import  com.konradkowalczyk.fizkey_java_android.Menu.Kinematyka.Rzuty.RzutPionowyActivity;
import  com.konradkowalczyk.fizkey_java_android.R;
import  com.konradkowalczyk.fizkey_java_android.RecyclerViewFragment;

public class KinematykaMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinematyka_menu);

        //pasek zadań
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //tworzenie fragmentu
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
                    return new RecyclerViewFragment(new String[]{"Spadek Swobodny"},
                            new Class[]{RzutPionowyActivity.class});

            }
            return null;
        }

    }


}
