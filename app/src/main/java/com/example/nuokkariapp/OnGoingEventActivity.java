package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OnGoingEventActivity extends AppCompatActivity {

    private TextView title, titleCounter, visitorCounter, titleFeedback, titleWriter;
    private EditText feedbackBox, writer;
    private CheckBox anonym;
    private Button visitorLeft, visitorCame, readFeedback, backToMainActivity, endEvent;
    private int visitorCount, visitorLimit;
    private Event onGoingEvent;
    private SimpleDateFormat format;
    private boolean hasRecurrance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going_event);
        title = (TextView) findViewById(R.id.titleEventOn);
        titleCounter = (TextView)findViewById(R.id.titleVisitorCount);
        visitorCounter = (TextView)findViewById(R.id.visitorCounter);
        titleFeedback = (TextView)findViewById(R.id.titleGiveFeedback);
        titleWriter = (TextView)findViewById(R.id.titleWriter);
        feedbackBox = (EditText) findViewById(R.id.feebackBox);
        writer = (EditText) findViewById(R.id.writerName);
        writer.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(writer.getWindowToken(), 0);
                return true;
            }
        });
        anonym = (CheckBox) findViewById(R.id.checkBoxAnonym);
        visitorLeft = (Button) findViewById(R.id.buttonVisitorLeft);
        visitorCame = (Button) findViewById(R.id.buttonVisitorCame);
        readFeedback = (Button) findViewById(R.id.buttonReadFeedback);
        backToMainActivity = (Button) findViewById(R.id.buttonToFrontpage);
        endEvent = (Button) findViewById(R.id.buttonEndEvent);
        visitorCounter.setText(Integer.toString(visitorCount));
        format = new SimpleDateFormat("dd.MM.yyy");
    }

    public void addVisitor(View v){
        if(visitorCount<visitorLimit) {
            visitorCount++;
            visitorCounter.setText(String.valueOf(visitorCount));
            onGoingEvent.setVisitorCount(visitorCount);
            EventCollection.getInstance().modifyEventOnList(onGoingEvent, onGoingEvent.getIndex());
        }
    }

    public void removeVisitor(View v){
        if(visitorCount > 0){
            visitorCount--;
            visitorCounter.setText(String.valueOf(visitorCount));
            onGoingEvent.setVisitorCount(visitorCount);
            EventCollection.getInstance().modifyEventOnList(onGoingEvent, onGoingEvent.getIndex());
        }
    }

    public void submitFeedback(View v){
        if(anonym.isChecked()){
            Feedback fb = new Feedback(feedbackBox.getText().toString(), "anonyymi");
            onGoingEvent.addFeedbackToEvent(fb);
            EventCollection.getInstance().modifyEventOnList(onGoingEvent, onGoingEvent.getIndex());
            feedbackBox.setText("");
            writer.setText("");
            anonym.setChecked(false);
        }else{
            Feedback fb = new Feedback(feedbackBox.getText().toString(), writer.getText().toString());
            onGoingEvent.addFeedbackToEvent(fb);
            EventCollection.getInstance().modifyEventOnList(onGoingEvent, onGoingEvent.getIndex());
            feedbackBox.setText("");
            writer.setText("");
        }
    }

    public void backToMainActivity(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        onGoingEvent = (Event) bundle.getSerializable("event");
        title.setText(onGoingEvent.getName());
        visitorCount = onGoingEvent.getVisitorCount();
        visitorCounter.setText(String.valueOf(visitorCount));
        visitorLimit = onGoingEvent.getVisitorLimit();
    }

    //ends event and tells how long event lasted
    //adds event to archive if not recurring, otherwise adds next event to upcoming events list
    public void endEvent(View v) {
        onGoingEvent.setOnGoing(false);
        onGoingEvent.setVisitorCount(0);
        Date time = new Date();
        Timer.getInstance().setEndTime(time);
        String d = Timer.getInstance().getDuration();
        Timer.getInstance().initializeTimer();
        Toast.makeText(this, d, Toast.LENGTH_LONG).show();
        EventCollection.getInstance().endEvent(onGoingEvent.getIndex());
        for(RecurringEvent e : EventCollection.getInstance().getRecurringEventArrayList()){
            if(onGoingEvent.getID() == e.getID()){
                hasRecurrance = true;
                String stringDate = onGoingEvent.getDate();
                try {
                    Date date = format.parse(stringDate);
                    long dateLong = date.getTime() + e.getDifferenceBetweenEvents();
                    String dateString = new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateLong));
                    Date newDate = format.parse(dateString);
                    if(!newDate.before(e.getRecurringUntil())){
                        EventCollection.getInstance().getRecurringEventArrayList().remove(e.getIndex());
                        EventArchive.getInstance().addEventToArchive(onGoingEvent);
                    }else{
                        onGoingEvent.setDate(dateString);
                        onGoingEvent.setIndex(EventCollection.getInstance().getEventArrayList().size());
                        EventCollection.getInstance().addEventToList(onGoingEvent);
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(!hasRecurrance){
            EventArchive.getInstance().addEventToArchive(onGoingEvent);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void viewFeedback(View v){
        Intent intent = new Intent(this, FeedbackViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", onGoingEvent);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
