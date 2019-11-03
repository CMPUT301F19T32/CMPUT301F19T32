package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends MainActivity implements OnMapReadyCallback {
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Marker currentMarker;
    public static Geolocation location;
    private List<Geolocation> mapList;
    private String mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        LatLng startingMarker;
        if (location == null) {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[1];
                permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
                ActivityCompat.requestPermissions(this, permissions, MY_LOCATION_REQUEST_CODE);
                return;
            }
            mMap.setMyLocationEnabled(true);
            startingMarker = getCurrentLocation();
            location = new Geolocation(startingMarker.latitude, startingMarker.longitude);
        } else {
            startingMarker = new LatLng(location.getLatitude(), location.getLongitude());

        }
        currentMarker = mMap.addMarker(new MarkerOptions().position(startingMarker).title("Record Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startingMarker));

    }

    private LatLng getCurrentLocation() {
        LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return new LatLng(0,0);
        }

        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            return new LatLng(0,0);
        }
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();
        return new LatLng(latitude, longitude);
    }

}