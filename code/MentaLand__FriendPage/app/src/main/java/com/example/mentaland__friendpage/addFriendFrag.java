package com.example.mentaland__friendpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class addFriendFrag extends DialogFragment {
    private String userName;
    private EditText userToSent;
    private EditText massage;
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

    public addFriendFrag(String userNamein){this.userName = userNamein;}


    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_friend_fragment,null);
        userToSent = view.findViewById(R.id.UserNameToSent);
        massage = view.findViewById(R.id.MassageToSent);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Search For a Friend")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Sent", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //search in FireStore
                        request.setMassageSent(massage.getText().toString());
                        request.setReciveName(userToSent.getText().toString());
                        request.setSentName(userName);
                        listener.onOkPress(request);
                    }
                }).create();
    }
}
