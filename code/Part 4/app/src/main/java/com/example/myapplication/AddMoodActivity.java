package com.example.myapplication;

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
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.GPS_PROVIDER;

public class AddMoodActivity extends AppCompatActivity {
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





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initComponent();
        Intent intentc = getIntent();
        user = intentc.getStringExtra("user");


//Get GridView in layout
        final GridView gridview = (GridView) findViewById(R.id.gridview);

        gridview.setAdapter(new ImageAdapter(this));
// Set the background
        gridview.setBackgroundResource(R.color.common_google_signin_btn_text_dark);
        Spinner social = (Spinner) findViewById(R.id.social);
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
                    Toast.makeText(AddMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT).show();

                }
                if(position==1){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img2));
                    gridview.setVisibility(View.INVISIBLE);
                    emotion= "angry";
                    Toast.makeText(AddMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT).show();
                }
                if(position==2){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img3));
                    gridview.setVisibility(View.INVISIBLE);
                    emotion="sad";
                    Toast.makeText(AddMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT).show();
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
                String s = getAddress(currentLocation) ;
                a=getCurrentLocation().latitude;
                b=getCurrentLocation().longitude;
                location_view.setText(s);


            }
        });
        reason=findViewById(R.id.reason);
        //this code is learn from https://stackoverflow.com/questions/28823898/android-how-to-set-maximum-word-limit-on-edittext
        reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int wordsLength = countWords(s.toString());// words.length;
                // count == 0 means a new word is going to start
                if (count == 0 && wordsLength >= 2) {
                    setCharLimit(reason, reason.getText().length());
                } else {
                    removeFilter(reason);
                }

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date date = new Date();


        final Intent back = new Intent(this,HomePage.class);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emotion==null){
                    Toast.makeText(AddMoodActivity.this, "At least enter emotion", Toast.LENGTH_SHORT).show();
                }
                else{


                    geolocation = new Geolocation(a,b);
                    final Mood moodhistory =new Mood(emotion,reason.getText().toString(),dateFormat.format(date),socialstate,user,Double.toString(a),Double.toString(b));
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    //final CollectionReference collectionReference = db.collection("Account");

                    //final DocumentReference ReceiverRef = db.collection("Account").document(user);
                    db.collection("Account").document(user).collection("moodHistory").document(moodhistory.getTime()).set(moodhistory);
                    //startActivity(back);
                    //overridePendingTransition(0, 0);
                    finish();

                }

            }
        });



    }
    public LatLng getCurrentLocation() {
        LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return new LatLng(0,0);
        }

        lm.requestLocationUpdates(GPS_PROVIDER, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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

        Location myLocation = lm.getLastKnownLocation(GPS_PROVIDER);


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
        if (latLng.latitude==0.0 && latLng.longitude==0.0){
            return "Unknown";
        }
        Geocoder geocoder = new Geocoder(AddMoodActivity.this, Locale.getDefault());
        try{
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);

            address = addresses.get(0).getThoroughfare() + ",\t" + addresses.get(0).getLocality();


        }catch (IOException e){
            e.printStackTrace();
        }


        return address;
    }
    private int countWords(String s) {
        String trim = s.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length; // separate string around spaces
    }

    private InputFilter filter;

    private void setCharLimit(EditText et, int max) {
        filter = new InputFilter.LengthFilter(max);
        et.setFilters(new InputFilter[] { filter });
    }

    private void removeFilter(EditText et) {
        if (filter != null) {
            et.setFilters(new InputFilter[0]);
            filter = null;
        }
    }




}
