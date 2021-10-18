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
        //HttpSession session = request.getSession();
        response.setContentType("text/html");
        //System.out.println("\"MainServlet\"\nURL: " + request.getRequestURI() + "\n" + LocalDateTime.now() + "\n" + request.getLocalAddr() + "\n" + request.getRequestedSessionId() + "\n");

        getServletContext().getRequestDispatcher("/testpage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("ChatServlet doPost");



        String s = request.getParameter("nickname");
        System.out.println("MainServlet doPost: " + s);

        User user = new User(s);

        if (!DataBase.users.contains(user)) {
            DataBase.addUser(user);
        }

        request.getSession().setAttribute("nickname", s);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userSessiom", request.getSession().getId());

        response.sendRedirect(request.getContextPath() + "/chat");
    }
}
