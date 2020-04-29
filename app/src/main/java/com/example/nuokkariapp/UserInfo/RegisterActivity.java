package com.example.nuokkariapp.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuokkariapp.MainActivity;
import com.example.nuokkariapp.R;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password, confirmPassword;
    Button register;
    DatabaseHelper databaseHelper;

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
        register = (Button) findViewById(R.id.buttonLogin);
        databaseHelper = new DatabaseHelper(this);
    }

    public void register(View v){

        String userName = name.getText().toString();
        String Email = email.getText().toString();
        String Password = confirmPassword.getText().toString();
        String number = "";

        User user = new User(userName, number, Email, Password);
        boolean createUser = databaseHelper.addData(user);
        if(createUser) {
            System.out.println("created");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Jokin meni pieleen, yritä uudestaan.", Toast.LENGTH_SHORT).show();
        }

    }
}
