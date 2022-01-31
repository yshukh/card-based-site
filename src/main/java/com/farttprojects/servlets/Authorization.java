package com.farttprojects.servlets;

import com.farttprojects.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by fatiz on 27.11.2017.
 */

@WebServlet(urlPatterns = {"/authorization"})
public class Authorization extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/forms/authorization.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        boolean isCorrect = UserService.checkUser(login, pass);
        if(isCorrect) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
//            session.setMaxInactiveInterval(60);
//            Cookie userName = new Cookie("user", login);
//            userName.setMaxAge(60);
//            resp.addCookie(userName);
            resp.sendRedirect("/Blog");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/forms/authorization.jsp").forward(req, resp);
        }
    }
}
