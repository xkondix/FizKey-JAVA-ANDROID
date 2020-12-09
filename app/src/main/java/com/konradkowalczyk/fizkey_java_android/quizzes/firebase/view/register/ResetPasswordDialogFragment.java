package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.register;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.konradkowalczyk.fizkey_java_android.R;

public class ResetPasswordDialogFragment extends DialogFragment {


    private TextView okTextView, cancelTextView;
    private EditText emailEditText;
    private OnSendResetEmail onSendResetEmail;

    public interface OnSendResetEmail{
        void respondData(String email);
    }

    public ResetPasswordDialogFragment() {
        // Required empty public constructor
    }


    public static ResetPasswordDialogFragment newInstance() {
        ResetPasswordDialogFragment fragment = new ResetPasswordDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password_dialog, container, false);


        okTextView = view.findViewById(R.id.ok_reset_password_dialog_fragment);
        cancelTextView = view.findViewById(R.id.cancel_reset_password_dialog_fragment);
        emailEditText = view.findViewById(R.id.email_reset_password_dialog_fragment);


        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        if (emailEditText.getText().toString().trim().equalsIgnoreCase("")) {
            emailEditText.setError("Nick nie może być pusty");
        }

        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //zmienne
                if(!emailEditText.getText().toString().trim().equalsIgnoreCase("")){
                    onSendResetEmail.respondData(emailEditText.getText().toString().trim());
                    getDialog().dismiss();}
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onSendResetEmail = (ResetPasswordDialogFragment.OnSendResetEmail) getTargetFragment();
        }catch (ClassCastException e){
        }
    }

}