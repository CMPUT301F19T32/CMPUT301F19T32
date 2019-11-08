package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    ArrayList<Account> accountDataList;


    String TAG = "sample";
    EditText usernameCreate;
    EditText passwordCreate;
    EditText passwordConfirm;
    Button signUpButton2;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameCreate = findViewById(R.id.username_field);
        passwordCreate = findViewById(R.id.password_field);
        passwordConfirm = findViewById(R.id.passwordConfirm_field);
        signUpButton2 = findViewById(R.id.signUp_button2);

        accountDataList = new ArrayList<>();


        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Account");

        final Intent backToMainIntent = new Intent(this, MainActivity.class);
        signUpButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {


                if(usernameCreate.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter a name!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(passwordCreate.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter a password!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!passwordConfirm.getText().toString().equals(passwordCreate.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Two password dont match, please check your password!",Toast.LENGTH_SHORT).show();
                    return;
                }

                final DocumentReference SignUpRef = db.collection("Account").document(usernameCreate.getText().toString());
                SignUpRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                Toast.makeText(getApplicationContext(),"The username already exists, please enter a different name!",Toast.LENGTH_SHORT).show();
                            }

                            else{
                                final String  username = usernameCreate.getText().toString();
                                final String password = passwordConfirm.getText().toString();

                                Account account = new Account("",new ArrayList<Mood>(),password,new ArrayList<Request>(),username);
                                db.collection("Account").document(username).set(account);

                                Toast.makeText(getApplicationContext(),"Succeffully signed up",Toast.LENGTH_SHORT).show();
                                // back to login page
                                startActivity(backToMainIntent);
                            }
                        }

                    }
                });


            }
        });

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                accountDataList.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("password")));
                    String username = doc.getId();
                    String password = (String) doc.getData().get("password");
                    ArrayList moodHistory = (ArrayList) doc.getData().get("moodHistory");
                    String emoji = (String) doc.getData().get("emoji");
                    ArrayList requestList = (ArrayList) doc.getData().get("moodHistory");
                    accountDataList.add(new Account(emoji,moodHistory,password,requestList,username));
                }

                //accountAdapter.notifyDataSetChanged();
            }
        });


    }
}
