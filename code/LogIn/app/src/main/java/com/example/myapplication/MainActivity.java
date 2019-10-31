package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button signInButton;
    Button signUpButton;

    EditText usernameEditText;
    EditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.signIn_button);
        signUpButton= findViewById(R.id.signUp_button);
        usernameEditText = findViewById(R.id.username_field);
        passwordEditText = findViewById(R.id.password_field);

        final Intent intent = new Intent(this, signUpPage.class);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(intent);
            }

        });



    }
}
