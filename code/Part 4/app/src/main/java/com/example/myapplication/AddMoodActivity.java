package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.collection.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.grpc.Context;

import static android.location.LocationManager.GPS_PROVIDER;
/**
 * This activity can add new mood history
 */
public class AddMoodActivity extends AppCompatActivity {
    private ImageView image;
    private void initComponent() {
        image = (ImageView) findViewById(R.id.imageView);
    }
    private ImageView img_from_gallary;
    FirebaseFirestore db;

    ImageButton map_bt;
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
    static final int GALLERY_REQUEST_CODE=0;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef ;
    public Uri imguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initComponent();
        Intent intentc = getIntent();
        user = intentc.getStringExtra("user");
        storageRef=FirebaseStorage.getInstance().getReference(user);



//Get GridView in layout
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        img_from_gallary = findViewById(R.id.imageView2);

        gridview.setAdapter(new ImageAdapter(this));
// Set the background
        //gridview.setBackgroundResource(R.color.common_google_signin_btn_text_dark);
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
/**
 * Select moods
 */
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {

                //If user click one emoji in the gridview, then the imageview will show the corresponding emoji.
                if(position==0){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img1));
                    gridview.setVisibility(View.INVISIBLE);
                    emotion = "happy";
                    Toast toast_emotion_happy = Toast.makeText(AddMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast_emotion_happy.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(24);
                    toastTV.setTextColor(Color.BLUE);
                    toast_emotion_happy.show();

                }
                if(position==1){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img2));
                    gridview.setVisibility(View.INVISIBLE);
                    emotion= "angry";
                    Toast toast_emotion_anger = Toast.makeText(AddMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast_emotion_anger.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(24);
                    toastTV.setTextColor(Color.BLUE);
                    toast_emotion_anger.show();
                }
                if(position==2){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img3));
                    gridview.setVisibility(View.INVISIBLE);
                    emotion="sad";
                    Toast toast_emotion_sad = Toast.makeText(AddMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast_emotion_sad.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(24);
                    toastTV.setTextColor(Color.BLUE);
                    toast_emotion_sad.show();
                }


            }
        });
/**
 * Select image
 */
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridview.setVisibility(View.VISIBLE);
            }
        });

        img_from_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Filechooser();
            }
        });


        map_bt = findViewById(R.id.map_bt);
        location_view = findViewById(R.id.mood_location);
/**
 * Click to get the location
 */
        map_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng currentLocation = getCurrentLocation();
                location = new Geolocation(currentLocation.latitude,currentLocation.longitude);
                String s = getAddress(currentLocation) ;
                a=getCurrentLocation().latitude;
                b=getCurrentLocation().longitude;
                location_view.setText(s);
                Toast toast_location = Toast.makeText(AddMoodActivity.this, "Long click to erase the location", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast_location.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(24);
                toastTV.setTextColor(Color.BLUE);
                toast_location.show();


            }
        });


        map_bt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                location_view.setText("Unknown");
                return true;

            }
        });

/**
 * Click reason to enter reason also set limitation on input text. toast if the limitation is meet
 */
        reason=findViewById(R.id.reason);
        //this code is learn from https://stackoverflow.com/questions/28823898/android-how-to-set-maximum-word-limit-on-edittext
        reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String[] strings;


                String string = reason.getText().toString();
                strings = string.split("\\s");


                if(reason.getText().toString().length() == 20){
                    Toast toast_reason_letter = Toast.makeText(AddMoodActivity.this, "Maximum 20 letters", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast_reason_letter.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(24);
                    toastTV.setTextColor(Color.RED);
                    toast_reason_letter.show();
                }
                if (reason.getText().toString().split("\\s").length > 3){
                    reason.setText(strings[0] + " " + strings[1] + " " + strings[2]);
                    reason.setSelection(reason.getText().length());
                    Toast toast_reason = Toast.makeText(AddMoodActivity.this, "Maximum 3 words", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast_reason.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(24);
                    toastTV.setTextColor(Color.RED);
                    toast_reason.show();
                }
            }
        });


/**
 * Click submit to send the data to Firestore and Firebase Storage
 */
        final Intent back = new Intent(this,HomePage.class);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emotion==null){
                    Toast toast_emotion = Toast.makeText(AddMoodActivity.this, "At least enter emotion", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast_emotion.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(24);
                    toastTV.setTextColor(Color.RED);
                    toast_emotion.show();
                }
                else{
                    geolocation = new Geolocation(a,b);
                    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    final Date date = new Date();
                    String add_date=dateFormat.format(date);
                    final Mood moodhistory =new Mood(emotion,reason.getText().toString(),add_date,socialstate,user,Double.toString(a),Double.toString(b));
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    //final CollectionReference collectionReference = db.collection("Account");

                    //final DocumentReference ReceiverRef = db.collection("Account").document(user);
                    db.collection("Account").document(user).collection("moodHistory").document(moodhistory.getTime()).set(moodhistory);

                    //startActivity(back);
                    //overridePendingTransition(0, 0);
                    if (imguri!=null){
                        Fileuploader(add_date);
                    }
                    finish();
                }
            }
        });
    }


    /**
     * Choose picture and get its extension
     */
    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void Fileuploader(String date){
        StorageReference Ref = storageRef.child(date);

        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //Toast.makeText(AddMoodActivity.this,"Image uploaded successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
    private void Filechooser(){
        Intent intent =new Intent();
        intent.setType("image/'");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==1 && requestCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri=data.getData();
            img_from_gallary.setImageURI(imguri);
        }

    }
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri=data.getData();
            img_from_gallary.setImageURI(imguri);
        }
    }

    /**
     * Get the latitude and longitude
     */
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



    }


