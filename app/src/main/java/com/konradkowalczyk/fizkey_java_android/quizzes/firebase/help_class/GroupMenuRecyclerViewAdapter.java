package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class GroupMenuRecyclerViewAdapter extends
        ListAdapter<Group, GroupMenuRecyclerViewAdapter.GroupMenuHolder> {

    public interface OnItemClickListener {
        void onItemClick(Group group, int position);
    }

    private OnItemClickListener listener;
    private String numberOfGroupsMembers;


    public GroupMenuRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public void submitList(final List<Group> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    private static final DiffUtil.ItemCallback<Group> DIFF_CALLBACK = new DiffUtil.ItemCallback<Group>() {
        @Override
        public boolean areItemsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.getUuid().equals(newItem.getUuid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.getNameOfGroup().equals(newItem.getNameOfGroup()) &&
                    oldItem.getAuthor().equals(newItem.getAuthor()) &&
                    oldItem.getStudents().equals(newItem.getStudents());
        }

    };



    @NonNull
    @Override
    public GroupMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_group_menu, parent, false);

        numberOfGroupsMembers = parent.getContext().getResources().getString(R.string.number_of_group_members);
        return new GroupMenuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMenuHolder holder, int position) {
        Group group = getItem(position);
        group.getAuthor().get().addOnCompleteListener( userDocument -> {
            if (userDocument.isSuccessful()) {
                DocumentSnapshot document = userDocument.getResult();
                User user = document.toObject(User.class);
                String name = user.getName() + " " + user.getSurname();
                holder.nameOfTeacherTextView.setText(name);
            }
            else {
                holder.nameOfTeacherTextView.setText("null");

            }

        });

        holder.descriptionTextView.setText(group.getDescription());
        holder.groupNameTextView.setText(group.getNameOfGroup());
        holder.numberOfPupilsTextView.setText(numberOfGroupsMembers+": "+group.getStudents().size());
    }

    public class GroupMenuHolder extends RecyclerView.ViewHolder {

        private TextView nameOfTeacherTextView;
        private TextView groupNameTextView;
        private TextView descriptionTextView;
        private TextView numberOfPupilsTextView;

        public GroupMenuHolder(@NonNull View itemView) {
            super(itemView);

            nameOfTeacherTextView = itemView.findViewById(R.id.author_group_menu_card_view);
            numberOfPupilsTextView = itemView.findViewById(R.id.number_of_peoples_group_menu_card_view);
            groupNameTextView = itemView.findViewById(R.id.name_group_menu_card_view);
            descriptionTextView = itemView.findViewById(R.id.description_group_menu_card_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position), position);
                    }
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


