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

import com.example.nuokkariapp.R;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private TextView register;
    private EditText email, password;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.buttonLogin);
        register = (TextView) findViewById(R.id.textViewNewUser);
        email = (EditText) findViewById(R.id.editTextLoginEmail);
        password = (EditText) findViewById(R.id.editTextLoginPassword);
        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
            return true;
        }
    });
        databaseHelper = new DatabaseHelper(this);
    }

    //compares given information to information in databse
    public void login(View v){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = databaseHelper.getInformation(db);
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userName = "", userPhone = "";
        boolean correctInfo = false;
        while(cursor.moveToNext()){
            String compareEmail = cursor.getString(cursor.getColumnIndex("userEmail"));
            userName = cursor.getString(cursor.getColumnIndex("userName"));
            userPhone = cursor.getString(cursor.getColumnIndex("userPhone"));
            String comparePassword = cursor.getString(cursor.getColumnIndex("userPassword"));
            if(compareEmail.equals(userEmail) && comparePassword.equals(userPassword)){
                correctInfo = true;
                break;
            }
        }
        if(correctInfo) {
            User user = new User(userName, userPhone, email.getText().toString(), password.getText().toString());
            Cursor idCursor = databaseHelper.getID(email.getText().toString());
            int userID = -1;
            while(idCursor.moveToNext()){
                userID = idCursor.getInt(0);
            }
            user.setID(userID);
            Intent intent = new Intent(this, UserCheckActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Väärä käyttäjänimi tai salasana", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
