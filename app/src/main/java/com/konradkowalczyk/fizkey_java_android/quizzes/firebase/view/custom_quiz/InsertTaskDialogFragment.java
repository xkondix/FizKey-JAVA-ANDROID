package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.konradkowalczyk.fizkey_java_android.R;


public class InsertTaskDialogFragment extends DialogFragment {

    private EditText topicEditText, desripctionEditText;
    private TextView okTextView,cancelTextView;
    private Spinner groupSpinner;
    private OnFeedBack onFeedBack;

    public interface OnFeedBack {
        public void sendValues(String topic, String descripction);
    }

    public static InsertTaskDialogFragment newInstance() {
        InsertTaskDialogFragment fragment = new InsertTaskDialogFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public InsertTaskDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_insert_task_dialog, container, false);

        topicEditText = view.findViewById(R.id.topic_insert_task_dialog_fragment);
        desripctionEditText = view.findViewById(R.id.descricption_insert_task_dialog_fragment);

        cancelTextView = view.findViewById(R.id.cancel_insert_task_dialog_fragment);
        okTextView = view.findViewById(R.id.ok_insert_task_dialog_fragment);


        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   onFeedBack.sendValues(topicEditText.getText().toString(),desripctionEditText.getText().toString());
                   getDialog().dismiss();

            }
        });

        return view;
    }

    private String checkFields()
    {
        String information = "";

        if(topicEditText.getText().toString().trim().equals(""))
        {
            topicEditText.setError(getContext().getString(R.string.empty_field));
            information+="topic";
        }
        if(topicEditText.getText().toString().trim().equals("")){
            desripctionEditText.setError(getContext().getString(R.string.empty_field));
            information+="descricton";
        }

        return information;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onFeedBack = (InsertTaskDialogFragment.OnFeedBack) getActivity();
        }catch (ClassCastException e){
        }
    }
}