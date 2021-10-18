package com.eleonoralion.models;

public class Message {
    private String from;
    private String to;

    public String message;
    public String time;
    public String nick;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    //standard constructors, getters, setters
}