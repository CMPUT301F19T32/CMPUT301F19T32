package com.example.mentaland_sortlist;

public class Mood {
    private String emotionstr;
    private String emotionState;
    private String reason;
    private String time;
    private  String date;
    private String socialState;
    private String username;
    private  Geolocation geolocation;
    public Mood(String emotionstr, String emotionState, String reason, String time, String date, String socialState, String username) {
        this.emotionstr = emotionstr;
        this.emotionState = emotionState;
        this.reason = reason;
        this.time = time;
        this.date = date;
        this.socialState = socialState;
        this.username = username;
    }

    public String getEmotionstr() {
        return emotionstr;
    }

    public String getEmotionState() {
        return emotionState;
    }

    public String getReason() {
        return reason;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getSocialState() {
        return socialState;
    }

    public String getUsername() {
        return username;
    }

    public void setEmotionstr(String emotionstr) {
        this.emotionstr = emotionstr;
    }

    public void setEmotionState(String emotionState) {
        this.emotionState = emotionState;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSocialState(String socialState) {
        this.socialState = socialState;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
