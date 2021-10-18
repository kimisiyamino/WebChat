package com.eleonoralion.demoDB;

import com.eleonoralion.models.Message;
import com.eleonoralion.models.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    public volatile static List<User> users = new LinkedList<>();
    public volatile static List<Message> messages = new ArrayList<>();

    private static boolean isStart = false;

    static {
        addUser(new User("ADMIN", "https://yandex.ru/images/search?text=админ&from=tabbar&pos=0&img_url=https%3A%2F%2Fsun9-17.userapi.com%2Fimpf%2Fc837120%2Fv837120149%2F65acc%2Flw_Onbb7EgM.jpg%3Fsize%3D604x604%26quality%3D96%26sign%3Da2a167c610fd631fdb87ba48f03e6b05%26type%3Dalbum&rpt=simage"));
    }

    public synchronized static void start(){
        if(!isStart) {
            System.out.println("Database: started");
            isStart = true;
        }
    }

    public synchronized static void addUser(User user){
        users.add(user);
        System.out.println("Database: " + user.getNickname() + " added");
    }

    public synchronized static User getUserByNickname(String nickname){
        for (User user : users){
            if(user.getNickname().equals(nickname)){
                return user;
            }
        }
        return new User("null");
    }

    public synchronized static void addMessage(Message message){
        messages.add(message);
        System.out.println("Database: message added === " +message.getNick() + " " + message.getTime() + " " + message.getMessage() + " " + messages.size());

    }
}
