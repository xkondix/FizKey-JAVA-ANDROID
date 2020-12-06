package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.AuthViewModel;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText emailEditText ,passwordEditText;
    private Button signUpButton;
    private AuthViewModel viewModelAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelAuth = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        signUpButton = view.findViewById(R.id.sign_up_register);
        emailEditText = view.findViewById(R.id.email_register);
        passwordEditText = view.findViewById(R.id.password_register);

        return view;
    }

    @Override
    public void onClick(View v) {

        viewModelAuth.registerUser(new Account(getEmailFromEditText(),getPasswordFromEditText()));

    }

    private String getEmailFromEditText()
    {
        return emailEditText.getText().toString().trim();
    }

    private String getPasswordFromEditText()
    {
        return passwordEditText.getText().toString().trim();
    }
}