package com.example.mentaland_sortlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView moodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;
    ListView filterList;
    ArrayAdapter<Mood> filterAdapter;
    ArrayList<Mood> filterDataList;
    Button filter_button;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moodList = findViewById(R.id.mood_list);
        filter_button = findViewById(R.id.filter_button);
        filterList = findViewById(R.id.filter_list);
        add_button = findViewById(R.id.button);
        String []mood = {"Happy", "Angry", "Happy", "Sad", "Happy"};
        String []date = {"1010-09-01", "1115-08-06", "2015-10-17", "2015-09-18", "2015-10-21"};
        String []something = {"1", "2", "3", "4", "5"};

        moodDataList = new ArrayList<>();

        for (int i = 0; i < mood.length; i++) {
            moodDataList.add((new Mood(mood[i],something[i],something[i],something[i], date[i],something[i],something[i])));
        }
        final Intent intent = new Intent(this,Create.class);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(intent);
            }
        });
        moodAdapter = new myMoodList(this,moodDataList);

        moodList.setAdapter(moodAdapter);



    }
}
