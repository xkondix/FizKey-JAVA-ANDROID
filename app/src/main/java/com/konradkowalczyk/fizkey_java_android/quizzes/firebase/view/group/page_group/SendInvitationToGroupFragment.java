package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;


public class SendInvitationToGroupFragment extends Fragment {



    public SendInvitationToGroupFragment() {
        // Required empty public constructor
    }


    public static SendInvitationToGroupFragment newInstance() {
        SendInvitationToGroupFragment fragment = new SendInvitationToGroupFragment();
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
        return inflater.inflate(R.layout.fragment_send_invitation_to_group, container, false);
    }
}