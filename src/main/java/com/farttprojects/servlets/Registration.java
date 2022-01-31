package com.farttprojects.servlets;

import com.farttprojects.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fatiz on 07.12.2017.
 */

@WebServlet("/registration")
public class Registration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("unsuccess", false);
        req.getRequestDispatcher("/WEB-INF/forms/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        boolean success = UserService.addUser(login, password);
        if(success)
            req.getRequestDispatcher("/WEB-INF/forms/authorization.jsp").forward(req, resp);
        else {
            req.setAttribute("unsuccess", true);
            req.getRequestDispatcher("/WEB-INF/forms/registration.jsp").forward(req, resp);
        }
    }
}
