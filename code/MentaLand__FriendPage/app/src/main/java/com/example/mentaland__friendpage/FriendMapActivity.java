package com.example.mentaland__friendpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendMapActivity extends AppCompatActivity {

    private ArrayList<Mood> mapMood;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendmap);
        Bundle bundle = getIntent().getExtras();
        mapMood = (ArrayList<Mood>) bundle.getSerializable("array");
    }
}