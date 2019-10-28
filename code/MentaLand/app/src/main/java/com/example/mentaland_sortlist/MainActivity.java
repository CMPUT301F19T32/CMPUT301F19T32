package com.example.mentaland_sortlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView moodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;
    FloatingActionButton delete_mood;
    ListView filterList;
    ArrayAdapter<Mood> filterAdapter;
    ArrayList<Mood> filterDataList;
    Button filter_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        delete_mood = findViewById(R.id.delete_mood);
        moodList = findViewById(R.id.mood_list);
        filter_button = findViewById(R.id.filter_button);
        filterList = findViewById(R.id.filter_list);

        String []mood = {"Happy", "Angry", "Happy", "Sad", "Happy"};
        String []date = {"1010-09-01", "1115-08-06", "2015-10-17", "2015-09-18", "2015-10-21"};
        String []something = {"1", "2", "3", "4", "5"};

        moodDataList = new ArrayList<>();

        for (int i = 0; i < mood.length; i++) {
            moodDataList.add((new Mood(mood[i],something[i],something[i],something[i], date[i],something[i],something[i])));
        }

        moodAdapter = new myMoodList(this, moodDataList);

        moodList.setAdapter(moodAdapter);

        // filterDataList = new ArrayList<>();

    }
}
