package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nuokkariapp.UserInfo.LoginActivity;
import com.example.nuokkariapp.UserInfo.ProfileActivity;
import com.example.nuokkariapp.UserInfo.User;
import com.example.nuokkariapp.UserInfo.UserLocalStorage;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements myAdapter.OnEventListener {

    TextView titleEvents, titleUser, userName, userEmail;
    RecyclerView recyclerView;
    ImageView profilePic;
    boolean signedIn = false;
    Button addEvent, userInfo;
    UserLocalStorage userLocalStorage;
    ConstraintLayout layout;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter rvAdapter;
    EventCollection eventCollection;


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
        eventCollection = EventCollection.getInstance();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rvAdapter = new myAdapter(eventCollection.getEventArrayList(), this);
        recyclerView.setAdapter(rvAdapter);
        if(!signedIn){
            userName.setText("");
            userEmail.setText("");
            userInfo.setText("KIRJAUDU SISÄÄN");
        }
        layout = (ConstraintLayout) findViewById(R.id.layout);
        userLocalStorage = new UserLocalStorage(this);
    }

    public void createEvent(View v){
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("collection", (Serializable) eventCollection);
        startActivity(intent);
    }

    //button pressed to either sign in or to modify user information
    public void userInfo(View v){
        if(signedIn){
            modifyUserInfo();
        }else{
            signIn();
            User loggedInUser = userLocalStorage.getCurrentUser();
            userName.setText(loggedInUser.getName());
            userEmail.setText(loggedInUser.getEmail());
        }
    }

    public void signIn(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void modifyUserInfo(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void logOut(){
        userLocalStorage.clearUserData();
        userLocalStorage.setUserLoggedIn(false);
        signedIn = false;
        layout.removeView(findViewById(R.id.logOut));
    }

    public void onStart() {
        super.onStart();
        rvAdapter = new myAdapter(eventCollection.getEventArrayList(), this);
        recyclerView.setAdapter(rvAdapter);
    }


    @Override
    public void onEventClick(int position) {
        if(eventCollection.getEventArrayList().size() != 1) {
            Event chosenEvent = eventCollection.getEventArrayList().get(position + 1); //+1 because first event is not shown
            if(chosenEvent.isOnGoing()){
                Intent intent = new Intent(this, OnGoingEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("event", chosenEvent);
                intent.putExtras(bundle);
                startActivity(intent);
            }else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("chosenEvent", chosenEvent);
                Intent intent = new Intent(this, EventInfoActivity.class);
                intent.putExtras(bundle); //MODIFY EVENTACTIVITY TO GET THE EVENT & SHOW INFO
                intent.putExtra("position", position + 1);
                startActivity(intent);
            }
        }
    }
}
