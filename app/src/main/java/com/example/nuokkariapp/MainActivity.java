package com.example.nuokkariapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nuokkariapp.UserInfoUI.LoginActivity;


public class MainActivity extends AppCompatActivity {

    TextView titleEvents;
    TextView titleUser;
    ImageView profilePic;
    TextView userName;
    TextView userEmail;
    boolean signedIn = false;
    Button addEvent;
    Button userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleEvents = (TextView) findViewById(R.id.textView);
        titleUser = (TextView) findViewById(R.id.textView2);
        profilePic = (ImageView) findViewById(R.id.imageView);
        userName = (TextView) findViewById(R.id.textViewUser);
        userEmail = (TextView) findViewById(R.id.textViewEmail);
        addEvent = (Button) findViewById(R.id.buttonAddEvent);
        userInfo = (Button) findViewById(R.id.buttonEditInfo);
        if(!signedIn){
            userName.setText("");
            userEmail.setText("");
            userInfo.setText("KIRJAUDU SISÄÄN");
        }
    }

    public void createEvent(View v){

    }

    //button pressed to either sign in or to modify user information
    public void userInfo(View v){
        signIn();
        if(signedIn){
            //modifyUserInfo();
        }else{
            //singIn();
        }
    }

    public void signIn(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
