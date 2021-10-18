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
    User user;

    private static Set<ChatSocket> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        System.out.println("onOpen");
        userSessions.add(session);
        this.session = session;

        user = DataBase.getUserByNickname(username);


        String send = "";

       /* for(Message m : DataBase.messages) {

            System.out.println("сУУУУКАААА " + DataBase.messages.size() + " " + m.message);

            if (m.nick.equals(username)) {
                send = "<div class=\"d-flex justify-content-end mb-4\"><div class=\"msg_cotainer_send\">" + m.message + "<span class=\"msg_time_send\">" + m.time + "</br>" + m.nick + "</span></div><div class=\"img_cont_msg\"><img src=\"https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg\" class=\"rounded-circle user_img_msg\"></div></div>";
            } else {
                send = "<div class=\"d-flex justify-content-start mb-4\"><div class=\"img_cont_msg\"><img src=\"https://static.turbosquid.com/Preview/001214/650/2V/boy-cartoon-3D-model_D.jpg\" class=\"rounded-circle user_img_msg\" alt=\"\"></div><div class=\"msg_cotainer\">" + m.message + "<span class=\"msg_time\">" + m.time + "</br>" + m.nick + "</span></div></div>";
            }

            session.getAsyncRemote().sendText(send);

        }*/

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
    public void onMessage(Session session, String message) throws IOException {
        //System.out.println(session.getId() + " зашел в onMessage");
        //System.out.println(session.getId() + " прислал сообщение: " + message);

        String send = "";



        Message message_ = new Message();
        message_.message = message;
        message_.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        message_.nick =  user.getNickname();
        DataBase.addMessage(message_);

        for(Session _session : userSessions) {
            if(this.session.equals(_session)){
                send = "<div class=\"d-flex justify-content-end mb-4\"><div class=\"msg_cotainer_send\">" + message + "<span class=\"msg_time_send\">" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "</br>" + user.getNickname() + "</span></div><div class=\"img_cont_msg\"><img src=\"https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg\" class=\"rounded-circle user_img_msg\"></div></div>";
            }else{
                send = "<div class=\"d-flex justify-content-start mb-4\"><div class=\"img_cont_msg\"><img src=\"https://static.turbosquid.com/Preview/001214/650/2V/boy-cartoon-3D-model_D.jpg\" class=\"rounded-circle user_img_msg\" alt=\"\"></div><div class=\"msg_cotainer\">" + message + "<span class=\"msg_time\">" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "</br>" + user.getNickname() + "</span></div></div>";
            }

            System.out.println(session.getId() + " отправляет " + _session.getId() + " sms: " + send);

            _session.getAsyncRemote().sendText(send);
        }
     //   message.setFrom(users.get(session.getId()));

      /*  try {
            broadcast(message);
        } catch (EncodeException e) {
            e.printStackTrace();
        }*/
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("onClose");

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


  //  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Servlet: \"" + getClass().getSimpleName() + "\"\nURL: " + request.getRequestURI() + "\nTime: " + LocalDateTime.now() + "\nlocal/remote/localName: " + request.getLocalAddr() + " " + request.getRemoteAddr() + " " + request.getLocalName() + "\nSession: " + request.getRequestedSessionId());
        System.out.println("    Headers: ");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()){
           String header = headers.nextElement();
           /* if(header.equals("user-agent")){
                System.out.println("Browser: " + request.getHeader(header));
            }else if(header.equals("host")){
                System.out.println("host: " + request.getHeader(header));
            }else if(header.equals("connection")){
                System.out.println("connection: " + request.getHeader(header));
            }*/

            System.out.println(header + ": " + request.getHeader(header));
        }



       // getServletContext().getRequestDispatcher("/chat.jsp").forward(request, response);
    }

  //  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
