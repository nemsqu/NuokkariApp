package com.example.nuokkariapp.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuokkariapp.CodeCreator;
import com.example.nuokkariapp.MainActivity;
import com.example.nuokkariapp.R;

public class UserCheckActivity extends AppCompatActivity {

    private TextView numbers;
    private EditText input;
    private Button buttonDone;
    private CodeCreator codeCreator;
    private String code;
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check);
        numbers = (TextView) findViewById(R.id.textViewNumbers);
        input = (EditText) findViewById(R.id.inputNumbers);
        input.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                return true;
            }
        });
        buttonDone = (Button) findViewById(R.id.buttonDone);
        codeCreator = new CodeCreator();
        code = codeCreator.createCode();
        numbers.setText(code);
    }

    public void checkInputNumbers(View v){
        String userInput = input.getText().toString();
        if(userInput.equals(code)){
            Toast.makeText(this, "Koodi oikein.", Toast.LENGTH_SHORT).show();
            UserLocalStorage.getInstance().setLoggedInUser(loggedInUser);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Yritä uudestaan.", Toast.LENGTH_SHORT).show();
            code = codeCreator.createCode();
            numbers.setText(code);
            input.setText("");
        }
    }

    public void onStart() {
        super.onStart();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        loggedInUser = (User) bundle.getSerializable("user");
    }
}
