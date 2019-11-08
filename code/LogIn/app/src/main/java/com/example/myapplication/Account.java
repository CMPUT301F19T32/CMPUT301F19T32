package com.example.myapplication;

import java.util.ArrayList;
import java.util.Arrays;

public class Account {

    private String emoji;
    private ArrayList<Mood> moodHistory;
    private String password;
    private ArrayList requestList;
    private String username;

    public Account() {
    }

    public Account(String emoji, ArrayList<Mood> moodHistory, String password, ArrayList requestList, String username) {
        this.emoji = emoji;
        this.moodHistory = moodHistory;
        this.password = password;
        this.requestList = requestList;
        this.username = username;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public ArrayList<Mood> getMoodHistory() {
        return moodHistory;
    }

    public void setMoodHistory(ArrayList<Mood> moodHistory) {
        this.moodHistory = moodHistory;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList requestList) {
        this.requestList = requestList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

