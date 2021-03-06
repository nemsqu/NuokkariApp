package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuokkariapp.UserInfo.LoginActivity;
import com.example.nuokkariapp.UserInfo.ProfileActivity;
import com.example.nuokkariapp.UserInfo.UserLocalStorage;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements EventViewAdapter.OnEventListener {

    private TextView titleEvents, titleUser, userName, userEmail;
    private RecyclerView recyclerView;
    private ImageView profilePic;
    private boolean signedIn = false;
    private Button addEvent, userInfo, search;
    private UserLocalStorage userLocalStorage;
    private ConstraintLayout layout;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter rvAdapter;
    private EventCollection eventCollection;
    private EditText searchBox;
    private ArrayList<Event> eventArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleEvents = (TextView) findViewById(R.id.textView);
        titleUser = (TextView) findViewById(R.id.textView2);
        profilePic = (ImageView) findViewById(R.id.imageView);
        userName = (TextView) findViewById(R.id.textViewUser);
        userEmail = (TextView) findViewById(R.id.textViewEmail);
        addEvent = (Button) findViewById(R.id.buttonAddNewEvent);
        userInfo = (Button) findViewById(R.id.buttonEditInfo);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        search = (Button) findViewById(R.id.buttonSearch);
        searchBox = (EditText) findViewById(R.id.searchBox);
        eventCollection = EventCollection.getInstance();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        eventArrayList = eventCollection.getEventArrayList();
        rvAdapter = new EventViewAdapter(eventArrayList, this);
        recyclerView.setAdapter(rvAdapter);
        userLocalStorage = UserLocalStorage.getInstance();
        if(!signedIn){
            userName.setText("");
            userEmail.setText("");
            userInfo.setText("KIRJAUDU SISÄÄN");
        }else{
            userName.setText(userLocalStorage.getLoggedInUser().getName());
            userEmail.setText(userLocalStorage.getLoggedInUser().getEmail());
            userInfo.setText("MUOKKAA TIETOJA");
        }
        layout = (ConstraintLayout) findViewById(R.id.layout);
    }

    public void createEvent(View v){
        if(signedIn) {
            Event event = new Event("", "", "", "", "", "", 200, "", "", 0);
            Bundle bundle = new Bundle();
            bundle.putSerializable("event", event);
            Intent intent = new Intent(this, AddEventActivity.class);
            intent.putExtras(bundle);
            intent.putExtra("reused", false);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Kirjaudu ensin sisään", Toast.LENGTH_SHORT).show();
        }
    }

    public void reuseEvent(View v){
        if(signedIn) {
            Intent intent = new Intent(this, ReuseEventActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Kirjaudu ensin sisään", Toast.LENGTH_SHORT).show();
        }
    }

    //button pressed to either sign in or to modify user information
    public void userInfo(View v){
        if(signedIn){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


    public void onStart() {
        super.onStart();
        rvAdapter = new EventViewAdapter(eventCollection.getEventArrayList(), this);
        recyclerView.setAdapter(rvAdapter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        signedIn = userLocalStorage.isLoggedIn();
        if(!signedIn){
            userName.setText("");
            userEmail.setText("");
            userInfo.setText("KIRJAUDU SISÄÄN");
        }else {
            userName.setText(userLocalStorage.getLoggedInUser().getName());
            userEmail.setText(userLocalStorage.getLoggedInUser().getEmail());
            userInfo.setText("MUOKKAA TIETOJA");
        }
    }


    //open event from recyclerview according to its state when it is clicked
    @Override
    public void onEventClick(int position) {
            Event chosenEvent = eventArrayList.get(position);
            if(chosenEvent.isOnGoing() && signedIn){
                Intent intent = new Intent(this, OnGoingEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("event", chosenEvent);
                intent.putExtras(bundle);
                startActivity(intent);
            }else if(!chosenEvent.isOnGoing() && signedIn){
                Bundle bundle = new Bundle();
                bundle.putSerializable("chosenEvent", chosenEvent);
                Intent intent = new Intent(this, EventInfoActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("position", position + 1);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Kirjaudu ensin sisään", Toast.LENGTH_SHORT).show();
            }
    }

    //search events by description
    public void search(View v){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        assert inputManager != null;
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        String text = searchBox.getText().toString();
        ArrayList<Event> events = EventCollection.getInstance().getEventArrayList();
        ArrayList<Event> filteredEvents = new ArrayList<>();
        if(text.equals("")){
            filteredEvents.addAll(events);
        }else {
            for (Event event : events) {
                if (event.getDescription().contains(text)) {
                    filteredEvents.add(event);
                }
            }
        }
        eventArrayList = filteredEvents;
        rvAdapter = new EventViewAdapter(eventArrayList, this);
        recyclerView.setAdapter(rvAdapter);
    }
}
