package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

        final Mood mood = moods.get(position);

        TextView mood_text = view.findViewById(R.id.mood_text);
        TextView date_text = view.findViewById(R.id.date_text);

        mood_text.setText(mood.getEmotionState());
        date_text.setText(mood.getTime());

        Collections.sort(moods, new Comparator<Mood>() {
            public int compare(Mood first, Mood second)  {
                return second.getTime().compareTo(first.getTime());
            }
        });

        context.moodAdapter.notifyDataSetChanged();

        return view;

    }
}

/*
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final int pos = position;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        final Mood mood = moods.get(position);

        TextView mood_text = view.findViewById(R.id.mood_text);
        TextView date_text = view.findViewById(R.id.date_text);

        mood_text.setText(mood.getEmotionState());
        date_text.setText(mood.getTime());

        //sort the list by date
        Collections.sort(moods, new Comparator<Mood>() {
            public int compare(Mood first, Mood second)  {
                return second.getTime().compareTo(first.getTime());
            }
        });







        return view;
    }
    */


