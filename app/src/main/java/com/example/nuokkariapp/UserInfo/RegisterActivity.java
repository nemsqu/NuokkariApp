package com.example.nuokkariapp.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuokkariapp.MainActivity;
import com.example.nuokkariapp.PasswordValidityChecker;
import com.example.nuokkariapp.R;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password, confirmPassword;
    Button register;
    DatabaseHelper databaseHelper;
    PasswordValidityChecker passwordValidityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.editTextName);
        name.setText("");
        email = (EditText) findViewById(R.id.editTextEmail);
        email.setText("");
        password = (EditText) findViewById(R.id.editTextPassword);
        password.setText("");
        confirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        confirmPassword.setText("");
        confirmPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(confirmPassword.getWindowToken(), 0);
            return true;
            }
        });
        register = (Button) findViewById(R.id.buttonLogin);
        databaseHelper = new DatabaseHelper(this);
        passwordValidityChecker = new PasswordValidityChecker();
    }

    //validates given information, checks if email is already connected to another user
    //creates new user if all is fine
    public void register(View v) {

        String userName = name.getText().toString();
        String Email = email.getText().toString();
        String firstPassword = password.getText().toString();
        String Password = confirmPassword.getText().toString();
        String number = "";

        if (userName.equals("") || Email.equals("") || firstPassword.equals("") || Password.equals("")) {
            Toast.makeText(this, "Kentät eivät voi olla tyhjiä", Toast.LENGTH_SHORT).show();
        }
        if (!passwordValidityChecker.checkPassword(firstPassword, Password)) {
            Toast.makeText(this, "Salasanat eivät täsmää.", Toast.LENGTH_SHORT).show();
        } else {
            String passwordCheck = passwordValidityChecker.checkPassword(Password);
            if (!passwordCheck.equals("ok")) {
                Toast.makeText(this, passwordCheck, Toast.LENGTH_LONG).show();
            } else {
                User user = new User(userName, number, Email, Password);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                Cursor cursor = databaseHelper.getInformation(db);
                String email;
                boolean newEmail = true;
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    cursor.getString(cursor.getColumnIndex("ID"));
                    email = cursor.getString(cursor.getColumnIndex("userEmail"));
                    cursor.getString(cursor.getColumnIndex("userName"));
                    cursor.getString(cursor.getColumnIndex("userPhone"));
                    cursor.getString(cursor.getColumnIndex("userPassword"));
                    if (email.equals(Email)) {
                        Toast.makeText(this, "Sähköpostiosoite on jo käytössä", Toast.LENGTH_LONG).show();
                        newEmail = false;
                    }
                }
                if (newEmail) {
                    boolean createUser = databaseHelper.addData(user);
                    if (createUser) {
                        System.out.println("User created");
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Jokin meni pieleen, yritä uudestaan.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }

}
