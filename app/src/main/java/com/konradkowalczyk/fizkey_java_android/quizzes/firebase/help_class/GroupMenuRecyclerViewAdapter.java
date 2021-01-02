package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity.Group;

public class GroupMenuRecyclerViewAdapter extends
        ListAdapter<Group, GroupMenuRecyclerViewAdapter.GroupMenuHolder> {
    {
    }

    protected GroupMenuRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<Group> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public GroupMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMenuHolder holder, int position) {

    }

    public class GroupMenuHolder extends RecyclerView.ViewHolder {
        public GroupMenuHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


