package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventInfoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView mainTitle, nameTitle, locationTitle, dateTitle, startTitle, endTitle, ageTitle, limitTitle, descriptionTitle, recurringUntil;
    private EditText location, name, date, start, end, age, description, visitorLimit;
    private Button buttonSave, buttonStartEvent, buttonChooseDate, buttonFrontPage;
    private ImageView imageView;
    private Event chosenEvent;
    private int index, progressValue;
    public static final int PICK_IMAGE = 1, GALLERY_REQUEST_CODE = 123;
    Uri imageUri;
    private SeekBar seekBar;
    private Date chosenEndDate;
    private boolean hasRecurrence = false;

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
        limitTitle = (TextView) findViewById(R.id.titleVisitorLimit);
        descriptionTitle = (TextView) findViewById(R.id.titleDescription);
        name = (EditText) findViewById(R.id.eventName);
        location = (EditText) findViewById(R.id.eventLocation);
        date = (EditText) findViewById(R.id.eventDate);
        start = (EditText) findViewById(R.id.eventStartTime);
        end = (EditText) findViewById(R.id.eventEndTime);
        age = (EditText) findViewById(R.id.eventAge);
        visitorLimit = (EditText) findViewById(R.id.eventVisitorLimit);
        description = (EditText) findViewById(R.id.eventDescription);
        buttonSave = (Button) findViewById(R.id.buttonSaveEvent);
        buttonStartEvent = (Button) findViewById(R.id.buttonStartEvent);
        buttonFrontPage = (Button) findViewById(R.id.buttonBackToFrontPage);
        imageView = (ImageView) findViewById(R.id.eventPhoto);
        imageView.setTag("");
        recurringUntil = (TextView) findViewById(R.id.textViewEndDate);
        buttonChooseDate = (Button) findViewById(R.id.buttonChooseDate);
        buttonChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        seekBar();
    }

    //fills info from the chosen event, sets recurrence details if event is recurring
    //when returning from choosing picture, fills fields with info that had been inserted and is saved
    //in temporary sotrage
    public void onStart() {
        super.onStart();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        index = intent.getIntExtra("position", 0);
        chosenEvent = (Event) bundle.getSerializable("chosenEvent");
        if(EventCollection.getInstance().getTemporaryStore() != null){
            chosenEvent = EventCollection.getInstance().getTemporaryStore();
        }
        for(RecurringEvent e : EventCollection.getInstance().getRecurringEventArrayList()){
            if(chosenEvent.getID() == e.getID()){
                if(e.isRecurringDaily()){
                    seekBar.setProgress(1);
                }else if(e.isRecurringWeekly()){
                    seekBar.setProgress(2);
                }else if(e.isRecurringMonthly()){
                    seekBar.setProgress(3);
                }
                recurringUntil.setText(new SimpleDateFormat("dd.MM.yyyy").format(e.getRecurringUntil()));
            }
        }
        name.setText(chosenEvent.getName());
        location.setText(chosenEvent.getLocation());
        date.setText(chosenEvent.getDate());
        start.setText(chosenEvent.getStart());
        end.setText(chosenEvent.getEnd());
        age.setText(chosenEvent.getAge());
        visitorLimit.setText(Integer.toString(chosenEvent.getVisitorLimit()));
        description.setText(chosenEvent.getDescription());
        if(!chosenEvent.getImageURI().equals("")) {
            imageView.setImageURI(Uri.parse(chosenEvent.getImageURI()));
        }
        EventCollection.getInstance().setTemporaryStore(null);
    }

    public void seekBar(){
        seekBar = (SeekBar) findViewById(R.id.seekBarRecurrence);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar){
                    }
                }
        );
    }

    public void saveEvent(View v){
        if(checkFields()) {
            savingEvent();
        }
    }

    private boolean checkFields() {
        if(name.getText().toString().equals("") || location.getText().toString().equals("") || date.getText().toString().equals("")
                || start.getText().toString().equals("") || end.getText().toString().equals("") || age.getText().toString().equals("") ||
                description.getText().toString().equals("")){
            Toast.makeText(this, "Täytä kaikki tähdellä merkityt kentät.", Toast.LENGTH_LONG).show();
            return false;
        }
        try {
            Date check = new SimpleDateFormat("dd.MM.yyyy").parse(date.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(this, "Anna päivämäärä muodossa 'pp.kk.vvvv'.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
        if(progressValue != 0){
            try {
                String dateString = recurringUntil.getText().toString();
                String[] splitString = dateString.split(" ");
                Date check = new SimpleDateFormat("dd.MM.yyyy").parse(splitString[0]);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Valitse, mihin asti tapahtumaa toistetaan.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    //saves possible modifications to event and recurring event
    //creates recurring event i event was not recurring before
    public void savingEvent(){
        chosenEvent.setName(name.getText().toString());
        chosenEvent.setDate(date.getText().toString());
        chosenEvent.setLocation(location.getText().toString());
        chosenEvent.setStart(start.getText().toString());
        chosenEvent.setEnd(end.getText().toString());
        chosenEvent.setAge(age.getText().toString());
        chosenEvent.setVisitorLimit(Integer.parseInt(visitorLimit.getText().toString()));
        chosenEvent.setDescription(description.getText().toString());
        EventCollection.getInstance().modifyEventOnList(chosenEvent, chosenEvent.getIndex());
        for(RecurringEvent chosenRecurrence : EventCollection.getInstance().getRecurringEventArrayList()){
            if(chosenRecurrence.getID() == chosenEvent.getID()){
                hasRecurrence = true;
                String dateString = recurringUntil.getText().toString();
                String[] splitString = dateString.split(" ");
                try {
                    chosenEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(splitString[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                chosenRecurrence.setName(name.getText().toString());
                chosenRecurrence.setDate(date.getText().toString());
                chosenRecurrence.setLocation(location.getText().toString());
                chosenRecurrence.setStart(start.getText().toString());
                chosenRecurrence.setEnd(end.getText().toString());
                chosenRecurrence.setAge(age.getText().toString());
                chosenRecurrence.setVisitorLimit(Integer.parseInt(visitorLimit.getText().toString()));
                chosenRecurrence.setDescription(description.getText().toString());
                if(progressValue == 0){
                    EventCollection.getInstance().getRecurringEventArrayList().remove(chosenRecurrence.getIndex());
                }else if(progressValue == 1){
                    chosenRecurrence.setRecurringDaily(true);
                    chosenRecurrence.setRecurringUntil(chosenEndDate);
                    EventCollection.getInstance().modifyEventOnList(chosenRecurrence, chosenRecurrence.getIndex());
                }else if(progressValue == 2){
                    chosenRecurrence.setRecurringWeekly(true);
                    chosenRecurrence.setRecurringUntil(chosenEndDate);
                    EventCollection.getInstance().modifyEventOnList(chosenRecurrence, chosenRecurrence.getIndex());
                }else if(progressValue == 3){
                    chosenRecurrence.setRecurringMonthly(true);
                    chosenRecurrence.setRecurringUntil(chosenEndDate);
                    EventCollection.getInstance().modifyEventOnList(chosenRecurrence, chosenRecurrence.getIndex());
                }
            }
        }
        if(!hasRecurrence && progressValue != 0){
            RecurringEvent recurringEvent = new RecurringEvent(name.getText().toString(), location.getText().toString(), date.getText().toString(), start.getText().toString(),
                    end.getText().toString(), age.getText().toString(), 200, description.getText().toString(), imageView.getTag().toString(), chosenEvent.getID(), progressValue, chosenEndDate);
            recurringEvent.setIndex(EventCollection.getInstance().getRecurringEventArrayList().size());
            EventCollection.getInstance().addEventToList(recurringEvent);
        }
        EventCollection.getInstance().setTemporaryStore(null);
    }

    //starts timer and sets event as ongoing
    public void startEvent(View v){
        if(Timer.getInstance().getStartTime() == null) {
            if(checkFields()) {
                chosenEvent.setOnGoing(true);
                savingEvent();
                Date start = new Date();
                Timer.getInstance().setStartTime(start);
                Intent intent = new Intent(this, OnGoingEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("event", chosenEvent);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "Toinen tapahtuma on jo käynnissä.", Toast.LENGTH_SHORT).show();
        }
        EventCollection.getInstance().setTemporaryStore(null);
    }

    public void addPicture(View v){
        Event temporaryStore = new Event(name.getText().toString(), location.getText().toString(), date.getText().toString(), start.getText().toString(),
                end.getText().toString(), age.getText().toString(), Integer.parseInt(visitorLimit.getText().toString()), description.getText().toString(), imageView.getTag().toString(), chosenEvent.getID());
        EventCollection.getInstance().setTemporaryStore(temporaryStore);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    //loads picture after being chosen
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                bitmap.setHasAlpha(false);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setTag(imageUri);
        }
    }

    //sets chosen end date for event
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        String dateString = new SimpleDateFormat("dd.MM.yyyy").format(new Date(calendar.getTimeInMillis()));
        recurringUntil.setText(dateString + " asti");
        try {
            chosenEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void toFrontPage(View v){
        EventCollection.getInstance().setTemporaryStore(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
