package com.farttprojects.blogdao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by fatiz on 27.11.2017.
 */
public class DBconnection {

    private static String LOGIN = "postgres";
    private static String PASS = "admin";
    private static String URL = "jdbc:postgresql://localhost:5432/blog";

    private static Connection connection;

    public static Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, LOGIN, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
