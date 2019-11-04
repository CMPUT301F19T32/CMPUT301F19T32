package com.example.myapplication;

public class Request {
    private String sentName;
    private String reciveName;
    private String massageSent;

    public Request(String userName, String friendName, String massageSent) {
        this.sentName = userName;
        this.reciveName = friendName;
        this.massageSent = massageSent;
    }

    public String getSentName() {
        return sentName;
    }

    public String getReciveName() {
        return reciveName;
    }

    public String getMassageSent() {
        return massageSent;
    }

    public void setSentName(String sentName) {
        this.sentName = sentName;
    }

    public void setReciveName(String reciveName) {
        this.reciveName = reciveName;
    }

    public void setMassageSent(String massageSent) {
        this.massageSent = massageSent;
    }
}
