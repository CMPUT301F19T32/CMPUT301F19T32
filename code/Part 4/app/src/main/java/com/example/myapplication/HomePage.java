package com.example.myapplication;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *  this is home page class
 */
public class HomePage extends AppCompatActivity implements DeleteMoodFrag.OnFragmentInteractionListener {

    ListView moodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;

    String filter="all";
    LinearLayout filterLayout;
    Button filter_button;
    Button filter_angry;
    Button filter_sad;
    Button filter_happy;
    Button filter_all;
    Button map_button;
    FloatingActionButton add_button;
    FirebaseFirestore db;
    TextView test;
    Button followed;

    Button myProfileButton;
    ArrayList<String> mood ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent tohome = getIntent();
        final String usernameMain = tohome.getStringExtra("username");
        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("Account").document(usernameMain);


        /**
         *  link the variable to the layout variables
         */
        filter_button = findViewById(R.id.filter_button);
        filterLayout = findViewById(R.id.filter_layout);
        filter_angry = findViewById(R.id.filter_angry);
        filter_happy = findViewById(R.id.filter_happy);
        filter_sad = findViewById(R.id.filter_sad);
        filter_all = findViewById(R.id.filter_all);
        add_button = findViewById(R.id.button);


        /**
         *  allow the user to use filter
         *  could filter by moods
         */
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

        /**
         *  when click on map button, go to MyHistotyMoodMap class
         *  allow the user to see mood history on map
         */
        TextView name= findViewById(R.id.uname);
        name.setText(usernameMain);
        map_button = findViewById(R.id.map_button);
        map_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for (int j = 0; j < moodList.getChildCount(); j++) {
                    moodList.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                }


                Intent mapIntent  = new Intent(HomePage.this, MyHistotyMoodMap.class );
                Bundle bundle = new Bundle();
                bundle.putSerializable("mood",moodDataList);
                mapIntent.putExtras(bundle);
                startActivity(mapIntent);


            }
        });

        moodList = findViewById(R.id.mood_list);
        moodDataList = new ArrayList<>();

        moodAdapter = new myMoodList(HomePage.this,moodDataList);
        moodList.setAdapter(moodAdapter);

        /**
         *  when click on the mood, go to edit activity
         *  allow the user to view and edit an exist mood
         */
        final Intent edit = new Intent(HomePage.this, EditMoodActivity.class);
        moodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectTime = moodDataList.get(position).getTime();
                Bundle extras = new Bundle();
                extras.putString("key",selectTime);
                extras.putString("user",usernameMain);
                edit.putExtras(extras);
                startActivity(edit);
                for (int j = 0; j < moodList.getChildCount(); j++) {
                    moodList.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });




        /**
         *  when long click on the mood, would go to delete mood fragment
         *  the fragment would confirm that the user really want to delete a mood
         *  allow the user to delete an exist mood
         */
        moodList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int idx, long arg3) {
                //deleteCurrent(usernameMain,idx);
                new DeleteMoodFrag(usernameMain,idx).show(getSupportFragmentManager(),"addFriend");

                return true;
            }
        });





        /**
         *  when click on the followed button, would go to friend activity
         *  allow the user to see and add friend in the friend activity
         */
        followed = findViewById(R.id.followed_button);
        followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < moodList.getChildCount(); j++) {
                    moodList.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                }
                Intent intentf = new Intent(HomePage.this,FriendActivity.class);
                intentf.putExtra("user",usernameMain);
                HomePage.this.startActivity(intentf);
            }
        });


        /**
         *  when click on the add_mood button, would switch to Add mood Activity
         *  allow the user to add a mood
         */
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < moodList.getChildCount(); j++) {
                    moodList.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                }
                Intent intentc = new Intent(HomePage.this, AddMoodActivity.class);
                intentc.putExtra("user",usernameMain);
                HomePage.this.startActivity(intentc);
                moodList.clearChoices();
            }
        });

        /**
         *  when click on the myprofile button, would switch to my profile Activity
         *  allow the user to set his/her own profile
         */
        myProfileButton = findViewById(R.id.profile_button);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < moodList.getChildCount(); j++) {
                    moodList.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                }
                Intent intentMyProfile = new Intent(HomePage.this, MyProfileActivity.class);
                intentMyProfile.putExtra("usernameMyProfile",usernameMain);
                HomePage.this.startActivity(intentMyProfile);
            }
        });


        /**
         *  when click on the filter_button, some moods button would be visible for user to choose
         */
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterLayout.setVisibility(View.VISIBLE);
                for (int j = 0; j < moodList.getChildCount(); j++) {
                    moodList.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        /**
         *  allow the user to filter the mood history with sad
         */
        filter_sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter="sad";
                final Mood moodhistory =new Mood("","0","","0","0","0","0");
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").set(moodhistory);
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").delete();
                filter_button.setText("SAD");
                filterLayout.setVisibility(View.INVISIBLE);



            }
        });

        /**
         *  allow the user to filter the mood history with angry
         */
        filter_angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filter="angry";
                final Mood moodhistory =new Mood("","0","","0","0","0","0");
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").set(moodhistory);
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").delete();
                filter_button.setText("ANGRY");
                filterLayout.setVisibility(View.INVISIBLE);

            }
        });


        /**
         *  allow the user to filter the mood history with happy
         */
        filter_happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filter="happy";
                final Mood moodhistory =new Mood("","0","","0","0","0","0");
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").set(moodhistory);
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").delete();
                filter_button.setText("HAPPY");
                filterLayout.setVisibility(View.INVISIBLE);

            }
        });


        /**
         *  allow the user to go back to the original mood history list
         */
        filter_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter="all";
                final Mood moodhistory =new Mood("","0","","0","0","0","0");
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").set(moodhistory);
                db.collection("Account").document(usernameMain).collection("moodHistory").document("0").delete();
                filter_button.setText("Filter");
                filterLayout.setVisibility(View.INVISIBLE);

            }
        });

    }


    /**
     *  this method connect to DeleteMoodFrag
     *  the state equal to 1 means the user confirm to delete the mood
     *  do the delete here
     */
    @Override
    public void onYesPressed(Integer state,String username, Integer idx) {
        if (state == 1){
            db.collection("Account").document(username).collection("moodHistory").document(moodDataList.get(idx).getTime())
                    .delete();
        }
    }




}
