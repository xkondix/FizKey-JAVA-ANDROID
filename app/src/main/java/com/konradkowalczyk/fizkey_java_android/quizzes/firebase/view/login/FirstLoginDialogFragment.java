package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstLoginDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstLoginDialogFragment extends DialogFragment {


    public interface OnSendPersonalData{
        void respondData(String name, String surname);
    }

    private OnSendPersonalData onSend;
    private TextView okTextView, cancelTextView;
    private EditText nameEditText, surnameEditText;

    public static FirstLoginDialogFragment newInstance() {
        FirstLoginDialogFragment fragment = new FirstLoginDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_login_dialog, container, false);


        okTextView = view.findViewById(R.id.ok_name_first_login_dialog_fragment);
        cancelTextView = view.findViewById(R.id.cancel_name_first_login_dialog_fragment);
        nameEditText = view.findViewById(R.id.name_first_login_dialog_fragment);
        surnameEditText = view.findViewById(R.id.surname_name_first_login_dialog_fragment);


        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        if (nameEditText.getText().toString().trim().equalsIgnoreCase("")) {
            nameEditText.setError("Nick nie może być pusty");
        }

        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //zmienne
                if(!nameEditText.getText().toString().trim().equalsIgnoreCase("")){
                    onSend.respondData(nameEditText.getText().toString().trim(),surnameEditText.getText().toString().trim());
                    getDialog().dismiss();}
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onSend = (FirstLoginDialogFragment.OnSendPersonalData) getTargetFragment();
        }catch (ClassCastException e){
        }
    }
}