package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity implements AddFriendFrag.OnFragmentInteractionListener{
    ListView moodFriendList;
    ArrayAdapter<Mood> moodFrArrayAdapter;
    ArrayList<Mood> moodFrArrayList;
    private int index;
    private String user;
    private Mood mood;
    ArrayList<String> friendList;
    ArrayList<Request> requestsList;
    String toast1 = "Already Friend";
    String toast2 = "Already Sent Earlier";
    String toast3 = "Already Sent";
    private  Request request;
    //friendList get from fireStore
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Intent toFriend = getIntent();
        user = toFriend.getStringExtra("user");
        moodFriendList = findViewById(R.id.friendLIstVeiw);
        moodFrArrayList = new ArrayList<>();

        for (int i = 0; i<friendList.size();i++){
            //找到 keyword = friendList.get(i)
            //找到 keyword 的 moodList.get(0)  mood = moodList.get(0)
            //moodFriendList.add()mood
        }


        moodFrArrayAdapter = new CustomeFriendList(this, moodFrArrayList);
        moodFriendList.setAdapter(moodFrArrayAdapter);
        Button requestButton = findViewById(R.id.request);
        final Button map = findViewById(R.id.map_fr);
        Button addFriend = findViewById(R.id.addFriend);
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
                Intent mapIntent  = new Intent(FriendActivity.this, FriendMoodMap.class );
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

        for (int i = 0; i < friendList.size(); i++){
            if (request.getReciveName() == friendList.get(i)){
                Toast.makeText(getApplicationContext(),toast1,Toast.LENGTH_LONG);
                return;
            }
        }
        for (int i = 0; i < requestsList.size(); i++){
            if ((requestsList.get(i).getSentName().toString() == user) &(requestsList.get(i).getReciveName() == request.getReciveName())){
                Toast.makeText(getApplicationContext(),toast2,Toast.LENGTH_LONG);
                return;
            }

        }
        //Request update to firestore
    }


}
