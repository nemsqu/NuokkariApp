package com.example.nuokkariapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.MyViewHolder> {

    ArrayList<Event> eventsList;
    private OnEventListener globalOnEventListener;

    public EventViewAdapter(ArrayList<Event> events, OnEventListener onEventListener) {
        eventsList = new ArrayList<>();
        eventsList.addAll(events);
        globalOnEventListener = onEventListener;
    }

    //sets click listeners
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
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
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v, globalOnEventListener);
        return vh;
    }

    //get event from list at this position and set text to event name
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = (Event) eventsList.get(position);
        CharSequence s = event.getName();
        holder.textView.setText(s);
    }


    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public interface OnEventListener{
        void onEventClick(int position);

    }

}
