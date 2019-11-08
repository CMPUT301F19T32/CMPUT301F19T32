package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private TextView textView;
    private LocationManager locationManager;
    private double longitude;
    private double latitude;
    Button followed;
    ArrayList<Request> req;

    Button myProfileButton;
    ArrayList<String> mood ;
    ArrayList<String> date ;
    //Account acc;
    ArrayList<Account> accountList;
    Map<String, Object> m;
    ArrayList<Object> acc;
    Object []mm;
    ArrayList<String>sent;
    ArrayList<String>receive;
    ArrayList<String>message;
    String trr;
    int[] num;
    List<String> emotionList = new ArrayList<>();
    List<String> latitudeList = new ArrayList<>();
    List<String> longitudeList = new ArrayList<>();
    List<String> reasonList = new ArrayList<>();
    List<String> socialList = new ArrayList<>();
    List<String> timeList = new ArrayList<>();

    StringBuilder socialf = new StringBuilder("");
    StringBuilder timef = new StringBuilder("");
    StringBuilder emotionf = new StringBuilder("");
    StringBuilder latitudef = new StringBuilder("");
    StringBuilder longitudef = new StringBuilder("");
    StringBuilder reasonf = new StringBuilder("");

    String filter="all";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent tohome = getIntent();
        final String usernameMain = tohome.getStringExtra("username");
        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("Account").document(usernameMain);



        filter_button = findViewById(R.id.filter_button);
        filterLayout = findViewById(R.id.filter_layout);
        filter_angry = findViewById(R.id.filter_angry);
        filter_happy = findViewById(R.id.filter_happy);
        filter_sad = findViewById(R.id.filter_sad);
        filter_all = findViewById(R.id.filter_all);

        add_button = findViewById(R.id.button);

        final CollectionReference collectionReference =  db.collection("Account").document(usernameMain).collection("moodHistory");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    moodDataList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String emotionState = (String) doc.getData().get("emotionState");
                        String latitude = (String) doc.getData().get("latitude");
                        String longitude = (String) doc.getData().get("longitude");
                        String reason = (String) doc.getData().get("reason");
                        String socialState = (String) doc.getData().get("socialState");
                        String time = (String) doc.getData().get("time");
                        if(filter.equals("angry")){
                            if (emotionState.equals("angry")){
                                moodDataList.add(new Mood(emotionState, reason, time, socialState, usernameMain, latitude, longitude));
                            }
                        }
                        if(filter.equals("sad")){
                            if (emotionState.equals("sad")){
                                moodDataList.add(new Mood(emotionState, reason, time, socialState, usernameMain, latitude, longitude));
                            }
                        }
                        if(filter.equals("happy")){
                            if (emotionState.equals("happy")){
                                moodDataList.add(new Mood(emotionState, reason, time, socialState, usernameMain, latitude, longitude));
                            }
                        }
                        if(filter.equals("all")){
                            moodDataList.add(new Mood(emotionState, reason, time, socialState, usernameMain, latitude, longitude));
                        }

                    }
                    for (int i =0; i < moodDataList.size(); i++){
                        Collections.sort(moodDataList, new Comparator<Mood>() {
                            public int compare(Mood first, Mood second)  {
                                return second.getTime().compareTo(first.getTime());
                            }
                        });
                    }
                    moodAdapter.notifyDataSetChanged();
                }
            }
        });
        TextView name= findViewById(R.id.uname);
        name.setText(usernameMain);
        textView = findViewById(R.id.location);
        map_button = findViewById(R.id.map_button);
        map_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this,MyHistotyMoodMap.class); // go to map activity.
                intent.putExtra("user",usernameMain);
                startActivity(intent);
            }
        });

        moodList = findViewById(R.id.mood_list);
        moodDataList = new ArrayList<>();

        moodAdapter = new myMoodList(HomePage.this,moodDataList);
        moodList.setAdapter(moodAdapter);

        final Intent edit = new Intent(HomePage.this,Edit.class);
        moodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectTime = moodDataList.get(position).getTime();
                Bundle extras = new Bundle();
                extras.putString("key",selectTime);
                extras.putString("user",usernameMain);
                edit.putExtras(extras);
                startActivity(edit);
            }
        });

        moodList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                db.collection("Account").document(usernameMain).collection("moodHistory").document(moodDataList.get(index).getTime())
                        .delete();
                return true;
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
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentc = new Intent(HomePage.this,Create.class);
                intentc.putExtra("user",usernameMain);
                HomePage.this.startActivity(intentc);
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



        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterLayout.setVisibility(View.VISIBLE);
            }
        });

        filter_sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filter="sad";
                final Mood moodhistory =new Mood("0","0","0","0","0","0","0");
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").set(moodhistory);
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").delete();
                filterLayout.setVisibility(View.INVISIBLE);

<<<<<<< HEAD
=======
                for(int num = 0;num < moodDataList.size();num++) {
                    if (moodDataList.get(num).getEmotionState().equals("sad")) {
                        String emotionState = moodDataList.get(num).getEmotionState();
                        String reason = moodDataList.get(num).getReason();
                        String time = moodDataList.get(num).getTime();
                        String socialState = moodDataList.get(num).getSocialState();
                        String username = moodDataList.get(num).getUsername();
                        String latitude = moodDataList.get(num).getLatitude();
                        String longitude = moodDataList.get(num).getLongitude();
                        filterDataList.add(new Mood(emotionState, reason, time, socialState, username, latitude, longitude));
                        chk = 1;
>>>>>>> 9a42cace9c3616fc8643136ec62df1111a60a183


            }
        });


        filter_angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

<<<<<<< HEAD
                filter="angry";
                final Mood moodhistory =new Mood("0","0","0","0","0","0","0");
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").set(moodhistory);
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").delete();
=======
                filterDataList = new ArrayList<>();

                for(int num = 0;num < moodDataList.size();num++) {
                    if (moodDataList.get(num).getEmotionState().equals("angry")) {
                        String emotionState = moodDataList.get(num).getEmotionState();
                        String reason = moodDataList.get(num).getReason();
                        String time = moodDataList.get(num).getTime();
                        String socialState = moodDataList.get(num).getSocialState();
                        String username = moodDataList.get(num).getUsername();
                        String latitude = moodDataList.get(num).getLatitude();
                        String longitude = moodDataList.get(num).getLongitude();
                        filterDataList.add(new Mood(emotionState, reason, time, socialState, username, latitude, longitude));
                        chk = 1;

                    }
                }
>>>>>>> 9a42cace9c3616fc8643136ec62df1111a60a183
                filterLayout.setVisibility(View.INVISIBLE);

            }
        });
        filter_happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

<<<<<<< HEAD
                filter="happy";
                final Mood moodhistory =new Mood("0","0","0","0","0","0","0");
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").set(moodhistory);
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").delete();
=======
                filterDataList = new ArrayList<>();

                for(int num = 0;num < moodDataList.size();num++) {
                    if (moodDataList.get(num).getEmotionState().equals("happy")) {
                        String emotionState = moodDataList.get(num).getEmotionState();
                        String reason = moodDataList.get(num).getReason();
                        String time = moodDataList.get(num).getTime();
                        String socialState = moodDataList.get(num).getSocialState();
                        String username = moodDataList.get(num).getUsername();
                        String latitude = moodDataList.get(num).getLatitude();
                        String longitude = moodDataList.get(num).getLongitude();
                        filterDataList.add(new Mood(emotionState, reason, time, socialState, username, latitude, longitude));
                        chk = 1;

                    }
                }
>>>>>>> 9a42cace9c3616fc8643136ec62df1111a60a183
                filterLayout.setVisibility(View.INVISIBLE);

            }
        });


        filter_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter="all";
                final Mood moodhistory =new Mood("0","0","0","0","0","0","0");
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").set(moodhistory);
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").delete();
                filterLayout.setVisibility(View.INVISIBLE);

            }
        });

<<<<<<< HEAD
    }


=======



    }


    public void filter_show(ArrayList<Mood> myDataList){
        moodAdapter = new myMoodList(this, myDataList);
        moodList.setAdapter(moodAdapter);
    }

>>>>>>> 9a42cace9c3616fc8643136ec62df1111a60a183
}
