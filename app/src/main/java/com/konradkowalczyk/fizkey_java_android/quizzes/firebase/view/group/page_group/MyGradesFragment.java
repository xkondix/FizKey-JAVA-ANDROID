package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.CustomQuizModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskQuizRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.UserViewModel;


public class MyGradesFragment extends Fragment {

    private RecyclerView recyclerViewTasks;
    private TaskQuizRecyclerViewAdapter tasksRecyclerViewadapter;
    private ArrayAdapter<String> studentsAdapter;

    private GroupViewModel groupViewModel;
    private UserViewModel userViewModel;

    private Spinner studentsSpinner;

    public MyGradesFragment() {
        // Required empty public constructor
    }

    public static MyGradesFragment newInstance() {
        MyGradesFragment fragment = new MyGradesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_grades, container, false);

        studentsSpinner = view.findViewById(R.id.students_my_grades_fragment);

        recyclerViewTasks = view.findViewById(R.id.recycler_view_my_grades_fragment);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTasks.setHasFixedSize(true);

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        groupViewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);
        groupViewModel.getTasksDoneCurrentlyUserMutableLiveData().observe(getViewLifecycleOwner(), customQuizModels -> {
            tasksRecyclerViewadapter.submitList(customQuizModels);
        });

        tasksRecyclerViewadapter = new TaskQuizRecyclerViewAdapter();
        recyclerViewTasks.setAdapter(tasksRecyclerViewadapter);


        tasksRecyclerViewadapter.setOnItemClickListener(new TaskQuizRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomQuizModel customQuizModel, int position) {
                groupViewModel.setCurrentlyCustomQuizModelLiveData(new MutableLiveData<>(customQuizModel));
                groupViewModel.setIsGrades(true);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.group_content_frame, DateGradesFragment.newInstance());
                ft.commit();
            }
        });

        if(!userViewModel.getUuid().equals(groupViewModel.getGroupLiveData().getValue().getAuthorUUID())) {
            studentsSpinner.setVisibility(View.GONE);
        }
        else {
            studentsAdapter = new ArrayAdapter<String>(getContext()
                    , android.R.layout.simple_spinner_item
                    , groupViewModel.getNamesOfUsers());
            studentsSpinner.setAdapter(studentsAdapter);
            studentsSpinner.setVisibility(View.VISIBLE);


        }

        studentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String studentUuid = groupViewModel.getGroupLiveData().getValue().getStudents().get(position).getId();
                groupViewModel.setTasksAndGradesCurrentlyUserMutableLiveData(studentUuid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});



        return view;
    }
}