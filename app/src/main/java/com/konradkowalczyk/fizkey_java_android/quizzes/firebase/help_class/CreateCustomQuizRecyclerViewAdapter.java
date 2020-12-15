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


public class CreateCustomQuizRecyclerViewAdapter extends
        ListAdapter<TaskRecycler, CreateCustomQuizRecyclerViewAdapter.CreateCustomQuizHolder> {

    private OnItemClickListener listener;

    public CreateCustomQuizRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public void submitList(final List<TaskRecycler> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    private static final DiffUtil.ItemCallback<TaskRecycler> DIFF_CALLBACK = new DiffUtil.ItemCallback<TaskRecycler>() {
        @Override
        public boolean areItemsTheSame(TaskRecycler oldItem, TaskRecycler newItem) {
            return false;
        }
        @Override
        public boolean areContentsTheSame(TaskRecycler oldItem, TaskRecycler newItem) {
            return oldItem.getAnswers().equals(newItem.getAnswers()) &&
                    oldItem.getGoodAnswer().equals(newItem.getGoodAnswer()) &&
                    oldItem.getQuestion().equals(newItem.getQuestion());
        }
    };



    @NonNull
    @Override
    public CreateCustomQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_create_custom_quiz, parent, false);
        return new CreateCustomQuizHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull CreateCustomQuizHolder holder, int position) {
        TaskRecycler currentTask = getItem(position);
        holder.goodAnswerTextView.setText(currentTask.getGoodAnswer());
        holder.answersTextView.setText(currentTask.getAnswers());
        holder.questionTextView.setText(currentTask.getQuestion());
        holder.numberOfQuestionTextView.setText("Position "+ position);
    }
    public TaskRecycler getTaskRecyclerAt(int position) {
        return getItem(position);
    }
    class CreateCustomQuizHolder extends RecyclerView.ViewHolder {


        private TextView numberOfQuestionTextView;
        private TextView questionTextView;
        private TextView goodAnswerTextView;
        private TextView answersTextView;

        public CreateCustomQuizHolder(View itemView) {
            super(itemView);

            answersTextView = itemView.findViewById(R.id.answers_card_view_create_custom_quiz);
            goodAnswerTextView = itemView.findViewById(R.id.good_answer_card_view_create_custom_quiz);
            questionTextView = itemView.findViewById(R.id.question_card_view_create_custom_quiz);
            numberOfQuestionTextView = itemView.findViewById(R.id.number_of_question_card_view_create_custom_quiz);

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
        void onItemClick(TaskRecycler task, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
