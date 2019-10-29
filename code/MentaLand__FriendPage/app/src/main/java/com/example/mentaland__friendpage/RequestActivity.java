package com.example.mentaland__friendpage;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {
    private String username;
    ListView requestVeiw;
    ArrayList<Request> requestArrayList;
    ArrayAdapter<Request> requestArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_layout);
        username = getIntent().getStringExtra("user");

    }

}
