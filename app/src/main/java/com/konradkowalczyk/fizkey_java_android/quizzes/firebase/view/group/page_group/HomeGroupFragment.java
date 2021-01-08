package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.GroupMembersRecyclerViewAdapter;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;


public class HomeGroupFragment extends Fragment {

    private RecyclerView recyclerViewGroupMembers;
    private TextView nameOfGroupTextView, descricptionTextView;

    private GroupMembersRecyclerViewAdapter groupMembersAdapter;

    private GroupViewModel groupViewModel;

    public HomeGroupFragment() {
        // Required empty public constructor
    }


    public static HomeGroupFragment newInstance() {
        HomeGroupFragment fragment = new HomeGroupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_group, container, false);

        nameOfGroupTextView = view.findViewById(R.id.name_of_group_home_fragment);
        descricptionTextView = view.findViewById(R.id.descricption_home_fragment);

        recyclerViewGroupMembers = view.findViewById(R.id.group_members_recycler_view_home_fragment);
        recyclerViewGroupMembers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewGroupMembers.setHasFixedSize(true);

        groupViewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);

        groupViewModel.getGroupLiveData().observe(getViewLifecycleOwner(), group ->{
            System.out.println(group);
            groupViewModel.setUsers();
            nameOfGroupTextView.setText(groupViewModel.getGroupLiveData().getValue().getNameOfGroup());
            descricptionTextView.setText(groupViewModel.getGroupLiveData().getValue().getDescription());
        });

        groupViewModel.getUsersMutableLiveData().observe(getViewLifecycleOwner(), users ->{
            groupMembersAdapter.submitList(users);
        });

        groupMembersAdapter = new GroupMembersRecyclerViewAdapter();
        recyclerViewGroupMembers.setAdapter(groupMembersAdapter);





        return view;
    }
}