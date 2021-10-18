package com.eleonoralion.demoDB;

import com.eleonoralion.models.Message;
import com.eleonoralion.models.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    public static List<User> users = new LinkedList<>();

    public static List<Message> messages = new ArrayList<>();

    public static void addUser(User user){
        users.add(user);
        System.out.println("Database: " + user.getNickname() + " added");
    }

    public static User getUserByNickname(String nickname){
        for (User user : users){
            if(user.getNickname().equals(nickname)){
                return user;
            }
        }
        return new User("null");
    }

    public static void addMessage(Message message){
        messages.add(message);
        System.out.println("Database: " + "message added ===" + messages.size());

    }
}
