package com.fatizprojects.servlets;

import com.fatizprojects.blogdao.model.Comment;
import com.fatizprojects.services.CommentService;
import com.fatizprojects.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by fatiz on 02.12.2017.
 */

@WebServlet("/sendComment")
public class SendComment extends HttpServlet {

    private final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id_post = req.getParameter("id");
        String text_comment = req.getParameter("comment");
        String author_login = null;
        author_login = (String) req.getSession(false).getAttribute("login");

        if(author_login == null)
            return;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String date = sdf.format(cal.getTime());

        Comment comment = new Comment();
        comment.setText(text_comment);
        comment.setId_post(Integer.parseInt(id_post));
        comment.setLogin_author(author_login);
        comment.setDate(date);

        CommentService.addComment(comment);

        resp.sendRedirect("/Blog/viewPost?id=" + id_post);
    }
}
