package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class myMoodList extends ArrayAdapter<Mood> {

    private ArrayList<Mood> moods;
    private HomePage context;

    public myMoodList(HomePage context, ArrayList<Mood> moods){
        super(context,0, moods);
        this.moods = moods;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

        final Mood mood = moods.get(position);

        TextView mood_text = view.findViewById(R.id.mood_text);
        TextView date_text = view.findViewById(R.id.date_text);

        mood_text.setText(mood.getEmotionState().toUpperCase());
        date_text.setText(mood.getTime());

        if(moods.get(position).getEmotionState().equals("sad")){
            mood_text.setTextColor(Color.BLUE);
            date_text.setTextColor(Color.BLUE);
        }
        if(moods.get(position).getEmotionState().equals("happy")){
            mood_text.setTextColor(Color.rgb(0,125,0));
            date_text.setTextColor(Color.rgb(0,125,0));
        }
        if(moods.get(position).getEmotionState().equals("angry")){
            mood_text.setTextColor(Color.RED);
            date_text.setTextColor(Color.RED);
        }



        return view;

    }
}



