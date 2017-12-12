package com.fatizprojects.blogdao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by fatiz on 27.11.2017.
 */
public abstract class GeneralDAO<E, K> {
    private Connection connection;

    public abstract List<E> getAll() throws SQLException;
    public abstract E update(E entity) throws SQLException;
    public abstract E getEntityById(K id) throws SQLException;
    public abstract boolean delete(K id) throws SQLException;
    public abstract boolean insert(E entity) throws SQLException;

    public GeneralDAO(){
        connection = DBconnection.getConnection();
    }

    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
