package com.example.nuokkariapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    ArrayList<Event> eventsList = new ArrayList<>();
    private OnEventListener globalOnEventListener;

    public myAdapter(List<Event> events, OnEventListener onEventListener) {
        if(events.size() == 1) {
            eventsList.addAll(events);
        }else{
            eventsList.addAll(events);
            eventsList.remove(0);
        }
        globalOnEventListener = onEventListener;
    }

    public ArrayList<Event> getEventsList() {
        return eventsList;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
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
