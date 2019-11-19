package com.example.myapplication;
/**
 * contain the information of a request.
 */
public class Request {
    private String sentName;
    private String reciveName;
    private String messageSent;

    public Request(String sentName, String reciveName, String messageSent) {
        this.sentName = sentName;
        this.reciveName = reciveName;
        this.messageSent = messageSent;
    }

    public String getSentName() {
        return sentName;
    }

    public void setSentName(String sentName) {
        this.sentName = sentName;
    }

    public String getReciveName() {
        return reciveName;
    }

    public void setReciveName(String reciveName) {
        this.reciveName = reciveName;
    }

    public String getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(String messageSent) {
        this.messageSent = messageSent;
    }
}
