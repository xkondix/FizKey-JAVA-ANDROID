package com.konradkowalczyk.fizkey_java_android.Menu.Kinematyka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
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
        Fragment fragment = new RecyclerViewFragment(new String[]{"Spadek Swobodny"},
                new Class[]{RzutPionowyActivity.class});
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame_kinematyka, fragment);
        ft.commit();

    }


}
