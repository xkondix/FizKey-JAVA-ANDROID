package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.group.page_group;

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
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Email;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.repository.EmailRepository;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.GroupViewModel;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view_model.UserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendInvitationToGroupFragment extends Fragment {

    private Button sendEmailEditText;
    private EditText emailEditText;

    private EmailRepository emailRepository;
    private GroupViewModel groupViewModel;
    private UserViewModel userViewModel;

    private Group group;
    private User user;


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
        View view = inflater.inflate(R.layout.fragment_send_invitation_to_group, container, false);

        emailEditText = view.findViewById(R.id.email_send_invitation_to_group_fragment);
        sendEmailEditText = view.findViewById(R.id.send_invitation_to_group_fragment);

        emailRepository = EmailRepository.getInstance();
        groupViewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);


        group = groupViewModel.getGroupLiveData().getValue();
        user = userViewModel.getLiveDataUser().getValue();

        sendEmailEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = getContext().getResources().getString(R.string.invitation_to_group)
                        + group.getNameOfGroup() + '"';
                String message = user.getNameAndSurname() + " "
                        + getContext().getResources().getString(R.string.invitation_to_group_message)
                        + group.getUuid();
                String to = emailEditText.getText().toString().trim();

                Email email = new Email(to, topic, message);

                emailRepository.getEmailInterface().createEmail(email).enqueue(new Callback<Email>() {
                    @Override
                    public void onResponse(Call<Email> call, Response<Email> response) {
                        System.out.println(response.headers());
                        System.out.println(response.body());
                        //Toast.makeText(getContext(), "Comment " + r.body().getComment() + " created", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Email> call, Throwable t) {
                        Toast.makeText(getContext(), "Error Creating Email: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}