package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.UserViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizMenuFragment;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizActivity;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizResultDialog;
import com.konradkowalczyk.fizkey_java_android.quizzes.quizy.QuizResults;

public class GroupActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String GROUP_UUID = "group UUID";
    public static final String USER_UUID = " user";

    private TextView welcomeTextView;

    private GroupViewModel groupViewModel;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.group_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); //synchronizacja szuflad

        NavigationView navigationView = findViewById(R.id.group_navigator_view);
        navigationView.setNavigationItemSelectedListener(this);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.init();
        groupViewModel.getGroupsByUUID(getIntent().getStringExtra(GROUP_UUID));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init();
        userViewModel.getUserByUuid(getIntent().getStringExtra(USER_UUID));

        View headerView = navigationView.getHeaderView(0);
        welcomeTextView = (TextView) headerView.findViewById(R.id.welcome_name_nav_head);

        userViewModel.getLiveDataUser().observe(this, user ->
        {
            welcomeTextView.setText(getResources().getString(R.string.welcome) + " " + user.getName());
        });

        groupViewModel.getGroupLiveData().observe(this, group ->
        {
            if(!group.getAuthorUUID().equals(getIntent().getStringExtra(USER_UUID))) {
                groupViewModel.setTasksAndGradesCurrentlyUserMutableLiveData(getIntent().getStringExtra(USER_UUID));
                navigationView.getMenu().findItem(R.id.ask_a_task).setVisible(false);
                navigationView.getMenu().findItem(R.id.task_to_be_solved).setVisible(true);
                navigationView.getMenu().findItem(R.id.my_grades).setVisible(true);
                navigationView.getMenu().findItem(R.id.correct_the_grade).setVisible(true);
                navigationView.getMenu().findItem(R.id.send_an_invitation_to_the_group).setVisible(false);
                navigationView.getMenu().findItem(R.id.leave_group).setVisible(true);
            }
            else{
                if(group.getStudents().size() > 0) {
                    groupViewModel.setTasksAndGradesCurrentlyUserMutableLiveData(group.getStudents().get(0).getId());
                }
                navigationView.getMenu().findItem(R.id.ask_a_task).setVisible(true);
                navigationView.getMenu().findItem(R.id.task_to_be_solved).setVisible(false);
                navigationView.getMenu().findItem(R.id.my_grades).setVisible(true);
                navigationView.getMenu().findItem(R.id.correct_the_grade).setVisible(false);
                navigationView.getMenu().findItem(R.id.send_an_invitation_to_the_group).setVisible(true);
                navigationView.getMenu().findItem(R.id.leave_group).setVisible(false);

            }

        });



        Fragment fragment =  HomeGroupFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.group_content_frame, fragment);
        ft.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        Fragment fragment = null;

        switch(id)
        {
            case R.id.group_profile:
                fragment = HomeGroupFragment.newInstance();
                break;
            case R.id.ask_a_task:
                fragment = CreateCustomQuizFragment.newInstance();
                break;
            case R.id.task_to_be_solved:
                fragment = TaskToBeSolvedFragment.newInstance();
                break;
            case R.id.my_grades:
                fragment = MyGradesFragment.newInstance();
                break;
            case R.id.correct_the_grade:
                fragment = CorrectTheGradeFragment.newInstance();
                break;
            case R.id.send_an_invitation_to_the_group:
                fragment = SendInvitationToGroupFragment.newInstance();
                break;
            case R.id.leave_group:
                userViewModel.leaveGroup(groupViewModel.getGroupLiveData());
                groupViewModel.leaveUser(userViewModel.getUuid());
                finish();
                break;
            default:
                fragment =  HomeGroupFragment.newInstance();

        }

        if(fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.group_content_frame,fragment);
            ft.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.group_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = findViewById(R.id.group_drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(groupViewModel.getIsGrades().getValue())
        {
            groupViewModel.setIsGrades(false);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.group_content_frame, MyGradesFragment.newInstance());
            ft.commit();
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
            groupViewModel.updateGrades(userViewModel.getUuid(), quizResults);

            QuizResultDialog dialog = QuizResultDialog
                    .newInstance(quizResults);
            dialog.show(getSupportFragmentManager(), "Results View");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        groupViewModel.onDestroy();
    }
}