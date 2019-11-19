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
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AgreeDisagreeFrag extends DialogFragment {
    private TextView fromUser;
    private TextView massage;
    private OnFragmentInteractionListener listener;
    private Request request;
    /**
     * contains the onAgreePressed method
     */
    public interface OnFragmentInteractionListener {
        void onAgreePressed(Integer state);
    }

    /**
     *  Make sure the OnFragmentInteractionListener implement
     * @param context
     */
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
    public AgreeDisagreeFrag(Request request){ this.request = request; };

    /**
     * create the fragment
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.agree_disagree_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        fromUser = view.findViewById(R.id.FromUser);
        massage = view.findViewById(R.id.MasssageGet);
        fromUser.setText(request.getSentName());
        massage.setText(request.getMessageSent());
        return builder
                .setView(view)
                .setTitle("Request")
                .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onAgreePressed(0);
                    }
                })
                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onAgreePressed(1);
                    }
                }).create();
    }
}
