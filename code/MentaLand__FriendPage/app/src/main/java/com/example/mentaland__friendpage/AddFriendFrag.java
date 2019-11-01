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
                        //search in FireStore
                        //

                        //
                        String massage  =  massageIn.getText().toString();
                        String formWhom = userName;
                        String toWhom = userToSent.getText().toString();
                        Request requestIn = new Request(formWhom,toWhom,massage);
                        listener.onOkPress(requestIn);
                    }
                }).create();
    }
}
