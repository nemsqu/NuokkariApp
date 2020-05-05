package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditFeedbackActivity extends AppCompatActivity {

    private TextView mainTitle, writerTitle, feedbackTitle;
    private EditText writer, feedback;
    private Button buttonSaveChanges;
    private Feedback currentFeedback;
    private int index;
    private Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feedback);
        mainTitle = (TextView) findViewById(R.id.editFeedbackTitle);
        writerTitle = (TextView) findViewById(R.id.titleWriter);
        feedbackTitle = (TextView) findViewById(R.id.titleWriter);
        writer = (EditText) findViewById(R.id.editWriter);
        feedback = (EditText) findViewById(R.id.editFeedback);
        buttonSaveChanges = (Button) findViewById(R.id.buttonSaveEditedFeedback);
    }

    public void saveChanges(View v){
        currentFeedback.setGivenFeedback(feedback.getText().toString());
        currentFeedback.setWriter(writer.getText().toString());
        currentEvent.modifyFeedbackOnList(currentFeedback, index);
        EventCollection.getInstance().modifyEventOnList(currentEvent, currentEvent.getIndex());
        Intent intent = new Intent(this, FeedbackViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", currentEvent);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onStart() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentFeedback = (Feedback) bundle.getSerializable("fb");
        currentEvent = (Event) bundle.getSerializable("event");
        index = (int) bundle.get("index");
        feedback.setText(currentFeedback.getGivenFeedback());
        writer.setText(currentFeedback.getWriter());
        super.onStart();
    }
}
