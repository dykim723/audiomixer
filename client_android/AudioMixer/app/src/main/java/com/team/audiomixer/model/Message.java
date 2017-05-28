package com.team.audiomixer.model;

/**
 * Created by dykim on 2017-05-28.
 */

public class Message {
    private User to;
    private String sentDate;
    private String readDate;
    private String text;

    public Message(User to, String text) {
        this.to = to;
        this.text = text;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
