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

import java.util.ArrayList;
import java.util.List;

public class DateGradesRecyclerViewAdapter extends
        ListAdapter<String, DateGradesRecyclerViewAdapter.DateGradesHolder> {




    public interface OnItemClickListener {
        void onItemClick(String date, int position);
    }

    private OnItemClickListener listener;


    public DateGradesRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public void submitList(final List<String> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {

        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public DateGradesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_date_group, parent, false);

        return new DateGradesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DateGradesHolder holder, int position) {
        String date = getItem(position);
        holder.dateTextView.setText(date);
    }

    public class DateGradesHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView;

        public DateGradesHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.date_group_card_view);

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

    public void setOnItemClickListener(DateGradesRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
