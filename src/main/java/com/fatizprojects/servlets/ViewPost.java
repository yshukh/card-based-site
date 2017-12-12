package com.fatizprojects.servlets;

import com.fatizprojects.blogdao.model.Comment;
import com.fatizprojects.blogdao.model.Tag;
import com.fatizprojects.services.CommentService;
import com.fatizprojects.services.PostService;
import com.fatizprojects.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fatiz on 29.11.2017.
 */

@WebServlet("/viewPost")
public class ViewPost extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = new Integer(req.getParameter("id"));
        req.setAttribute("post", PostService.getPost(id));
        List<Tag> tags = PostService.getPost(id).getTags();
        Tag excessive = new Tag("");
        excessive.setId_tag(10);
        tags.remove(excessive);
        if(tags.size() != 0) {
            req.setAttribute("tags", tags);
        }
        List<Comment> comments = CommentService.getAllCommentsSortedByDate(id.intValue());
        Collections.reverse(comments);
        req.setAttribute("comments", comments);
        req.getRequestDispatcher("/WEB-INF/forms/viewPost.jsp").forward(req, resp);
    }
}
