package com.eleonoralion.servlets;

import com.eleonoralion.demoDB.DataBase;
import com.eleonoralion.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;


@WebServlet("")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        //System.out.println("\"MainServlet\"\nURL: " + request.getRequestURI() + "\n" + LocalDateTime.now() + "\n" + request.getLocalAddr() + "\n" + request.getRequestedSessionId() + "\n");

        DataBase.start();

        getServletContext().getRequestDispatcher("/testpage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nickname = request.getParameter("nickname");

        // Тут проверяем данные пользователя, его ник
        //
        //
        //
        //

        User user = new User(nickname);

        if (!DataBase.users.contains(user)) {
            DataBase.addUser(user);
        }else{
            user = DataBase.getUserByNickname(nickname);
        }

        request.getSession().setAttribute("nickname", user.getNickname());
        request.getSession().setAttribute("userImagePath", user.getImagePath());
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/chat");
    }
}
