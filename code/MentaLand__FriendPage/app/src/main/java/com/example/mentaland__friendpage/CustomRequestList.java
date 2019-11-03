package com.example.mentaland__friendpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomRequestList extends ArrayAdapter<Request> {
    private ArrayList<Request> requests;
    private Context context;

    public CustomRequestList(Context context, ArrayList<Request> requests){
        super(context,0,requests);
        this.requests = requests;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.fr_mood_item,parent,false);
        }
        Request request = requests.get(position);
        TextView friendName = view.findViewById(R.id.requestViewFriendText);
        friendName.setText(request.getSentName() + "has sent a request");

        return view;
    }
}
