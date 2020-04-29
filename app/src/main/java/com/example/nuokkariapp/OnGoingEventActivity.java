package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class OnGoingEventActivity extends AppCompatActivity {

    private TextView title, titleCounter, visitorCounter, titleFeedback, titleWriter;
    private EditText feedbackBox, writer;
    private CheckBox anonym;
    private Button visitorLeft, visitorCame, readFeedback, backToMainActivity, endEvent;
    private int visitorCount = 0;
    private Event onGoingEvent;
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
        anonym = (CheckBox) findViewById(R.id.checkBoxAnonym);
        visitorLeft = (Button) findViewById(R.id.buttonVisitorLeft);
        visitorCame = (Button) findViewById(R.id.buttonVisitorCame);
        readFeedback = (Button) findViewById(R.id.buttonReadFeedback);
        backToMainActivity = (Button) findViewById(R.id.buttonToFrontpage);
        endEvent = (Button) findViewById(R.id.buttonEndEvent);
        visitorCounter.setText(Integer.toString(visitorCount));
    }

    public void addVisitor(View v){
        visitorCount++;
        visitorCounter.setText(Integer.toString(visitorCount));
    }

    public void removeVisitor(View v){
        if(visitorCount > 0){
            visitorCount--;
            visitorCounter.setText(Integer.toString(visitorCount));
        }
    }

    public void submitFeedback(View v){
        if(anonym.isChecked()){
            Feedback fb = new Feedback(feedbackBox.getText().toString(), "anonyymi");
            FeedbackCollection.getInstance().addFeedbackToList(fb);
            feedbackBox.setText("");
            writer.setText("");
            anonym.setChecked(false);
        }else{
            Feedback fb = new Feedback(feedbackBox.getText().toString(), writer.getText().toString());
            FeedbackCollection.getInstance().addFeedbackToList(fb);
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
    }

    public void endEvent(View v){
        onGoingEvent.setOnGoing(false);
        int index = onGoingEvent.getIndex();
        EventCollection.getInstance().modifyEventOnList(onGoingEvent, index);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
