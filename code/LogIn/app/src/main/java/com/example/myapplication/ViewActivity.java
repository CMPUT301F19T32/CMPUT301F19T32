package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {
    public TextView view_username;
    public TextView view_emotion;
    public TextView view_reason;
    public TextView view_social;
    private ImageView image;
    private void initComponent() {
        image = (ImageView) findViewById(R.id.imageView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mood);
        initComponent();
        view_username = findViewById(R.id.view_username);
        view_emotion = findViewById(R.id.view_emotion);
        view_reason = findViewById(R.id.view_reason);
        view_social = findViewById(R.id.view_social);


        view_username.setText(getIntent().getStringExtra("username"));
        view_emotion.setText(getIntent().getStringExtra("emotion"));
        view_reason.setText(getIntent().getStringExtra("reason"));
        view_social.setText(getIntent().getStringExtra("social"));
        if(getIntent().getStringExtra("emotion").equals("happy")){
            image.setImageDrawable( getResources().getDrawable(R.drawable.img1));
        }
        if(getIntent().getStringExtra("emotion").equals("angry")){
            image.setImageDrawable( getResources().getDrawable(R.drawable.img2));
        }
        if(getIntent().getStringExtra("emotion").equals("sad")){
            image.setImageDrawable( getResources().getDrawable(R.drawable.img3));
        }

    }
}