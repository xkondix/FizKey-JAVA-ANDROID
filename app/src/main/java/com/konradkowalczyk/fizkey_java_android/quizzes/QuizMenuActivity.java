package com.konradkowalczyk.fizkey_java_android.quizzes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz.CreateCustomQuizFragment;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz.SolveCustomQuizFragment;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.login.LoginFragment;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.register.RegisterFragment;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.AuthViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.TaskViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.UserViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizMenuFragment;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizActivity;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizResultDialog;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizResults;

public class QuizMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private TextView status;

    private AuthViewModel authViewModel;
    private UserViewModel userViewModel;
    private TaskViewModel taskViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Dodanie paska aktywności
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); //synchronizacja szuflad

        NavigationView navigationView = findViewById(R.id.navigator_view);
        navigationView.setNavigationItemSelectedListener(this);


        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.init();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init();

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.init();


        //textview w nagłówku
        View headerView = navigationView.getHeaderView(0);
        status = (TextView) headerView.findViewById(R.id.status);
        status.setText(authViewModel.getLoginUserLiveData().getValue() != null ? getResources().getString(R.string.login) : getResources().getString(R.string.logout));

        authViewModel.getLoginUserLiveData().observe(this, auth -> {
            status.setText((auth != null ? getResources().getString(R.string.login) : getResources().getString(R.string.logout)));
        });




        //stworzenie bazowego fragmentu
        Fragment fragment = new QuizMenuFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        Fragment fragment = null;

        switch(id)
        {
            case R.id.menu_quiz:
                fragment = new QuizMenuFragment();
                break;
            case R.id.register:
                fragment = new RegisterFragment();
                break;
            case R.id.login:
                fragment = LoginFragment.newInstance();
                break;
            case R.id.create_custom_quiz_fragment:
                fragment = new CreateCustomQuizFragment();
                break;
            case R.id.solve_custom_quiz_fragment:
                fragment = new SolveCustomQuizFragment();
                break;
            case R.id.logout:
                authViewModel.logout();
                Toast.makeText(this, getResources().getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
                fragment = new QuizMenuFragment();
                break;
            default:
                fragment = new QuizMenuFragment();

        }

        if(fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame,fragment);
            ft.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == QuizMenuFragment.GET_RESULTS_REQUEST && resultCode == RESULT_OK) {
            QuizResults quizResults = data.getParcelableExtra(QuizActivity.RESULTS);

            QuizResultDialog dialog = QuizResultDialog
                    .newInstance(quizResults);
            dialog.show(getSupportFragmentManager(), "Results View");
        }

    }


    public AuthViewModel getAuthViewModel() {
        return authViewModel;
    }

    public UserViewModel getUserViewModel() {
        return userViewModel;
    }
}

