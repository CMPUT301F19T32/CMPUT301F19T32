package com.example.mentaland__friendpage;

import android.app.Dialog;
import android.content.Context;
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
    public interface OnFragmentInteractionListener {
        void onAgreePressed();
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
    public AgreeDisagreeFrag(Request request){ this.request = request; };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.agree_disagree_layout, null);
    }
}
