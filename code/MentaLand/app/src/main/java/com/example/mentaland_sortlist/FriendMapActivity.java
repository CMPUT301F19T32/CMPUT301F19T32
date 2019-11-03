package com.example.mentaland_sortlist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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