package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class AddEventActivity extends AppCompatActivity implements Serializable {

    TextView mainTitle, nameTitle, locationTitle, dateTitle, startTitle, endTitle, ageTitle, descriptionTitle;
    EditText location, name, date, start, end, age, description;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
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
    }

    public void addEvent(View v){
        Event newEvent = new Event(name.getText().toString(), location.getText().toString(), date.getText().toString(), start.getText().toString(),
                end.getText().toString(), age.getText().toString(), description.getText().toString());
        EventCollection.getInstance().addEventToList(newEvent);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
