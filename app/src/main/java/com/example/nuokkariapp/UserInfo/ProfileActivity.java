package com.example.nuokkariapp.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuokkariapp.PasswordValidityChecker;
import com.example.nuokkariapp.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView title, user, email, phone, password;
    private EditText editUser, editEmail, editPhone, editPassword;
    private Button save, logOut;
    private UserLocalStorage userLocalStorage;
    private User currentUser;
    private PasswordValidityChecker passwordValidityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        title = (TextView) findViewById(R.id.profileTitle);
        user = (TextView) findViewById(R.id.profileUser);
        editUser = (EditText) findViewById(R.id.profileEditName);
        email = (TextView) findViewById(R.id.profileEmail);
        editEmail = (EditText) findViewById(R.id.profileEditEmail);
        phone = (TextView) findViewById(R.id.profilePhoneNumber);
        editPhone = (EditText) findViewById(R.id.profileEditPhoneNumber);
        password = (TextView) findViewById(R.id.profilePassword);
        editPassword = (EditText) findViewById(R.id.profileEditPassword);
        editPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(editPassword.getWindowToken(), 0);
                return true;
            }
        });
        save = (Button) findViewById(R.id.buttonSaveChanges);
        logOut = (Button) findViewById(R.id.logOut);
        passwordValidityChecker = new PasswordValidityChecker();
    }

    //verifies fields before saving
    public void saveChanges(View v){
        if(editUser.getText().toString().equals("")) {
            Toast.makeText(this, "Nimi ei voi olla tyhjä.", Toast.LENGTH_LONG).show();
        }else if(editEmail.getText().toString().equals("")) {
            Toast.makeText(this, "Sähköposti ei voi olla tyhjä.", Toast.LENGTH_LONG).show();
        }else if(editPassword.getText().toString().equals("")){
            Toast.makeText(this, "Salasana ei voi olla tyhjä.", Toast.LENGTH_LONG).show();
        }else {
            String passwordCheck = passwordValidityChecker.checkPassword(editPassword.getText().toString());
            if (!passwordCheck.equals("ok")) {
                Toast.makeText(this, passwordCheck, Toast.LENGTH_LONG).show();
            } else {
                currentUser.setName(editUser.getText().toString());
                currentUser.setEmail(editEmail.getText().toString());
                currentUser.setPhoneNumber(editPhone.getText().toString());
                currentUser.setPassword(editPassword.getText().toString());
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                Cursor data = databaseHelper.getID(email.getText().toString());
                databaseHelper.updateUser(currentUser);
            }
        }
    }

    public void logOut(View v){
        userLocalStorage.logUserOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onStart() {
        super.onStart();
        userLocalStorage = UserLocalStorage.getInstance();
        currentUser = userLocalStorage.getLoggedInUser();
        editUser.setText(currentUser.getName());
        editEmail.setText(currentUser.getEmail());
        editPhone.setText(currentUser.getPhoneNumber());
        editPassword.setText(currentUser.getPassword());
    }

}
