package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddFriendFrag extends DialogFragment {
    private String userName;
    private EditText userToSent;
    private EditText massageIn;
    private OnFragmentInteractionListener listener;
    private Request request;

    public interface OnFragmentInteractionListener {
        void onOkPress(Request request);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public AddFriendFrag(String userNamein){this.userName = userNamein;}


    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_friend_fragment,null);
        userToSent = view.findViewById(R.id.UserNameToSent);
        massageIn = view.findViewById(R.id.MassageToSent);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Search For a Friend")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Sent", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String massage  =  massageIn.getText().toString();
                        String formWhom = userName;
                        String toWhom = userToSent.getText().toString();
                        final Request requestIn = new Request(formWhom,toWhom,massage);

                        /** firestore request list **/
                        /*** store request to firebase ***/
                        FirebaseFirestore db;
                        db = FirebaseFirestore.getInstance();
                        final CollectionReference collectionReference = db.collection("Account");

                        final DocumentReference ReceiverRef = db.collection("Account").document(toWhom);
                        ReceiverRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        ReceiverRef
                                                .update("requestList", FieldValue.arrayUnion(requestIn));
                                        //Toast.makeText(getActivity(), "request sent successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //Log.d(TAG, "The user does not exist!");

                                        Toast.makeText(getActivity(), "The user does not exist!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                /** else {
                                 Log.d(TAG, "Failed with: ", task.getException());
                                 } **/

                            }
                        });

                        listener.onOkPress(requestIn);
                    }
                }).create();
    }
}
