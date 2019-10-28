package com.example.mentaland__friendpage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView moodFriendList;
    ArrayAdapter<Mood> moodFrArrayAdapter;
    ArrayList<Mood> moodFrArrayList;
    private int index;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moodFriendList = findViewById(R.id.friendLIstVeiw);
        moodFrArrayList = new ArrayList<>();
        moodFrArrayAdapter = new CustomeFriendList(this, moodFrArrayList);
        moodFriendList.setAdapter(moodFrArrayAdapter);
        Button requestButton = findViewById(R.id.request);
        Button map = findViewById(R.id.map_fr);
        Button addFriend = findViewById(R.id.addFriend);
        user = "a";
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = moodFrArrayList.size() + 1;
                new addFriendFrag(user).show(getSupportFragmentManager(),"addFriend");

            }
        });


    }
    
}
