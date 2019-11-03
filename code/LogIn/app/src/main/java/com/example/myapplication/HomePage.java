package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    ListView moodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;
    ListView filterList;
    ArrayAdapter<Mood> filterAdapter;
    ArrayList<Mood> filterDataList;
    Button filter_button;
    Button add_button;
    FirebaseFirestore db;
    TextView test;
    String TAG = "sample";
    private TextView textView;
    private LocationManager locationManager;
    private double longitude;
    private double latitude;
    Button map_button;
    Button followed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent tohome = getIntent();
        String username = tohome.getStringExtra("username");
        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("Account").document(username);
        moodList = findViewById(R.id.mood_list);
        filter_button = findViewById(R.id.filter_button);
        filterList = findViewById(R.id.filter_list);
        add_button = findViewById(R.id.button);
        test= findViewById(R.id.test);
        DocumentReference getdata = db.collection("Account").document(username);
        getdata.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    StringBuilder fields = new StringBuilder("");
                    fields.append(document.get("password"));
                    test.setText(fields.toString());
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        TextView name= findViewById(R.id.uname);
        String []mood = {"Happy", "Angry", "Happy", "Sad", "Happy"};
        String []date = {"1010-09-01", "1115-08-06", "2015-10-17", "2015-09-18", "2015-10-21"};
        String []something = {"1", "2", "3", "4", "5"};
        name.setText(username);
        moodDataList = new ArrayList<>();

        for (int i = 00; i < mood.length; i++) {
            moodDataList.add((new Mood(mood[i],something[i],something[i],something[i], date[i],something[i],something[i])));
        }
        moodAdapter = new myMoodList(this,moodDataList);
        moodList.setAdapter(moodAdapter);
        textView = findViewById(R.id.location);
        map_button = findViewById(R.id.map_button);
        map_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,MapsActivity.class);
                startActivity(intent);
            }
        });


        followed = findViewById(R.id.followed_button);
        followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentf = new Intent(HomePage.this,FriendActivity.class);
                HomePage.this.startActivity(intentf);
            }
        });
        //Switch to Create Activity after click "Add Mood" button
        final Intent intent = new Intent(this,Create.class);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage.this.startActivity(intent);
            }
        });




    }
}
