package com.eleonoralion.chat;



import com.eleonoralion.demoDB.DataBase;
import com.eleonoralion.models.Message;
import com.eleonoralion.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

//decoders = MessageDecoder.class,
//        encoders = MessageEncoder.class
//@WebServlet("/chat")
//@ServerEndpoint(value ="/chat/{username}")

@ServerEndpoint(value="/chat/{username}")
public class ChatSocket{

    private Session session;
    private User user;

  //  private static Set<ChatSocket> chatEndpoints = new CopyOnWriteArraySet<>();
  //  private static HashMap<String, String> users = new HashMap<>();

    private static final Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        this.session = session;
        userSessions.add(session);
        user = DataBase.getUserByNickname(username);

        // Загрузка истории сообщений
        for(Message _message : DataBase.messages) {
            System.out.println(_message);

            try {
                session.getAsyncRemote().sendText(new MessageEncoder().encode(_message));
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }

        // Оповещение о подключении нового user в чат
        // Создаем сообщение формата: Nick 12:00:00 message
        Message message_ = new Message("notification", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), (user.getNickname() + " connected"));
        // Сохраняем сообщенние в историю
        DataBase.addMessage(message_);
        // Отправляем всем на сервере
        for(Session _session : userSessions) {
            try {
                _session.getAsyncRemote().sendText(new MessageEncoder().encode(message_));
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }

       /* chatEndpoints.add(this);
s
        users.put(session.getId(), username);

        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        try {
            broadcast(message);
        } catch (EncodeException e) {
            e.printStackTrace();
        }*/
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        // Создаем сообщение формата: Nick 12:00:00 message
        Message message_ = new Message(user.getNickname(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), message);
        message_.setUserImagePath(user.getImagePath());
        // Сохраняем сообщенние в историю
        DataBase.addMessage(message_);
        // Отправляем всем на сервере
        for(Session _session : userSessions) {
            try {
                _session.getAsyncRemote().sendText(new MessageEncoder().encode(message_));
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        userSessions.remove(session);

     /*   chatEndpoints.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        try {
            broadcast(message);
        } catch (EncodeException e) {
            e.printStackTrace();
        }*/
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

   /*private static void broadcast(Message message) throws IOException, EncodeException {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}