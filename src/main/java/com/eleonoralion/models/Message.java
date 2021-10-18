package com.eleonoralion.models;

public class Message {
    private String nick;
    private String time;
    private String message;

    private String userImagePath;

    public Message() {
    }

    public Message(String nick, String time, String message) {
        this.nick = nick;
        this.time = time;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    @Override
    public String toString() {
        return "Message{" +
                "nick='" + nick + '\'' +
                ", time='" + time + '\'' +
                ", message='" + message + '\'' +
                ", userImagePath='" + userImagePath + '\'' +
                '}';
    }
}