package com.example.mentaland_sortlist;

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
    private MainActivity context;

    public myMoodList(MainActivity context, ArrayList<Mood> moods) {
        super(context,0,moods);
        this.moods = moods;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final int pos = position;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        Mood mood = moods.get(position);

        TextView mood_text = view.findViewById(R.id.mood_text);
        TextView date_text = view.findViewById(R.id.date_text);

        mood_text.setText(mood.getEmotionstr());
        date_text.setText(mood.getEmotionstr());

        context.delete_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.moodDataList.remove(pos);
                context.moodAdapter.notifyDataSetChanged();
                context.moodList.setAdapter(context.moodAdapter);
        }
        });

        return view;
    }
}
