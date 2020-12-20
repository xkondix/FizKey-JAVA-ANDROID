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

public class TaskQuizRecyclerViewAdapter extends
        ListAdapter<CustomQuizModel, TaskQuizRecyclerViewAdapter.TaskQuizHolder> {

    private OnItemClickListener listener;

    public TaskQuizRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public void submitList(final List<CustomQuizModel> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    private static final DiffUtil.ItemCallback<CustomQuizModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<CustomQuizModel>() {
        @Override
        public boolean areItemsTheSame(CustomQuizModel oldItem, CustomQuizModel newItem) {
            return true;
        }
        @Override
        public boolean areContentsTheSame(CustomQuizModel oldItem, CustomQuizModel newItem) {
            return false;
        }
    };



    @NonNull
    @Override
    public TaskQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_task_quiz, parent, false);
        return new TaskQuizHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskQuizHolder holder, int position) {
        CustomQuizModel currentTask = getItem(position);
        holder.topicTextView.setText(currentTask.getTopic());
        holder.descriptionTextView.setText(currentTask.getDescripcion());
        holder.timerTextView.setText(String.valueOf(currentTask.getTimerValue()));
        holder.dataTextView.setText(currentTask.getData());
    }

    public CustomQuizModel getTaskRecyclerAt(int position) {
        return getItem(position);
    }

    class TaskQuizHolder extends RecyclerView.ViewHolder {


        private TextView topicTextView;
        private TextView descriptionTextView;
        private TextView timerTextView;
        private TextView dataTextView;

        public TaskQuizHolder(View itemView) {
            super(itemView);

            topicTextView = itemView.findViewById(R.id.topic_task_quiz_card_view);
            descriptionTextView = itemView.findViewById(R.id.description_task_quiz_card_view);
            dataTextView = itemView.findViewById(R.id.data_task_quiz_card_view);
            timerTextView = itemView.findViewById(R.id.timer_task_quiz_card_view);

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
    public interface OnItemClickListener {
        void onItemClick(CustomQuizModel customQuizModel, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
