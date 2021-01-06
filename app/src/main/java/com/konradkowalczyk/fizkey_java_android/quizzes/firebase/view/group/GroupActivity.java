package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;

public class GroupActivity extends AppCompatActivity {

    public static final String GROUP_UUID = "group UUID";
    public static final String IS_AUTHOR = "is author";

    GroupViewModel groupViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.init();

        groupViewModel.getGroupsByUUID(getIntent().getStringExtra(GROUP_UUID));
        groupViewModel.getGroupLiveData().observe(this, group ->{
            System.out.println(group.toString());
        });



    }
}