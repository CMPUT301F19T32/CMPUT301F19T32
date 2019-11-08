package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class MyHistotyMoodMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Mood> mapMood;
    private LatLngBounds mMapBoundary;
    FirebaseFirestore db;
    String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_histoty_mood_map);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        mapMood= new ArrayList<Mood>();
        final CollectionReference collectionReference =  db.collection("Account").document(user).collection("moodHistory");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                       for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String emotionState = (String) doc.getData().get("emotionState");
                        String latitude = (String) doc.getData().get("latitude");
                        String longitude = (String) doc.getData().get("longitude");
                        String reason = (String) doc.getData().get("reason");
                        String socialState = (String) doc.getData().get("socialState");
                        String time = (String) doc.getData().get("time");
                        mapMood.add(new Mood(emotionState, reason, time, socialState, user, latitude, longitude));
                        System.out.println("*******************");

                       }
                    System.out.println(mapMood.size());
                }
                System.out.println(mapMood.size());

            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int num = 0;num < mapMood.size();num++) {
            Mood mood = mapMood.get(num);
            //System.out.println(mood.getLatitude()+" "+mood.getLongitude());

            // if the mood has location, place a marker on map.
            if (!mood.getLatitude().equals("0.0")&&!mood.getLongitude().equals("0.0")) {
                LatLng latLng = new LatLng(Double.parseDouble(mood.getLatitude()), Double.parseDouble(mood.getLongitude()));
                System.out.println(latLng.toString());


                // if Emotionstr is happy, the color of marker is green, and red for angry, blue for sad.
                if(mood.getEmotionState().equals("happy")) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title(mood.getEmotionState()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }else if (mood.getEmotionState().equals("angry")){
                    mMap.addMarker(new MarkerOptions().position(latLng).title(mood.getEmotionState()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }else if(mood.getEmotionState().equals("sad")){
                    mMap.addMarker(new MarkerOptions().position(latLng).title(mood.getEmotionState()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                }
                // set view to clearly see the map
                //Geolocation geo = new Geolocation(Double.parseDouble(mood.getLatitude()), Double.parseDouble(mood.getLatitude()));
                Geolocation geo = new Geolocation(latLng.latitude,latLng.longitude);
                setCameraView(geo);
            }

        }



    }

    private void setCameraView(Geolocation geolocation){
        double bottomBoundary = geolocation.getLatitude() - 0.1;
        double leftBoundary = geolocation.getLongitude()-0.1;
        double topBoundary = geolocation.getLatitude()+0.1;
        double rightBoundary = geolocation.getLongitude()+0.1;

        mMapBoundary = new LatLngBounds(new LatLng(bottomBoundary,leftBoundary),new LatLng(topBoundary,rightBoundary));


        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary,0));

    }
}
