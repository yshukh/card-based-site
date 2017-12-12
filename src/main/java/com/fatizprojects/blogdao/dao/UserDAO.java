package com.fatizprojects.blogdao.dao;

import com.fatizprojects.blogdao.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatiz on 27.11.2017.
 */
public class UserDAO extends GeneralDAO<User, Integer> {
    private final String SELECT_ALL_USERS = "SELECT * FROM users";
    private final String SELECT_BY_ID = "SELECT * FROM users WHERE id_user = ?";
    private final String DELETE_BY_ID = "DELETE FROM users WHERE id_user = ?";
    private final String UPDATE_USER = "UPDATE users set login = ?, password = ?, privileged = ?  where id_user = ?";
    private final String INSERT_USER = "INSERT INTO users (login, password, privileged) "
            + "VALUES (?, ?, ?)";

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(SELECT_ALL_USERS);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            User user = new User();
            user.setId(rs.getInt(2));
            user.setLogin(rs.getString(4));
            user.setPass(rs.getString(1))   ;
            user.setPrivileged(rs.getBoolean(3));
            users.add(user);
        }
        return users;
    }

    @Override
    public User update(User entity) throws SQLException {
        PreparedStatement ps = getPrepareStatement(UPDATE_USER);
        ps.setString(1, entity.getLogin());
        ps.setString(2, entity.getPass());
        ps.setBoolean(3, entity.isPrivileged());
        ps.setInt(4, entity.getId());
        ps.executeUpdate();

        return getEntityById(entity.getId());
    }

    @Override
    public User getEntityById(Integer id) throws SQLException {
        User user = null;

        PreparedStatement ps = getPrepareStatement(SELECT_BY_ID);
        ps.setInt(1, id.intValue());
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            user = new User();
            user.setId(id.intValue());
            user.setLogin(rs.getString(4));
            user.setPass(rs.getString(1));
            user.setPrivileged(rs.getBoolean(3));
        }
        return user;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement ps = getPrepareStatement(DELETE_BY_ID);
        ps.setInt(1, id.intValue());
        int result = ps.executeUpdate();
        return result != 0;
    }

    @Override
    public boolean insert(User entity) throws SQLException {
        PreparedStatement ps = getPrepareStatement(INSERT_USER);
        ps.setString(1, entity.getLogin());
        ps.setString(2, entity.getPass());
        ps.setBoolean(3, entity.isPrivileged());
        int result = ps.executeUpdate();

        return result != 0;
    }
}
