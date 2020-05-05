package com.example.nuokkariapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedbackViewAdapter extends RecyclerView.Adapter<com.example.nuokkariapp.FeedbackViewAdapter.MyViewHolder> {

        ArrayList<Feedback> fbList = new ArrayList<>();
        private OnEventListener globalOnEventListener;

        public FeedbackViewAdapter(ArrayList<Feedback> feedbacks, com.example.nuokkariapp.FeedbackViewAdapter.OnEventListener onEventListener) {
            fbList.addAll(feedbacks);
            globalOnEventListener = onEventListener;
        }

        public ArrayList<Feedback> getFeedbackList() {
            return fbList;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            public TextView textView;
            OnEventListener onEventListener;
            public MyViewHolder(TextView v, OnEventListener onEventListener) {
                super(v);
                textView = v;
                this.onEventListener = onEventListener;

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                onEventListener.onEventClick(getAdapterPosition());
            }
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
            MyViewHolder vh = new MyViewHolder(v, globalOnEventListener);
            return vh;
        }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Feedback fb = (Feedback) fbList.get(position);
        CharSequence s = fb.getGivenFeedback();
        holder.textView.setText(s);
    }


        @Override
        public int getItemCount() {
            return fbList.size();
        }

        public interface OnEventListener{
            void onEventClick(int position);

        }

}
