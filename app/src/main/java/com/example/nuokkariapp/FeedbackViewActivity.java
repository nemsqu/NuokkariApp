package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FeedbackViewActivity extends AppCompatActivity implements FeedbackViewAdapter.OnEventListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FeedbackViewAdapter fbAdapter;
    private Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);
        recyclerView = (RecyclerView) findViewById(R.id.pastEvents);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onEventClick(int position) {
        Feedback fb = event.getEventFeedbacks().get(position);
        Intent intent = new Intent(this, EditFeedbackActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("fb", fb);
        bundle.putSerializable("event", event);
        bundle.putInt("index", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onStart() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        event = (Event) bundle.getSerializable("event");
        fbAdapter = new FeedbackViewAdapter(event.getEventFeedbacks(), this);
        recyclerView.setAdapter(fbAdapter);
        super.onStart();
    }

    public void back(View v){
        Intent intent = new Intent(this, OnGoingEventActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
