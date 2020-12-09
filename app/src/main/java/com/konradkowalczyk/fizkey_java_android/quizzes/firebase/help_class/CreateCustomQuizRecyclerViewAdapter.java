package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.List;


public class CreateCustomQuizRecyclerViewAdapter extends RecyclerView.Adapter<CreateCustomQuizRecyclerViewAdapter.QuizRecyclerViewHolder> {

    private List<TaskRecycler> taskRecyclers = new ArrayList<>();
    private AdapterView.OnItemClickListener listener;

    @NonNull
    @Override
    public CreateCustomQuizRecyclerViewAdapter.QuizRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemQuizResultView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_quiz_result_item, parent, false);
        return new CreateCustomQuizRecyclerViewAdapter.QuizRecyclerViewHolder(itemQuizResultView);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCustomQuizRecyclerViewAdapter.QuizRecyclerViewHolder holder, int position) {

        holder.goodAnswerTextView.setText("yo");
//        holder.answersTextView.setText(quizResults.get(position).getYourAnswer());
//        holder.questionTextView.setText(quizResults.get(position).getQuestion());
//        holder.numberOfQuestionTextView.setText(quizResults.get(position).getNumberQuestion());

    }

    @Override
    public int getItemCount() {
        return taskRecyclers.size();
    }

    public void setQuizResults(List<TaskRecycler> taskRecyclers) {
        this.taskRecyclers = taskRecyclers;
        notifyDataSetChanged();
    }

    public void changeList(List<TaskRecycler> taskRecyclers) {
    }

    protected class QuizRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView numberOfQuestionTextView;
        private TextView questionTextView;
        private TextView goodAnswerTextView;
        private TextView answersTextView;

        public QuizRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            answersTextView = itemView.findViewById(R.id.answers_card_view_create_custom_quiz);
            goodAnswerTextView = itemView.findViewById(R.id.good_answer_card_view_create_custom_quiz);
            questionTextView = itemView.findViewById(R.id.question_card_view_create_custom_quiz);
            numberOfQuestionTextView = itemView.findViewById(R.id.number_of_question_card_view_create_custom_quiz);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(getItem(position));
//                    }
//                }
//            });

        }

    }
}
