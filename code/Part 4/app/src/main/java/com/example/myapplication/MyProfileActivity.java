package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;

/**
 * my profile activity
 * user can add an introduction here
 */
public class MyProfileActivity extends AppCompatActivity {
    FirebaseFirestore db;

    TextView usernameProfile;
    String username_profile;
    EditText introductionProfile;
    Button submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        db = FirebaseFirestore.getInstance();
        final Intent intentMyProfile = getIntent();
        username_profile = intentMyProfile.getStringExtra("usernameMyProfile");
        submit=findViewById(R.id.submit);
        usernameProfile = findViewById(R.id.username_myProfile);
        introductionProfile = findViewById(R.id.introduction_field);
        usernameProfile.setText(username_profile);



        DocumentReference docRef = db.collection("Account").document(username_profile).collection("Profile").document("Introduction");
        Source source = Source.CACHE;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String intro =(String) document.getData().get("introduction");
                        introductionProfile.setText(intro);
                    } else {

                    }
                } else {

                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference pro = db.collection("Account").document(username_profile);
                pro.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                final Introduction introduction = new Introduction(introductionProfile.getText().toString());
                                db.collection("Account").document(username_profile).collection("Profile").document("Introduction").set(introduction);
                                Toast toast_my_profile = Toast.makeText(MyProfileActivity.this, "My Profile updated", Toast.LENGTH_SHORT);
                                LinearLayout toastLayout = (LinearLayout) toast_my_profile.getView();
                                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                                toastTV.setTextSize(24);
                                toastTV.setTextColor(Color.BLUE);
                                toast_my_profile.show();
                            } else{
                            }
                        }
                    }
                });
                finish();
            }
        });




    }
}
