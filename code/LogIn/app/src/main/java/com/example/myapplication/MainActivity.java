package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button signInButton;
    Button signUpButton;

    EditText usernameEditText;
    EditText passwordEditText;

    String TAG = "sample";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.signIn_button);
        signUpButton= findViewById(R.id.signUp_button);
        usernameEditText = findViewById(R.id.username_field);
        passwordEditText = findViewById(R.id.password_field);

        db = FirebaseFirestore.getInstance();
        final Intent intent = new Intent(this, signUpPage.class);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(intent);
            }

        });

        final Intent tohome = new Intent(MainActivity.this,HomePage.class);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                            Toast.makeText(getApplicationContext(), inputpassword, Toast.LENGTH_SHORT).show();
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
}
