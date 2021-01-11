package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class InsertTaskDialogFragment extends DialogFragment {

    private final static String GROUP_NAMES = "group names";

    private EditText topicEditText, desripctionEditText;
    private TextView okTextView,cancelTextView;
    private TableLayout tableLayout;
    private CheckBox forAllCheckBox;


    private Map<Integer, CheckBox> checkBoxes;
    private List<Group> groupNames,groupsActive;

    private OnFeedBack onFeedBack;

    public interface OnFeedBack {
        public void sendValues(String topic, String descripction,List<Group> groupNames, boolean forAll);
    }

    public static InsertTaskDialogFragment newInstance(List<Group> groupNames) {
        InsertTaskDialogFragment fragment = new InsertTaskDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(GROUP_NAMES, new ArrayList<Group>(groupNames));
        fragment.setArguments(args);
        return fragment;
    }

    public InsertTaskDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupNames = (ArrayList<Group>) getArguments().getSerializable(GROUP_NAMES);
        }
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

        tableLayout = view.findViewById(R.id.groups_table_insert_task_dialog_fragment);
        forAllCheckBox = view.findViewById(R.id.for_all_insert_task_dialog_fragment);


        checkBoxes = new TreeMap<>();


        for(int i = 0; i <groupNames.size(); i++) {

            CheckBox cb = new CheckBox(getActivity().getApplicationContext());
            cb.setText(groupNames.get(i).getNameOfGroup());
            cb.setTextColor(Color.BLACK);
            checkBoxes.put(i,cb);
            tableLayout.addView(cb);

        }


        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   onFeedBack.sendValues(topicEditText.getText().toString()
                           ,desripctionEditText.getText().toString()
                           ,createActives()
                           ,forAllCheckBox.isChecked());
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

    private List<Group> createActives()
    {
        groupsActive = new ArrayList<>();

        for (Map.Entry<Integer, CheckBox> entry : checkBoxes.entrySet()) {
            if(entry.getValue().isChecked())
            {
                groupsActive.add(groupNames.get(entry.getKey()));
            }
        }

        return groupsActive;
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