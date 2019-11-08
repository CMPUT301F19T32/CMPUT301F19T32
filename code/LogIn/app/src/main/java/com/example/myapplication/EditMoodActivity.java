package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.GPS_PROVIDER;

public class EditMoodActivity extends AppCompatActivity {
    private ImageView image;
    private void initComponent() {
        image = (ImageView) findViewById(R.id.imageView);
    }
    FirebaseFirestore db;
    Button map_bt;
    TextView location_view;
    Geolocation location;
    String emotion;
    String socialstate;
    Button submit;
    EditText reason;
    Geolocation geolocation;
    double a;
    double b;
    private String user;
    String emotionState ;
    String latitude ;
    String longitude ;
    String reasonS;
    String socialState;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initComponent();
        Intent edit = getIntent();
        Bundle extras = edit.getExtras();
        final String key = extras.getString("key");
        user = extras.getString("user");
        db = FirebaseFirestore.getInstance();
        final Spinner social = (Spinner) findViewById(R.id.social);

        DocumentReference docRef = db.collection("Account").document(user).collection("moodHistory").document(key);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    emotion = (String) doc.getData().get("emotionState");
                    latitude = (String) doc.getData().get("latitude");
                    longitude = (String) doc.getData().get("longitude");
                    reasonS = (String) doc.getData().get("reason");
                    socialstate = (String) doc.getData().get("socialState");
                    time = (String) doc.getData().get("time");
                    reason.setText(reasonS);
                    if(emotion.equals("happy")){
                        image.setImageDrawable( getResources().getDrawable(R.drawable.img1));
                    }
                    if(emotion.equals("angry")){
                        image.setImageDrawable( getResources().getDrawable(R.drawable.img2));
                    }
                    if(emotion.equals("sad")){
                        image.setImageDrawable( getResources().getDrawable(R.drawable.img3));
                    }
                    if(socialstate.equals("Alone")){
                        social.setSelection(0);
                    }
                    if(socialstate.equals("With one person")){
                        social.setSelection(1);
                    }
                    if(socialstate.equals("With two to several people")){
                        social.setSelection(2);
                    }
                    if(socialstate.equals("With a crowd")){
                        social.setSelection(3);
                    }
                    if (latitude.equals("0.0")&&longitude.equals("0.0")){
                        location_view.setText("Unknown");}else{
                        LatLng originLatLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                        String originLocation = getAddress(originLatLng);
                        location_view.setText(originLocation);}
                }
                else {
                }
            }

        });

//Get GridView in layout
        final GridView gridview = (GridView) findViewById(R.id.gridview);

        gridview.setAdapter(new ImageAdapter(this));
// Set the background
        gridview.setBackgroundResource(R.color.common_google_signin_btn_text_dark);

        social.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if(pos==0){
                    socialstate="Alone";
                }
                if(pos==1){
                    socialstate="With one person";
                }
                if(pos==2){
                    socialstate="With two to several people";
                }
                if(pos==3){
                    socialstate="With a crowd";
                }
                String[] languages = getResources().getStringArray(R.array.social_states);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {

                //If user click one emoji in the gridview, then the imageview will show the corresponding emoji.
                if(position==0){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img1));
                    gridview.setVisibility(View.INVISIBLE);
                    emotion = "happy";
                    Toast.makeText(EditMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT).show();

                }
                if(position==1){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img2));
                    gridview.setVisibility(View.INVISIBLE);
                    emotion= "angry";
                    Toast.makeText(EditMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT).show();
                }
                if(position==2){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img3));
                    gridview.setVisibility(View.INVISIBLE);
                    emotion="sad";
                    Toast.makeText(EditMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT).show();
                }


            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridview.setVisibility(View.VISIBLE);
            }
        });


        map_bt = findViewById(R.id.map_bt);
        location_view = findViewById(R.id.mood_location);


        map_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng currentLocation = getCurrentLocation();
                location = new Geolocation(currentLocation.latitude,currentLocation.longitude);
                String s = getAddress(currentLocation);
                location_view.setText(s);




            }
        });
        reason=findViewById(R.id.reason);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date date = new Date();


        final Intent back = new Intent(this,HomePage.class);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditMoodActivity.this, emotion, Toast.LENGTH_SHORT).show();
                a=getCurrentLocation().latitude;
                b=getCurrentLocation().longitude;
                db.collection("Account").document(user).collection("moodHistory").document(key)
                        .update(
                                "emotionState", emotion,
                                "socialState", socialstate,
                                "reason",reason.getText().toString(),
                                "latitude",Double.toString(getCurrentLocation().latitude),
                                "longitude",Double.toString(getCurrentLocation().longitude)
                        );
                finish();

            }
        });



    }

    /**
     *  Use GPS to return current Latitude and Longitude
     * @return LatLng
     */

    public LatLng getCurrentLocation() {
        LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return new LatLng(0,0);
        }
        final Location myLocation = lm.getLastKnownLocation(GPS_PROVIDER);
        lm.requestLocationUpdates(GPS_PROVIDER, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLocation.setLatitude(location.getLatitude());
                myLocation.setLongitude(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });




        if (myLocation == null) {
            return new LatLng(0,0);
        }
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();
        return new LatLng(latitude, longitude);
    }

    /**
     *  Use latitude and longitude to get address
     * @param latLng
     * @return String
     */


    public String getAddress(LatLng latLng){
        String address = "";
        Geocoder geocoder = new Geocoder(EditMoodActivity.this, Locale.getDefault());
        try{
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            //String address_1 = addresses.get(0).getAddressLine(0);
            address = addresses.get(0).getThoroughfare()+",\t"+addresses.get(0).getLocality();


        }catch (IOException e){
            e.printStackTrace();
        }


        return address;
    }




}
