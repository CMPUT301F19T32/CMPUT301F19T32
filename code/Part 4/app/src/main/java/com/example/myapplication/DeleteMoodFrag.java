package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * this is the delete mood fragment
 * to confirm that the user really want to delete an exist mood
 */
public class DeleteMoodFrag extends DialogFragment {
    private String userNameMain;
    private TextView delete;
    private int idx;
    private OnFragmentInteractionListener listener;
    private HomePage home;

    /**
     * this is constructor
     */
    public DeleteMoodFrag(String usernameMain, int idx) {
        this.userNameMain = usernameMain;
        this.idx = idx;
    }

    /**
     * contains the onYesPress method
     */
    public interface OnFragmentInteractionListener {
        void onYesPressed(Integer state,String username,Integer idx);
    }


    /**
     *  Make sure the OnFragmentInteractionListener implement
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DeleteMoodFrag.OnFragmentInteractionListener) {
            listener = (DeleteMoodFrag.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }




    /**
     * create the fragment
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.delete_mood_frag,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("delete mood")
                .setMessage("Are you sure you want to delete the selected mood?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onYesPressed(0,userNameMain,idx);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onYesPressed(1,userNameMain,idx);
                    }
                }).create();
    }
}
