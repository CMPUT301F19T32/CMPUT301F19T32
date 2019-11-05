package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class signUpPage extends AppCompatActivity {

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
        setContentView(R.layout.activity_sign_up_page);

        usernameCreate = findViewById(R.id.username_field);
        passwordCreate = findViewById(R.id.password_field);
        passwordConfirm = findViewById(R.id.passwordConfirm_field);
        signUpButton2 = findViewById(R.id.signUp_button2);

        accountDataList = new ArrayList<>();


        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Account");

        signUpButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final String  username = usernameCreate.getText().toString();
                final String password = passwordConfirm.getText().toString();

                Account account = new Account(username,password,new ArrayList(),"",new ArrayList());
                db.collection("Account").document(username).set(account);

    /*
                HashMap<String, Object> data = new HashMap<>();

                if (username.length()>0 && password.length()>0){
                    data.put("password_text",password);



                    collectionReference
                            .document(username)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "dataaddition successful");
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Data addition failed" + e.toString());


                                }
                            });

                    usernameCreate.setText("");
                    passwordConfirm.setText("");

     */
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
                    accountDataList.add(new Account(username,password,moodHistory,emoji,requestList));
                }

                //accountAdapter.notifyDataSetChanged();
            }
        });


    }
}
