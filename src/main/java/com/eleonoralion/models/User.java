package com.eleonoralion.models;

import java.util.Objects;

public class User {
    private String nickname;
    private String imagePath;

    public User() {
        this.nickname = "default";
        imagePath = "https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg";
    }

    public User(String nickname){
        this.nickname = nickname;
        imagePath = "https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg";
    }

    public User(String nickname, String imagePath) {
        this.nickname = nickname;
        this.imagePath = imagePath;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return nickname.equals(user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }
}
