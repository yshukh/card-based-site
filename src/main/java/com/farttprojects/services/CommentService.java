package com.farttprojects.services;

import com.farttprojects.blogdao.dao.CommentDAO;
import com.farttprojects.blogdao.model.Comment;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by fatiz on 02.12.2017.
 */
public class CommentService {

    private static CommentDAO commentDAO = new CommentDAO();

    public static boolean addComment(Comment comment){

        boolean result = false;
        try {
            result = commentDAO.insert(comment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Comment> getAllCommentsSortedByDate(int id_post){

        List<Comment> comments = new ArrayList<>();

        try {
            comments = commentDAO.getAllByPostId(id_post);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        comments.sort(comparatorByDate);
        return comments;
    }

    private static Comparator<Comment> comparatorByDate = (p1, p2) ->{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return ( (sdf.parse(p1.getDate()).before( sdf.parse(p2.getDate()) )) ? 1 : -1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return 1;
        }
    };

}
