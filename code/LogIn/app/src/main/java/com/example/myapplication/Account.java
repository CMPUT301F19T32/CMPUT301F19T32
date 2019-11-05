package com.example.myapplication;

import java.util.ArrayList;
import java.util.Arrays;

public class Account {
    private String username;
    private String password;
    private ArrayList moodHistory;
    private String emoji;
    private ArrayList requestList;



    Account(String username, String password, ArrayList moodHistory,String emoji, ArrayList requestList) {
        this.username = username;
        this.password = password;
        this.moodHistory = moodHistory;
        this.emoji=emoji;
        this.requestList = requestList;

    }

    public ArrayList getRequestList() {
        return requestList;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList getMoooHistory() {
        return moodHistory;
    }
    public String getEmoji() {
        return emoji;
    }

}

