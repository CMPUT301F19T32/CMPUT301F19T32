package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity implements AddFriendFrag.OnFragmentInteractionListener{
    ListView moodFriendList;
    ArrayAdapter<Mood> moodFrArrayAdapter;
    ArrayList<Mood> moodFrArrayList;
    private int index;
    private String user;
    private Mood mood;
    private ArrayList<Mood> friendMoodList;
    ArrayList<String> friendList;
    ArrayList<Request> requestsList;
    String toast1 = "Already Friend";
    String toast2 = "Already Sent Earlier";
    String toast3 = "Already Sent";
    private Mood currentMood;
    Geolocation geolocation;
    FirebaseFirestore db;
    FirebaseFirestore cloudstorage;
    Request requestInner;
    Handler mHandler ;
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
        friendList = new ArrayList<>();
        cloudstorage= FirebaseFirestore.getInstance();

        //some sample info (delete later)

        double a = 100;
        double b = 123;
        geolocation = new Geolocation(a,b);
        db = FirebaseFirestore.getInstance();
/**
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,5000);
*/
        /**
         * The following part iterate the friendList of current user in firestore and get the last mood of friend.
         */
        final CollectionReference collectionReference =  cloudstorage.collection("Account").document(user).collection("Friend");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String friend = (String) doc.getId();
                        friendList.add(friend);

                    }
                    for (int i = 0; i<friendList.size();i++){
                        final CollectionReference collectionReferences =  db.collection("Account").document(friendList.get(i)).collection("moodHistory");
                        collectionReferences.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (queryDocumentSnapshots != null) {
                                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                                        System.out.println(friendList.size());
                                        String emotionState = (String) doc.getData().get("emotionState");
                                        String latitude = (String) doc.getData().get("latitude");
                                        String longitude = (String) doc.getData().get("longitude");
                                        String reason = (String) doc.getData().get("reason");
                                        String socialState = (String) doc.getData().get("socialState");
                                        String time = (String) doc.getData().get("time");
                                        String username= (String)doc.getData().get("username");
                                        currentMood=new Mood(emotionState, reason, time, socialState, username, latitude, longitude);
                                        System.out.println(currentMood.getUsername());
                                    }
                                    if(currentMood!=null){
                                        moodFrArrayList.add(currentMood);
                                        moodFrArrayAdapter.notifyDataSetChanged();
                                    }

                                }
                            }
                        });
                    }
                }
            }
        });


        moodFrArrayAdapter = new CustomeFriendList(this, moodFrArrayList);
        moodFriendList.setAdapter(moodFrArrayAdapter);
        Button requestButton = findViewById(R.id.request);
        final Button map = findViewById(R.id.map_fr);
        Button addFriend = findViewById(R.id.addFriend);
        Button refresh=findViewById(R.id.refresh);

        /**
         * refresh button will refresh the activity.
         */
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        /**
         * The add button will call a fragment to ask information for the friend.
         */
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = moodFrArrayList.size() + 1;
                new AddFriendFrag(user).show(getSupportFragmentManager(),"addFriend");

            }
        });
        /**
         * THE MAP BUTTON WILL GO TO ANOTHER ACTIVITY TO SHOW MAP OF FRIEND.
          */
        map.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent mapIntent  = new Intent(FriendActivity.this, FriendMoodMap.class );
                Bundle bundle = new Bundle();
                bundle.putSerializable("mood",moodFrArrayList);
                //Log.e("ff",moodDataList.toString());
                mapIntent.putExtras(bundle);
                startActivity(mapIntent);




            }
        });
        /**
         * THE REQUEST BUTTON WILL GO TO Request Activity to show requests.
         */
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestIntent  = new Intent(FriendActivity.this, RequestActivity.class );
                requestIntent.putExtra("user",user);
                startActivity(requestIntent);
            }
        });

        /**
         * When click the mood of a friend, the detail will show.
         */
        moodFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mood = moodFrArrayAdapter.getItem(position);
                Intent viewIntent  = new Intent(FriendActivity.this, ViewActivity.class );
                viewIntent.putExtra("username",mood.getUsername());
                viewIntent.putExtra("emotion",mood.getEmotionState());
                viewIntent.putExtra("reason",mood.getReason());
                viewIntent.putExtra("social",mood.getSocialState());
                startActivity(viewIntent);
                /// add new activity relatide to homepage
            }
        });

    }

    /**
     * This method is called when user sent a request
     * if the user wants to sent another user that is already friend,
     * or the user already sent request,
     * or there is no match user to  sent. different Toast will appear and the request is ignore.
     * @param request
     */
    @Override
    public void onOkPress(Request request) {

        requestInner = request;
        if(!requestInner.getReciveName().equals("")){
            DocumentReference docIdRef = db.collection("Account").document(requestInner.getReciveName());
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            DocumentReference docIdRef = db.collection("Account").document(requestInner.getReciveName()).collection("Request").document(requestInner.getSentName());
                            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Toast.makeText(FriendActivity.this, "Already sent", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            DocumentReference docIdRef = db.collection("Account").document(requestInner.getReciveName()).collection("Friend").document(requestInner.getSentName());
                                            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            Toast.makeText(FriendActivity.this, "Already friend", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            db.collection("Account").document(requestInner.getReciveName()).collection("Request").document(requestInner.getSentName()).set(requestInner);
                                                        }
                                                    } else {
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(FriendActivity.this, "Can't find this person", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                    }
                }
            });
        }
        else{
            Toast.makeText(FriendActivity.this, "Enter a person please", Toast.LENGTH_SHORT).show();
        }


        //final DocumentReference ReceiverRef = db.collection("Account").document(request.getReciveName()).collection("Request").document(request.getSentName());







    }
    /**
    private  final Runnable m_Runnable = new Runnable() {
        @Override
        public void run() {
            //Toast.makeText(FriendActivity.this,"in runnable",Toast.LENGTH_SHORT).show();

            FriendActivity.this.mHandler.postDelayed(m_Runnable, 5000);
        }
    };

 */

}
