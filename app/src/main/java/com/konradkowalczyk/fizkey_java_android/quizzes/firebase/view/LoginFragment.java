package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.konradkowalczyk.fizkey_java_android.R;


public class LoginFragment extends Fragment implements View.OnClickListener, FirstLoginDialogFragment.OnSendPersonalData {

    private EditText emailEditText ,passwordEditText;
    private Button signInButton;


    public LoginFragment(){
            // Required empty public constructor
            }

    public static LoginFragment newInstance(){
            LoginFragment fragment=new LoginFragment();
            return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            getActivity().setTitle(getContext().getResources().getString(R.string.sign_in_login));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
            Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        signInButton = view.findViewById(R.id.sign_in_login);
        emailEditText = view.findViewById(R.id.email_login);
        passwordEditText = view.findViewById(R.id.password_login);

        return view;
    }


    @Override
    public void onClick(View v) {

    }
}