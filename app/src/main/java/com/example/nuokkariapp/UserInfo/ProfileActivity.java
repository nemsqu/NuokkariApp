package com.example.nuokkariapp.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nuokkariapp.MainActivity;
import com.example.nuokkariapp.R;

public class ProfileActivity extends AppCompatActivity {

    TextView title;
    TextView user;
    EditText editUser;
    TextView email;
    EditText editEmail;
    TextView phone;
    EditText editPhone;
    TextView password;
    EditText editPassword;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        title = (TextView) findViewById(R.id.profileTitle);
        user = (TextView) findViewById(R.id.profileUser);
        editUser = (EditText) findViewById(R.id.editTextName);
        email = (TextView) findViewById(R.id.profileEmail);
        editEmail = (EditText) findViewById(R.id.profileEditEmail);
        phone = (TextView) findViewById(R.id.profilePhoneNumber);
        editPhone = (EditText) findViewById(R.id.profileEditPhoneNumber);
        password = (TextView) findViewById(R.id.profilePassword);
        editPassword = (EditText) findViewById(R.id.profileEditPassword);
        save = (Button) findViewById(R.id.buttonSaveChanges);
    }

    public void saveChanges(View v){

    }

    public void logOut(View v){

    }

}
