package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class FriendMoodMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Mood> mapMood;
    private LatLngBounds mMapBoundary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_mood_map);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mapMood = (ArrayList<Mood>) bundle.getSerializable("mood");


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

        for (int num = 0; num < mapMood.size(); num++) {
            Mood mood = mapMood.get(num);
            Log.i("ff", mood.toString());
            if (mood.getGeolocation() != null) {
                LatLng latLng = new LatLng(mood.getGeolocation().getLatitude(), mood.getGeolocation().getLongitude());

                if(mood.getEmotionstr().equals("Happy")) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title(mood.getUsername() + ":" + mood.getEmotionstr()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                }else if (mood.getEmotionstr().equals("Angry")){
                    mMap.addMarker(new MarkerOptions().position(latLng).title(mood.getUsername() + ":" + mood.getEmotionstr()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }else if(mood.getEmotionstr().equals("Sad")){
                    mMap.addMarker(new MarkerOptions().position(latLng).title(mood.getUsername() + ":" + mood.getEmotionstr()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }
                setCameraView(mood.getGeolocation());
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