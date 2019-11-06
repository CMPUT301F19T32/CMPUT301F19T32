package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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


    LinearLayout filterLayout;
    ArrayList<Mood> filterDataList;
    Button filter_button;
    Button filter_angry;
    Button filter_sad;
    Button filter_happy;
    Button filter_all;
    static int chk=0;
    Button map_button;

    Button add_button;
    FirebaseFirestore db;
    TextView test;
    String TAG = "sample";

    Button followed;

    Button myProfileButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent tohome = getIntent();
        final String usernameMain = tohome.getStringExtra("username");

        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("Account").document(usernameMain);


        moodList = findViewById(R.id.mood_list);
        filter_button = findViewById(R.id.filter_button);
        filterLayout = findViewById(R.id.filter_layout);
        filter_angry = findViewById(R.id.filter_angry);
        filter_happy = findViewById(R.id.filter_happy);
        filter_sad = findViewById(R.id.filter_sad);
        filter_all = findViewById(R.id.filter_all);

        add_button = findViewById(R.id.button);
        test= findViewById(R.id.test);
        DocumentReference getdata = db.collection("Account").document(usernameMain);
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


        // test case
        final String []mood = {"Happy", "Angry", "Sad", "Happy"};
        String []date = {"2019-11-05", "2019-11-04", "2019-11-13","2019-11-20"};
        String []something = {"1", "2", "3","4"};
        Geolocation geolocation1;
        Geolocation geolocation2;
        Geolocation geolocation3;
        Geolocation geolocation4;
        double a = 53.484310;
        double b = -113.506133;
        double c =  53.488036;
        double d =  -113.514072;
        double e = 53.486592;
        double f = -113.502130;
        geolocation1 = new Geolocation(a,b);
        geolocation2 = new Geolocation(c,d);
        geolocation3 = new Geolocation(e,f);
        geolocation4 = null;
        Geolocation[]geolocations = {geolocation1,geolocation2,geolocation3,geolocation4};


        name.setText(usernameMain);
        moodDataList = new ArrayList<>();

        for (int i = 00; i < mood.length; i++) {
            moodDataList.add((new Mood(mood[i],something[i],something[i],something[i], date[i],something[i],something[i],geolocations[i])));
        }
        moodAdapter = new myMoodList(this,moodDataList);
        moodList.setAdapter(moodAdapter);



        //click on map button on home page to show the moods that have locations.

        map_button = findViewById(R.id.map_button);
        map_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this,MyHistotyMoodMap.class); // go to map activity.
                Bundle bundle = new Bundle();

                bundle.putSerializable("mood",moodDataList);                        // put mood Array list into Bundle.
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });


        followed = findViewById(R.id.followed_button);
        followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentf = new Intent(HomePage.this,FriendActivity.class);
                intentf.putExtra("user",usernameMain);
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
                        Geolocation geolocation = moodDataList.get(num).getGeolocation();
                        filterDataList.add(new Mood("Sad", emotionState, reason, time, date, socialState, username, geolocation));
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
                        Geolocation geolocation = moodDataList.get(num).getGeolocation();
                        filterDataList.add(new Mood("Angry", emotionState, reason, time, date, socialState, username,geolocation));
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
                        Geolocation geolocation = moodDataList.get(num).getGeolocation();
                        filterDataList.add(new Mood("Happy", emotionState, reason, time, date, socialState, username,geolocation));
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



        /**** My Profile Button ***/
        myProfileButton = findViewById(R.id.profile_button);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMyProfile = new Intent(HomePage.this,MyProfile.class);
                intentMyProfile.putExtra("usernameMyProfile",usernameMain);
                HomePage.this.startActivity(intentMyProfile);
            }
        });





    }
    public void filter_show(ArrayList<Mood> myDataList){
        moodAdapter = new myMoodList(this, myDataList);
        moodList.setAdapter(moodAdapter);
    }

}
