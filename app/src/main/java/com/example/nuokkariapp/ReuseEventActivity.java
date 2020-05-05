package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReuseEventActivity extends AppCompatActivity implements EventViewAdapter.OnEventListener{

    ArrayList<Event> events;
    RecyclerView recyclerView;
    TextView title, hint;
    private LinearLayoutManager layoutManager;
    private EventViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuse_event);
        events = EventArchive.getInstance().getPastEventsList();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        title = (TextView) findViewById(R.id.titleOldEvents);
        hint = (TextView) findViewById(R.id.textViewChooseEvent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EventViewAdapter(events, this);
        recyclerView.setAdapter(adapter);
    }

    //sends chosen event's info to add event activity
    @Override
    public void onEventClick(int position) {
        Event chosenEvent = events.get(position);
        chosenEvent.setDate("");
        chosenEvent.setEventFeedbacks(new ArrayList<Feedback>());
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", chosenEvent);
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtras(bundle);
        intent.putExtra("reused", true);
        startActivity(intent);
    }

}
