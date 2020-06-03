package com.konradkowalczyk.fizkey_java_android;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Listener listener;
    private String[] titels;


    interface Listener
    {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        private CardView cardView;

        public ViewHolder(CardView view) {
            super(view);
            this.cardView=view;
        }
    }

    public RecyclerViewAdapter(String[] titels)
    {
        this.titels=titels;
    }

    public void setListener(Listener listener)
    {
        this.listener=listener;
    }

    @androidx.annotation.NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        android.widget.TextView textView = (android.widget.TextView) cardView.findViewById(R.id.textMenu);
        textView.setText(titels[position]);
        cardView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if(listener != null)
                {
                    listener.onClick(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return titels.length;
    }



}
