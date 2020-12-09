package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Account;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.register.ResetPasswordDialogFragment;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.AuthViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.UserViewModel;


public class LoginFragment extends Fragment implements View.OnClickListener
        , FirstLoginDialogFragment.OnSendPersonalData
        , ResetPasswordDialogFragment.OnSendResetEmail {

    private EditText emailEditText ,passwordEditText;
    private Button signInButton;

    private UserViewModel userViewModel;
    private AuthViewModel authViewModel;


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
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
            Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        signInButton = view.findViewById(R.id.sign_in_login);
        emailEditText = view.findViewById(R.id.email_login);
        passwordEditText = view.findViewById(R.id.password_login);

        signInButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sign_in_login:
                authViewModel.loginUser(new Account(getEmailFromEditText(),getPasswordFromEditText()));

                userViewModel.getCurrentlyUser();
                userViewModel.liveDataUser.observe(this, user -> {
                    if (user == null) {
                        FirstLoginDialogFragment dialog =  FirstLoginDialogFragment.newInstance();
                        dialog.setTargetFragment(LoginFragment.this, 1);
                        dialog.show(getFragmentManager(), "First Login Dialog");
                    } else {
                    }
                });
                break;

            case R.id.forgot_password_login:

                ResetPasswordDialogFragment dialog =  ResetPasswordDialogFragment.newInstance();
                dialog.setTargetFragment(LoginFragment.this, 1);
                dialog.show(getFragmentManager(), "Reset Password");
                break;
        }


        authViewModel.loginUser(new Account(getEmailFromEditText(),getPasswordFromEditText()));

        userViewModel.getCurrentlyUser();
        userViewModel.liveDataUser.observe(this, user -> {
            if (user == null) {
                FirstLoginDialogFragment dialog =  FirstLoginDialogFragment.newInstance();
                dialog.setTargetFragment(LoginFragment.this, 1);
                dialog.show(getFragmentManager(), "First Login Dialog");
            } else {
                System.out.println("login");
            }
        });

    }

    private String getEmailFromEditText() {
        return emailEditText.getText().toString().trim();
    }

    private String getPasswordFromEditText() {
        return passwordEditText.getText().toString().trim();
    }

    @Override
    public void respondData(String name, String surname) {
        if(name.equals(null) || surname.equals(null)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.value_of_login)
                    , Toast.LENGTH_LONG).show();
        }
        else {
            userViewModel.insertUser(new User(name, surname));
        }
    }

    @Override
    public void respondData(String email) {
        authViewModel.changePassword(email);
    }
}