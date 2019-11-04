package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity {



    TextView usernameProfile;
    EditText introductionProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Intent intentMyProfile = getIntent();
        String username_profile = intentMyProfile.getStringExtra("usernameMyProfile");



        usernameProfile = findViewById(R.id.username_myProfile);
        introductionProfile = findViewById(R.id.introduction_field);

        usernameProfile.setText(username_profile);

    }
}
