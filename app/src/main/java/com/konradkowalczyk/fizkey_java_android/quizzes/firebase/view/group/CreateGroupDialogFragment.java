package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.konradkowalczyk.fizkey_java_android.R;


public class CreateGroupDialogFragment extends DialogFragment {

    private TextView okTextView,cancelTextView;
    private TextView nameGroupTextView;
    private TextView descriptionTextView;

    private OnFeedBack onFeedBack;

    public interface OnFeedBack {
        public void sendValues(String name, String descripction);
    }

    public CreateGroupDialogFragment() {
        // Required empty public constructor
    }

    public static CreateGroupDialogFragment newInstance() {
        CreateGroupDialogFragment fragment = new CreateGroupDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create_group_dialog, container, false);

        nameGroupTextView = view.findViewById(R.id.name_create_group_dialog_fragment);
        descriptionTextView = view.findViewById(R.id.descricption_create_group_dialog_fragment);

        okTextView = view.findViewById(R.id.ok_create_group_dialog_fragment);
        cancelTextView = view.findViewById(R.id.cancel_create_group_dialog_fragment);

        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedBack.sendValues(nameGroupTextView.getText().toString(),descriptionTextView.getText().toString());
                getDialog().dismiss();

            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onFeedBack = (OnFeedBack) getTargetFragment();
        }catch (ClassCastException e){
        }
    }
}