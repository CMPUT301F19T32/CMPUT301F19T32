package com.example.myapplication;

import java.io.Serializable;

public class Mood implements Serializable {
    private String emotionState;
    private String reason;
    private String time;
    private String socialState;
    private String username;
    private String latitude;
    private String longitude;

    public Mood(String emotionState, String reason, String time, String socialState, String username, String latitude, String longitude) {
        this.emotionState = emotionState;
        this.reason = reason;
        this.time = time;
        this.socialState = socialState;
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getEmotionState() {
        return emotionState;
    }

    public void setEmotionState(String emotionState) {
        this.emotionState = emotionState;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSocialState() {
        return socialState;
    }

    public void setSocialState(String socialState) {
        this.socialState = socialState;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
