package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * This Activity shows the details of the friend mood that is selected
 */
public class ViewActivity extends AppCompatActivity {
    public TextView view_username;
    public TextView view_emotion;
    public TextView view_reason;
    public TextView view_social;
    public TextView view_location;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef ;
    FirebaseFirestore db;
    StorageReference storageReference;
    public Uri imguri;
    String latitude;
    String longitude;
    String time;
    String user;
    private ImageView image;
    private ImageView reason_image;
    private void initComponent() {
        image = (ImageView) findViewById(R.id.imageView);
        reason_image = (ImageView) findViewById(R.id.imageView2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mood);
        initComponent();
        view_username = findViewById(R.id.view_username);
        view_emotion = findViewById(R.id.view_emotion);
        view_reason = findViewById(R.id.view_reason);
        view_social = findViewById(R.id.view_social);
        view_location = findViewById(R.id.location);


        view_username.setText(getIntent().getStringExtra("username"));
        view_emotion.setText(getIntent().getStringExtra("emotion"));
        view_reason.setText(getIntent().getStringExtra("reason"));
        view_social.setText(getIntent().getStringExtra("social"));
        latitude=getIntent().getStringExtra("latitude");
        longitude=getIntent().getStringExtra("longitude");
        time=getIntent().getStringExtra("time");
        user = getIntent().getStringExtra("username");
        if(getIntent().getStringExtra("emotion").equals("happy")){
            image.setImageDrawable( getResources().getDrawable(R.drawable.img1));
        }
        if(getIntent().getStringExtra("emotion").equals("angry")){
            image.setImageDrawable( getResources().getDrawable(R.drawable.img2));
        }
        if(getIntent().getStringExtra("emotion").equals("sad")){
            image.setImageDrawable( getResources().getDrawable(R.drawable.img3));
        }
        db = FirebaseFirestore.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference(user);
        Source source = Source.CACHE;
        DocumentReference docRef = db.collection("Account").document(user).collection("moodHistory").document(time);
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    time = (String) doc.getData().get("time");
                    //StorageReference storageReference = FirebaseStorage.getInstance().getReference("Images").child("123.jpg");

                    storageReference.child(time).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Toast.makeText(EditMoodActivity.this,"Image successfully find",Toast.LENGTH_LONG).show();
                            Uri downloadUrl = uri;
                            String fileUrl = downloadUrl.toString();
                            Glide.with(ViewActivity.this /* context */)

                                    .load(fileUrl)
                                    .into(reason_image);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //Toast.makeText(EditMoodActivity.this,"Failed",Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });

        }

}