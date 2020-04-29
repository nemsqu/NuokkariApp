package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EventInfoActivity extends AppCompatActivity {

    private TextView mainTitle, nameTitle, locationTitle, dateTitle, startTitle, endTitle, ageTitle, descriptionTitle;
    private EditText location, name, date, start, end, age, description;
    private Button buttonSave, buttonStartEvent;
    private Event chosenEvent;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        mainTitle = (TextView) findViewById(R.id.titleEvent);
        locationTitle = (TextView) findViewById(R.id.titleLocation);
        nameTitle = (TextView) findViewById(R.id.titleName);
        dateTitle = (TextView) findViewById(R.id.titleDate);
        startTitle = (TextView) findViewById(R.id.titleStartTime);
        endTitle = (TextView) findViewById(R.id.titleEndTime);
        ageTitle = (TextView) findViewById(R.id.titleAge);
        descriptionTitle = (TextView) findViewById(R.id.titleDescription);
        name = (EditText) findViewById(R.id.eventName);
        location = (EditText) findViewById(R.id.eventLocation);
        date = (EditText) findViewById(R.id.eventDate);
        start = (EditText) findViewById(R.id.eventStartTime);
        end = (EditText) findViewById(R.id.eventEndTime);
        age = (EditText) findViewById(R.id.eventAge);
        description = (EditText) findViewById(R.id.eventDescription);
        buttonSave = (Button) findViewById(R.id.buttonSaveEvent);
        buttonStartEvent = (Button) findViewById(R.id.buttonStartEvent);
    }

    public void onStart() {
        super.onStart();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        index = intent.getIntExtra("position", 0);
        chosenEvent = (Event) bundle.getSerializable("chosenEvent");
        name.setText(chosenEvent.getName());
        location.setText(chosenEvent.getLocation());
        date.setText(chosenEvent.getDate());
        start.setText(chosenEvent.getStart());
        end.setText(chosenEvent.getEnd());
        age.setText(chosenEvent.getAge());
        description.setText(chosenEvent.getDescription());
        EventCollection.getInstance().modifyEventOnList(chosenEvent, index);
    }

    public void saveEvent(View v){
        chosenEvent.setName(name.getText().toString());
        chosenEvent.setDate(date.getText().toString());
        chosenEvent.setLocation(location.getText().toString());
        chosenEvent.setStart(start.getText().toString());
        chosenEvent.setEnd(end.getText().toString());
        chosenEvent.setAge(age.getText().toString());
        chosenEvent.setDescription(description.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startEvent(View v){
        chosenEvent.setName(name.getText().toString());
        chosenEvent.setDate(date.getText().toString());
        chosenEvent.setLocation(location.getText().toString());
        chosenEvent.setStart(start.getText().toString());
        chosenEvent.setEnd(end.getText().toString());
        chosenEvent.setAge(age.getText().toString());
        chosenEvent.setDescription(description.getText().toString());
        chosenEvent.setOnGoing(true);
        Intent intent = new Intent(this, OnGoingEventActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", chosenEvent);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
