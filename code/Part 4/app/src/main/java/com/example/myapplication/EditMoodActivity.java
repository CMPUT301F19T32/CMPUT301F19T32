package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import com.google.firebase.storage.UploadTask;

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
    String emotionState ;
    String latitude ;
    String longitude ;
    String reasonS;
    String socialState;
    String time;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef ;
    StorageReference storageReference;
    public Uri imguri;
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
        img_from_gallary = findViewById(R.id.imageView2);
        storageReference=FirebaseStorage.getInstance().getReference(user);
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
                    //StorageReference storageReference = FirebaseStorage.getInstance().getReference("Images").child("123.jpg");

                    storageReference.child(time).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Toast.makeText(EditMoodActivity.this,"Image successfully find",Toast.LENGTH_LONG).show();
                            Uri downloadUrl = uri;
                            String fileUrl = downloadUrl.toString();
                            Glide.with(EditMoodActivity.this /* context */)

                                    .load(fileUrl)
                                    .into(img_from_gallary);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //Toast.makeText(EditMoodActivity.this,"Failed",Toast.LENGTH_LONG).show();
                        }
                    });

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
        //gridview.setBackgroundResource(R.color.common_google_signin_btn_text_dark);

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
                    Toast toast_emotion_happy = Toast.makeText(EditMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT);
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
                    Toast toast_emotion_anger = Toast.makeText(EditMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT);
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
                    Toast toast_emotion_sad = Toast.makeText(EditMoodActivity.this, "Feel "   + emotion, Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast_emotion_sad.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(24);
                    toastTV.setTextColor(Color.BLUE);
                    toast_emotion_sad.show();
                }


            }
        });
        img_from_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Intent intent = new Intent(Intent.ACTION_PICK);
                 *                 intent.setType("image/*");
                 *                 String[] mimeTypes = {"image/jpeg", "image/png"};
                 *                 intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                 *                 // Launching the Intent
                 *                 startActivityForResult(intent,GALLERY_REQUEST_CODE);
                 */

                Filechooser();
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
                a=getCurrentLocation().latitude;
                b=getCurrentLocation().longitude;
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
                Toast toast_submit = Toast.makeText(EditMoodActivity.this, "Mood history has been updated", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast_submit.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(24);
                toastTV.setTextColor(Color.BLUE);
                toast_submit.show();
                db.collection("Account").document(user).collection("moodHistory").document(key)
                        .update(
                                "emotionState", emotion,
                                "socialState", socialstate,
                                "reason",reason.getText().toString(),
                                "latitude",Double.toString(a),
                                "longitude",Double.toString(b)
                        );
                if (imguri!=null){
                    Fileuploader(time);
                }
                finish();

            }
        });



    }

    /**
     *  Use GPS to return current Latitude and Longitude
     * @return LatLng
     */
    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void Fileuploader(String date){
        StorageReference Ref = storageReference.child(date);

        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //Toast.makeText(EditMoodActivity.this,"Image uploaded successfully",Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri=data.getData();
            img_from_gallary.setImageURI(imguri);
        }
    }
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
        if (latLng.latitude==0.0 && latLng.longitude==0.0){
            return "Unknown";
        }
        Geocoder geocoder = new Geocoder(EditMoodActivity.this, Locale.getDefault());
        try{
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);

            address = addresses.get(0).getThoroughfare() + ",\t" + addresses.get(0).getLocality();


        }catch (IOException e){
            e.printStackTrace();
        }


        return address;
    }




}
