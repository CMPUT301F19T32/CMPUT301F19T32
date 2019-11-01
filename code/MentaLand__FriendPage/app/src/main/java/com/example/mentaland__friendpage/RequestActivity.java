package com.example.mentaland__friendpage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
