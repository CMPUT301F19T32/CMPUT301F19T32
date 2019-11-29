package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity implements AgreeDisagreeFrag.OnFragmentInteractionListener{
    private String username;
    private Integer requestIndex;
    ListView requestVeiw;
    ArrayList<Request> requestArrayList;
    ArrayAdapter<Request> requestArrayAdapter;
    Request request;
    FirebaseFirestore db;
    List<String> friendList = new ArrayList<>();
    List<String> messageList = new ArrayList<>();
    StringBuilder sent = new StringBuilder("");
    StringBuilder rece = new StringBuilder("");
    StringBuilder mess = new StringBuilder("");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_layout);
        username = getIntent().getStringExtra("user");
        requestVeiw = findViewById(R.id.requestListView);
        requestArrayList = new ArrayList<>();
        requestArrayAdapter = new CustomRequestList(this,requestArrayList);
        requestVeiw.setAdapter(requestArrayAdapter);
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReferences =  db.collection("Account").document(username).collection("Request");
        collectionReferences.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException a) {
                if (queryDocumentSnapshots != null) {
                    requestArrayList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String message = (String) doc.getData().get("messageSent");
                        String friendName = (String) doc.getData().get("sentName");
                        String receiveName =(String)doc.getData().get("reciveName");

                        sent.append(friendName);
                        mess.append(message);
                        rece.append(receiveName);

                        System.out.println(message+"\n"+friendName+"\n"+receiveName+"\n");
                        //requestArrayListest.add(new Request(rece.toString(),sent.toString(),mess.toString()));
                        friendList.add(friendName);
                        messageList.add(message);
                        requestArrayList.add(new Request(friendName,receiveName,message));

                    }
                    requestArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        Button refresh=findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

//onclick view list both
        requestVeiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                requestIndex = position;
                request = requestArrayList.get(requestIndex);
                new AgreeDisagreeFrag(requestArrayAdapter.getItem(position)).show(getSupportFragmentManager(),"A/D");
            }
        });


        requestArrayAdapter = new CustomRequestList(this,requestArrayList);
        requestVeiw.setAdapter(requestArrayAdapter);
    }


    /**
     * If agree(1), the friend list of the user who sent request will add the current user.
     * Both agree and disagree will delete the request in firestore.
     * @param state
     */
    @Override
    public void onAgreePressed(Integer state) {
        // remove onClick  request in fireStore.
        System.out.println("*****************");

        System.out.println(request.getSentName());

        System.out.println("*****************");

        if (state == 1){

            //in Firestore, find keyword = request.getSentName(),  keyword user 的 friendList in firestore add  username.
            db.collection("Account").document(request.getSentName()).collection("Friend").document(username).set(request);

        }

        db.collection("Account").document(username).collection("Request").document(request.getSentName())
                .delete();
    }
}
