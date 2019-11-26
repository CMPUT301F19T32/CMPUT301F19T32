package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.myapplication.Constant.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.myapplication.Constant.PERMISSIONS_REQUEST_ENABLE_GPS;

/**
 * Main Activity
 * This activity is the sign in user interface
 * user sign in of choose sign up from here
 */
public class MainActivity extends AppCompatActivity {
    Button signInButton;
    Button signUpButton;

    EditText usernameEditText;
    EditText passwordEditText;

    String TAG = "sample";
    FirebaseFirestore db;
    private  boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.signIn_button);
        signUpButton= findViewById(R.id.signUp_button);
        usernameEditText = findViewById(R.id.username_field);
        passwordEditText = findViewById(R.id.password_field);

        db = FirebaseFirestore.getInstance();
        final Intent intent = new Intent(this, SignUpActivity.class);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(intent);
            }

        });

        final Intent tohome = new Intent(MainActivity.this,HomePage.class);

        /**
         * sign in
         */
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usernameEditText.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Please enter a name!",Toast.LENGTH_SHORT);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                    return;
                }

                if(passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter a password!",Toast.LENGTH_SHORT).show();
                    return;
                }

                final String  username = usernameEditText.getText().toString();
                final String  password = passwordEditText.getText().toString();
                DocumentReference docRef = db.collection("Account").document(username);

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            StringBuilder fields = new StringBuilder("");
                            fields.append(document.get("password"));
                            String inputpassword=fields.toString();
                            //Toast.makeText(getApplicationContext(), inputpassword, Toast.LENGTH_SHORT).show();
                            if (document.exists()&&inputpassword.equals(password)) {

                                Toast.makeText(getApplicationContext(), "Account Match", Toast.LENGTH_SHORT).show();
                                tohome.putExtra("username",username);
                                startActivity(tohome);
                            }
                            else if (document.exists()&&!inputpassword.equals(password)){
                                Toast.makeText(getApplicationContext(), "Account doesn't match the password", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                Toast.makeText(getApplicationContext(), "Account doesn't exist", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

            }
        });

    }

    /**
     *  Check whether google map service is available
     * @return boolean
     */

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }
    /**
     *  show alert message
     * @return void
     */

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     *  check google map availability
     * @return boolean
     */

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    /**
     *  ask user to get GPS permissions
     * @return void
     */

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     *  check google service
     * @return boolean
     */


    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     *  check whether user enables location
     * @return void
     */



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    /**
     *  check whether user enables GPS
     * @return void
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){

                }
                else{
                    getLocationPermission();
                }
            }
        }

    }

    /**
     *  wait for check
     * @return void
     */

    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
            }
            else{
                getLocationPermission();
            }
        }
    }



}
