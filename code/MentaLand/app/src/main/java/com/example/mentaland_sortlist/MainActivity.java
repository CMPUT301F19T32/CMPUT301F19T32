package com.example.mentaland_sortlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {
    ListView moodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;
    LinearLayout filterLayout;
    //ArrayAdapter<Mood> filterAdapter;
    ArrayList<Mood> filterDataList;
    Button filter_button;
    Button filter_angry;
    Button filter_sad;
    Button filter_happy;
    Button filter_all;
    static int chk=0;
    Button map_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moodList = findViewById(R.id.mood_list);
        filter_button = findViewById(R.id.filter_button);
        filterLayout = findViewById(R.id.filter_layout);
        filter_angry = findViewById(R.id.filter_angry);
        filter_happy = findViewById(R.id.filter_happy);
        filter_sad = findViewById(R.id.filter_sad);
        filter_all = findViewById(R.id.filter_all);

       // String []all_moods = {"Angry", "Happy", "Sad"};

        String []mood = {"Happy", "Angry", "Happy", "Sad", "Happy"};
        String []date = {"1010-09-01", "1115-08-06", "2015-10-17", "2015-09-18", "2015-10-21"};
        String []something = {"1", "2", "3", "4", "5"};

        moodDataList = new ArrayList<>();

        for (int i = 0; i < mood.length; i++) {
            moodDataList.add((new Mood(mood[i],something[i],something[i],something[i], date[i],something[i],something[i])));
        }

        moodAdapter = new myMoodList(this,moodDataList);

        moodList.setAdapter(moodAdapter);

        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterLayout.setVisibility(View.VISIBLE);
            }
        });

        filter_sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterDataList = new ArrayList<>();

                for(int num = 0;num < moodDataList.size();num++) {
                    if (moodDataList.get(num).getEmotionstr().equals("Sad")) {
                        String emotionState = moodDataList.get(num).getEmotionState();
                        String reason = moodDataList.get(num).getReason();
                        String time = moodDataList.get(num).getTime();
                        String date = moodDataList.get(num).getDate();
                        String socialState = moodDataList.get(num).getSocialState();
                        String username = moodDataList.get(num).getUsername();
                        filterDataList.add(new Mood("Sad", emotionState, reason, time, date, socialState, username));
                        chk = 1;

                    }
                }
                filterLayout.setVisibility(View.INVISIBLE);
                filter_show(filterDataList);

            }
        });

        filter_angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterDataList = new ArrayList<>();

                for(int num = 0;num < moodDataList.size();num++) {
                    if (moodDataList.get(num).getEmotionstr().equals("Angry")) {
                        String emotionState = moodDataList.get(num).getEmotionState();
                        String reason = moodDataList.get(num).getReason();
                        String time = moodDataList.get(num).getTime();
                        String date = moodDataList.get(num).getDate();
                        String socialState = moodDataList.get(num).getSocialState();
                        String username = moodDataList.get(num).getUsername();
                        filterDataList.add(new Mood("Angry", emotionState, reason, time, date, socialState, username));
                        chk = 1;

                    }
                }
                filterLayout.setVisibility(View.INVISIBLE);
                filter_show(filterDataList);

            }
        });

        filter_happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterDataList = new ArrayList<>();

                for(int num = 0;num < moodDataList.size();num++) {
                    if (moodDataList.get(num).getEmotionstr().equals("Happy")) {
                        String emotionState = moodDataList.get(num).getEmotionState();
                        String reason = moodDataList.get(num).getReason();
                        String time = moodDataList.get(num).getTime();
                        String date = moodDataList.get(num).getDate();
                        String socialState = moodDataList.get(num).getSocialState();
                        String username = moodDataList.get(num).getUsername();
                        filterDataList.add(new Mood("Happy", emotionState, reason, time, date, socialState, username));
                        chk = 1;

                    }
                }
                filterLayout.setVisibility(View.INVISIBLE);
                filter_show(filterDataList);

            }
        });
        filter_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterLayout.setVisibility(View.INVISIBLE);
                filter_show(moodDataList);
                chk = 0;

            }
        });

        map_button = findViewById(R.id.map_button);
        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);


            }
        });



    }

    public void filter_show(ArrayList<Mood> myDataList){
        moodAdapter = new myMoodList(this, myDataList);
        moodList.setAdapter(moodAdapter);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
