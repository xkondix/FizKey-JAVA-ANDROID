package com.konradkowalczyk.fizkey_java_android.quizzes.menu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class SelectPhenomenonDialogFragment extends DialogFragment {



    private TextView okTextView,cancelTextView;
    private TableLayout tableLayout;
    private List<String> active, phenomenonsActive;
    private String[] phenomenons,forces;
    private Map<Integer,CheckBox> checkBoxes;

    public OnFeedBack onFeedBack;



    public interface OnFeedBack {
        public void sendStatusMessage(String message);
        public void sendActivePhenomena(List<String> activePhenomena);
    }


    private static final String ACTIVE_PHENOMENA = "active";


    public SelectPhenomenonDialogFragment() {
        // Required empty public constructor
    }



    public static SelectPhenomenonDialogFragment newInstance(List<String> active) {
        SelectPhenomenonDialogFragment fragment = new SelectPhenomenonDialogFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ACTIVE_PHENOMENA, new ArrayList<String>(active));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            active = getArguments().getStringArrayList(ACTIVE_PHENOMENA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_phenomenon_dialog, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        cancelTextView = view.findViewById(R.id.cancel_quiz_dialog);
        okTextView = view.findViewById(R.id.ok_quiz_dialog);
        tableLayout = view.findViewById(R.id.quiz_table_layout);

        phenomenons = getContext().getResources().getStringArray(R.array.phenomenons_for_quizzes);
        forces = getContext().getResources().getStringArray(R.array.force_for_quizzes);
        checkBoxes = new TreeMap<>();


        int counter = 0;
        for(int i = 0; i <phenomenons.length; i++) {
            String text = phenomenons[i].split(" ")[0];
            int len = Integer.parseInt(phenomenons[i].split(" ")[1]);
            TextView t = new TextView(getActivity().getApplicationContext());
            t.setText(text);
            t.setTextColor(Color.BLACK);
            tableLayout.addView(t);
            for(int j = 0; j<len;j++) {

                CheckBox cb = new CheckBox(getActivity().getApplicationContext());
                cb.setText(forces[counter]);
                cb.setTextColor(Color.BLACK);
                checkBoxes.put(counter,cb);
                tableLayout.addView(cb);
                counter++;
            }
        }

        checkActiveCheckBox();

        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedBack.sendStatusMessage(getContext().getResources().getString(R.string.not_save));
                getDialog().dismiss();
            }
        });
        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedBack.sendStatusMessage(getContext().getResources().getString(R.string.save));
                onFeedBack.sendActivePhenomena(createActives());
                getDialog().dismiss();
            }
        });

        return view;
    }

    private List<String> createActives()
    {
        phenomenonsActive = new ArrayList<>();

        for (Map.Entry<Integer, CheckBox> entry : checkBoxes.entrySet()) {
            if(entry.getValue().isChecked())
            {
                phenomenonsActive.add(forces[entry.getKey()]);
            }
        }

        return phenomenonsActive;
    }

    private void checkActiveCheckBox()
    {
        for(CheckBox checkBox : checkBoxes.values())
        {
            for(String force : active) {
                if(checkBox.getText().toString().equals(force))
                {
                   checkBox.setChecked(true);
                   break;
                }
            }
        }

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