package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersRecyclerViewAdapter extends
        ListAdapter<User, GroupMembersRecyclerViewAdapter.GroupMembersHolder> {

    public interface OnItemClickListener {
        void onItemClick(User user, int position);
    }

    private OnItemClickListener listener;


    public GroupMembersRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public void submitList(final List<User> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUuid().equals(newItem.getUuid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getSurname().equals(newItem.getSurname()) &&
                    oldItem.getGroups().equals(newItem.getGroups());
        }

    };



    @NonNull
    @Override
    public GroupMembersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_group_members, parent, false);

        return new GroupMembersHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMembersHolder holder, int position) {
        User user = getItem(position);

        holder.nameOfUserTextView.setText(user.getName() + " " + user.getSurname());
        holder.numberTextView.setText((position + 1) + " ");
    }

    public class GroupMembersHolder extends RecyclerView.ViewHolder {

        private TextView nameOfUserTextView;
        private TextView numberTextView;

        public GroupMembersHolder(@NonNull View itemView) {
            super(itemView);

            numberTextView = itemView.findViewById(R.id.number_card_view_group_members);
            nameOfUserTextView = itemView.findViewById(R.id.name_surname_card_view_group_members);


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



