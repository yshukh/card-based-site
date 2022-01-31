package com.farttprojects.blogdao.dao;

import com.farttprojects.blogdao.model.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatiz on 27.11.2017.
 */
public class CommentDAO extends GeneralDAO<Comment, Integer>{

    private final String SELECT_ALL_COMMENTS = "SELECT * FROM comments";
    private final String SELECT_BY_ID = "SELECT * FROM comments WHERE id_comment = ?";
    private final String DELETE_BY_ID = "DELETE FROM comments WHERE id_comment = ?";
    private final String UPDATE_COMMENT = "UPDATE comments set text = ?, author_login = ?, id_post = ?, date = ? "
            + "where id_comment = ?";
    private final String INSERT_COMMENT = "INSERT INTO comments (text, author_login, id_post, date) "
            + "VALUES (?, ?, ?, ?)";
    private final String SELECT_BY_POST_ID = "SELECT * FROM comments WHERE id_post = ?";

    @Override
    public List<Comment> getAll() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(SELECT_ALL_COMMENTS);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Comment comment = new Comment();
            comment.setId(rs.getInt(1));
            comment.setText(rs.getString(2));
            comment.setLogin_author(rs.getString(4));
            comment.setId_post(rs.getInt(5));
            comment.setDate(rs.getString(3));

            comments.add(comment);
        }
        return comments;
    }

    @Override
    public Comment update(Comment entity) throws SQLException {
        PreparedStatement ps = getPrepareStatement(UPDATE_COMMENT);
        ps.setInt(5, entity.getId());
        ps.setString(1, entity.getText());
        ps.setString(2, entity.getLogin_author());
        ps.setInt(3, entity.getId_post());

        Timestamp timestamp = Timestamp.valueOf(entity.getDate());
        ps.setTimestamp(4, timestamp);

        ps.executeUpdate();

        return getEntityById(entity.getId());
    }

    @Override
    public Comment getEntityById(Integer id) throws SQLException {
        Comment comment = null;

        PreparedStatement ps = getPrepareStatement(SELECT_BY_ID);
        ps.setInt(1, id.intValue());
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            comment = new Comment();
            comment.setId(id.intValue());
            comment.setText(rs.getString(2));
            comment.setLogin_author((rs.getString(4)));
            comment.setId_post(rs.getInt(5));
            comment.setDate(rs.getString(3));
        }
        return comment;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement ps = getPrepareStatement(DELETE_BY_ID);
        ps.setInt(1, id.intValue());
        int result = ps.executeUpdate();
        return result != 0;
    }

    @Override
    public boolean insert(Comment entity) throws SQLException {
        PreparedStatement ps = getPrepareStatement(INSERT_COMMENT);
        ps.setString(1, entity.getText());
        ps.setString(2, entity.getLogin_author());
        ps.setInt(3, entity.getId_post());
        Timestamp timestamp = Timestamp.valueOf(entity.getDate());
        ps.setTimestamp(4, timestamp);
        int result = ps.executeUpdate();

        return result != 0;
    }

    public List<Comment> getAllByPostId(int id_post) throws SQLException {

        PreparedStatement ps = getPrepareStatement(SELECT_BY_POST_ID);
        ps.setInt(1, id_post);
        ResultSet rs = ps.executeQuery();

        List<Comment> comments = new ArrayList<>();
        while (rs.next()){
            Comment comment = new Comment();
            comment.setText(rs.getString(2));
            comment.setId(rs.getInt(1));
            comment.setLogin_author(rs.getString(4));
            comment.setId_post(rs.getInt(5));
            comment.setDate(rs.getString(3));

            comments.add(comment);
        }
        return comments;
    }
}
