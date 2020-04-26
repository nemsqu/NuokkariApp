package com.example.nuokkariapp.UserInfoUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nuokkariapp.R;

public class RegisterActivity extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView password;
    TextView confirmPassword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (TextView) findViewById(R.id.editTextName);
        email = (TextView) findViewById(R.id.editTextEmail);
        password = (TextView) findViewById(R.id.editTextPassword);
        confirmPassword = (TextView) findViewById(R.id.editTextConfirmPassword);
        register = (Button) findViewById(R.id.buttonLogin);
    }

    public void register(View v){

    }
}
