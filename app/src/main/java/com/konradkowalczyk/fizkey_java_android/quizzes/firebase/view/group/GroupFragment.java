package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.GroupMenuRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.FirestoreInstance;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.UserViewModel;


public class GroupFragment extends Fragment implements CreateGroupDialogFragment.OnFeedBack{

    private RecyclerView recyclerViewMyGorups, recyclerViewGroups;
    private Button jointTheGroupButton, createGroupButton;
    private EditText enterCodeEditText;

    private GroupMenuRecyclerViewAdapter myGroupsAdapter, groupsAdapter;
    private GroupViewModel groupViewModel;
    private UserViewModel userViewModel;

    public GroupFragment() {
    }

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_group, container, false);

        createGroupButton = view.findViewById(R.id.create_group_fragment);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateGroupDialogFragment dialog = CreateGroupDialogFragment.newInstance();
                dialog.setTargetFragment(GroupFragment.this, 2);
                dialog.show(getActivity().getSupportFragmentManager(), "create Group");
            }
        });
        jointTheGroupButton = view.findViewById(R.id.join_the_group_fragment);
        enterCodeEditText = view.findViewById(R.id.code_group_fragment);

        recyclerViewGroups = view.findViewById(R.id.group_recycler_view_group_fragment);
        recyclerViewGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewGroups.setHasFixedSize(true);

        recyclerViewMyGorups = view.findViewById(R.id.my_group_recycler_view_group_fragment);
        recyclerViewMyGorups.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMyGorups.setHasFixedSize(true);


        groupViewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);


        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        userViewModel.getGroupsLiveData().observe(getViewLifecycleOwner(), groups ->{
            groupsAdapter.submitList(groups);
        });

        userViewModel.getMyGroupsLiveData().observe(getViewLifecycleOwner(), myGroups ->{
            myGroupsAdapter.submitList(myGroups);
        });

        userViewModel.getLiveDataUser().observe(getViewLifecycleOwner(), groupRefrence -> {
            userViewModel.setGroups();
        });


        groupsAdapter = new GroupMenuRecyclerViewAdapter();
        myGroupsAdapter = new GroupMenuRecyclerViewAdapter();
        recyclerViewMyGorups.setAdapter(myGroupsAdapter);
        recyclerViewGroups.setAdapter(groupsAdapter);



        groupsAdapter.setOnItemClickListener(new GroupMenuRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Group group, int position) {

            }
        });

        myGroupsAdapter.setOnItemClickListener(new GroupMenuRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Group group, int position) {

            }
        });


        return view;
    }


    @Override
    public void sendValues(String name, String descripction) {
        User user = userViewModel.getLiveDataUser().getValue();
        Group group = new Group(FirestoreInstance.FIREBASE_FIRESTORE_INSTANCE
                .collection("users").document(user.getUuid())
                ,user.getUuid(),name,descripction);
        groupViewModel.insertGroup(group);
        groupViewModel.getCreateReference().observe(getViewLifecycleOwner(), groupReference ->{
            if(groupReference != null){
                user.addToGroup(groupReference);
                userViewModel.updateUser(user);
            }
        });
    }


}