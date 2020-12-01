package com.konradkowalczyk.fizkey_java_android.quizzes.quizy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.fizkey_java_android.R;

import java.util.ArrayList;
import java.util.List;

public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.QuizRecyclerViewHolder> {

    private List<QuizResult> quizResults = new ArrayList<>();

    @NonNull
    @Override
    public QuizRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemQuizResultView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_quiz_result_item, parent, false);
        return new QuizRecyclerViewHolder(itemQuizResultView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizRecyclerViewHolder holder, int position) {

        holder.goodAnswerTextView.setText(quizResults.get(position).getGoodAnswer());
        holder.yourAnswerTextView.setText(quizResults.get(position).getYourAnswer());
        holder.questionTextView.setText(quizResults.get(position).getQuestion());
        holder.numberQuestionTextView.setText(quizResults.get(position).getNumberQuestion());

    }

    @Override
    public int getItemCount() {
        return quizResults.size();
    }

    public void setQuizResults(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
        notifyDataSetChanged();
    }

    protected class QuizRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView numberQuestionTextView;
        private TextView questionTextView;
        private TextView goodAnswerTextView;
        private TextView yourAnswerTextView;

        public QuizRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            yourAnswerTextView = itemView.findViewById(R.id.card_view_your_anwser);
            goodAnswerTextView = itemView.findViewById(R.id.card_view_good_answer);
            questionTextView = itemView.findViewById(R.id.card_view_question);
            numberQuestionTextView = itemView.findViewById(R.id.card_view_number_question);

        }
    }
}
