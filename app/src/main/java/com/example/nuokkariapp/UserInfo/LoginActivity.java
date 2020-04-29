package com.example.nuokkariapp.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nuokkariapp.MainActivity;
import com.example.nuokkariapp.R;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView register;
    TextView email;
    TextView password;
    UserLocalStorage userLocalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.buttonLogin);
        register = (TextView) findViewById(R.id.textViewNewUser);
        email = (TextView) findViewById(R.id.editTextLoginEmail);
        password = (TextView) findViewById(R.id.editTextLoginPassword);
        userLocalStorage = new UserLocalStorage(this);
    }

    public void login(View v){
        User user = new User(email.getText().toString(), password.getText().toString());
        userLocalStorage.storeUserData(user);
        userLocalStorage.setUserLoggedIn(true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
