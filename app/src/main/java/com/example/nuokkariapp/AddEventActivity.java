package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView mainTitle, nameTitle, locationTitle, dateTitle, startTitle, endTitle, ageTitle, descriptionTitle, endDate;
    private EditText location, name, date, start, end, age, visitorLimit, description;
    private Button buttonSave, buttonAddPhoto, buttonChooseDate;
    private ImageView imageView;
    public static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private int ID = 0, progressValue;
    private boolean reused;
    private Event chosenEvent;
    private SeekBar seekBar;
    private Date chosenEndDate;

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
        visitorLimit = (EditText) findViewById(R.id.visitorLimit);
        description = (EditText) findViewById(R.id.eventDescription);
        imageView = (ImageView) findViewById(R.id.photo);
        imageView.setTag("");
        buttonAddPhoto = (Button) findViewById(R.id.buttonAddPhoto);
        buttonSave = (Button) findViewById(R.id.buttonSaveEvent);
        buttonChooseDate = (Button) findViewById(R.id.buttonChooseDate);
        endDate = (TextView) findViewById(R.id.textViewRecurringUntil);
        buttonChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        seekBar();
    }

    //creates a new event and also a recurring event if the event is set to be recurring
    //if event was reused, deletes the reused event from archive
    public void addEvent(View v){
        if(checkFields()) {
            Event newEvent;
            RecurringEvent recurringEvent = null;
            ID = IDCreator.getInstance().getID();
            newEvent = new Event(name.getText().toString(), location.getText().toString(), date.getText().toString(), start.getText().toString(),
                    end.getText().toString(), age.getText().toString(), Integer.parseInt(visitorLimit.getText().toString()), description.getText().toString(), imageView.getTag().toString(), ID);
            if (progressValue != 0) {
                recurringEvent = new RecurringEvent(name.getText().toString(), location.getText().toString(), date.getText().toString(), start.getText().toString(),
                        end.getText().toString(), age.getText().toString(), Integer.parseInt(visitorLimit.getText().toString()), description.getText().toString(), imageView.getTag().toString(), ID, progressValue, chosenEndDate);
            }
            EventCollection.getInstance().addEventToList(newEvent);
            if (recurringEvent != null) {
                recurringEvent.setIndex(EventCollection.getInstance().getRecurringEventArrayList().size());
                EventCollection.getInstance().addEventToList(recurringEvent);
            }
            if (reused) {
                EventArchive.getInstance().removeEvent(chosenEvent);
            }
            EventCollection.getInstance().setTemporaryStore(null);
            Toast.makeText(this, Integer.toString(newEvent.getID()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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
            System.out.println(check);
        } catch (ParseException e) {
            Toast.makeText(this, "Anna päivämäärä muodossa 'pp.kk.vvvv'.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
        if(progressValue != 0){
            try {
                String dateString = endDate.getText().toString();
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

    public void addPicture(View v){
        Event temporaryStore = new Event(name.getText().toString(), location.getText().toString(), date.getText().toString(), start.getText().toString(),
                end.getText().toString(), age.getText().toString(), Integer.parseInt(visitorLimit.getText().toString()), description.getText().toString(), imageView.getTag().toString(), -1);
        EventCollection.getInstance().setTemporaryStore(temporaryStore);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    //loads the chosen picture
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
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
    public void onStart() {
        super.onStart();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        chosenEvent = (Event) bundle.getSerializable("event");
        reused = intent.getBooleanExtra("reused", false);
        name.setText(chosenEvent.getName());
        location.setText(chosenEvent.getLocation());
        date.setText(chosenEvent.getDate());
        start.setText(chosenEvent.getStart());
        end.setText(chosenEvent.getEnd());
        age.setText(chosenEvent.getAge());
        visitorLimit.setText(Integer.toString(chosenEvent.getVisitorLimit()));
        description.setText(chosenEvent.getDescription());
        if(EventCollection.getInstance().getTemporaryStore() != null){
            Event temporaryStore = EventCollection.getInstance().getTemporaryStore();
            name.setText(temporaryStore.getName());
            location.setText(temporaryStore.getLocation());
            date.setText(temporaryStore.getDate());
            start.setText(temporaryStore.getStart());
            end.setText(temporaryStore.getEnd());
            age.setText(temporaryStore.getAge());
            visitorLimit.setText(Integer.toString(temporaryStore.getVisitorLimit()));
            description.setText(temporaryStore.getDescription());
            EventCollection.getInstance().setTemporaryStore(null);
        }
    }

    //handles SeekBar
    public void seekBar(){
        seekBar = (SeekBar) findViewById(R.id.seekBar);
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

    //sets end date user chose
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        String dateString = new SimpleDateFormat("dd.MM.yyyy").format(new Date(calendar.getTimeInMillis()));
        endDate.setText(dateString + " asti");
        try {
            chosenEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
