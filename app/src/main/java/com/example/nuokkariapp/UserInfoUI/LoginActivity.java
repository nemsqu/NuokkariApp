package com.example.nuokkariapp.UserInfoUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nuokkariapp.R;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView register;
    TextView email;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.buttonLogin);
        register = (TextView) findViewById(R.id.textViewNewUser);
        email = (TextView) findViewById(R.id.editTextLoginEmail);
        password = (TextView) findViewById(R.id.editTextLoginPassword);
    }

    public void login(View v){

    }

    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
