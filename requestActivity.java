package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity implements AgreeDisagreeFrag.OnFragmentInteractionListener{
    private String username;
    ListView requestVeiw;
    ArrayList<Request> requestArrayList;
    ArrayAdapter<Request> requestArrayAdapter;
    Request request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_layout);
        username = getIntent().getStringExtra("user");
        requestVeiw = findViewById(R.id.requestListView);
        requestArrayList = new ArrayList<>();



        /** read request list data from fire store no success yet**/
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Account");
        final DocumentReference OwnRequestRef = db.collection("Account").document(username);

        OwnRequestRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        requestArrayList = (ArrayList<Request>) document.get("requestList");
                    }
                }
            }
        });

        requestArrayAdapter = new CustomRequestList(this,requestArrayList);
        requestVeiw.setAdapter(requestArrayAdapter);


//onclick view list both
        requestVeiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AgreeDisagreeFrag(requestArrayAdapter.getItem(position)).show(getSupportFragmentManager(),"A/D");
            }
        });
    }
    @Override
    public void onAgreePressed(Integer state) {
        // firestore friend both +
    }
}
