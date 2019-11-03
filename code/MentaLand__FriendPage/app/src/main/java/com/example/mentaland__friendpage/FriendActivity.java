package com.example.mentaland__friendpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity implements AddFriendFrag.OnFragmentInteractionListener{
    ListView moodFriendList;
    ArrayAdapter<Mood> moodFrArrayAdapter;
    ArrayList<Mood> moodFrArrayList;
    private int index;
    private String user;
    private Mood mood;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        moodFriendList = findViewById(R.id.friendLIstVeiw);
        moodFrArrayList = new ArrayList<>();
        moodFrArrayAdapter = new CustomeFriendList(this, moodFrArrayList);
        moodFriendList.setAdapter(moodFrArrayAdapter);
        Button requestButton = findViewById(R.id.request);
        final Button map = findViewById(R.id.map_fr);
        Button addFriend = findViewById(R.id.addFriend);
        user = "a";
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = moodFrArrayList.size() + 1;
                new AddFriendFrag(user).show(getSupportFragmentManager(),"addFriend");

            }
        });

        map.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mapIntent  = new Intent(FriendActivity.this, FriendMapActivity.class );
                Bundle mapBundle = new Bundle();
                mapBundle.putSerializable("array", moodFrArrayList);
                mapIntent.putExtras(mapBundle);
                startActivity(mapIntent);

            }
        });
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestIntent  = new Intent(FriendActivity.this, RequestActivity.class );
                requestIntent.putExtra("user",user);
                startActivity(requestIntent);
            }
        });
        moodFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mood = moodFrArrayAdapter.getItem(position);
                /// add new activity relatide to homepage
            }
        });
    }

    @Override
    public void onOkPress(Request request) {
        //search and return toast in Friend page
        // return already, is friend , not a user, sent.
    }


}
