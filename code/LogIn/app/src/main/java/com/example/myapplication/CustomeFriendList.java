package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * This will be the mood custom List in FriendActivity that will shown.
 */
public class CustomeFriendList extends ArrayAdapter<Mood> {
    private ArrayList<Mood> moods;
    private Context context;

    public CustomeFriendList(Context context, ArrayList<Mood> moods) {
        super(context, 0, moods);
        this.moods = moods;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.fr_mood_item,parent,false);
        }

        Mood mood = moods.get(position);

        TextView userName = view.findViewById(R.id.UserName_fr);
        TextView moodText = view.findViewById(R.id.moodtext_fr);
        TextView timeText = view.findViewById(R.id.time_fr);

        userName.setText(mood.getUsername());
        timeText.setText(mood.getTime());
        moodText.setText(mood.getEmotionState());

        if(moods.get(position).getEmotionState().equals("sad")){
            timeText.setTextColor(Color.BLUE);
            moodText.setTextColor(Color.BLUE);
            userName.setTextColor(Color.BLUE);
        }
        if(moods.get(position).getEmotionState().equals("happy")){
            timeText.setTextColor(Color.GREEN);
            moodText.setTextColor(Color.GREEN);
            userName.setTextColor(Color.GREEN);
        }
        if(moods.get(position).getEmotionState().equals("angry")){
            timeText.setTextColor(Color.RED);
            moodText.setTextColor(Color.RED);
            userName.setTextColor(Color.RED);
        }

        return view;
    }
}