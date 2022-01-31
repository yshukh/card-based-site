package com.farttprojects.services;

import com.farttprojects.blogdao.dao.UserDAO;
import com.farttprojects.blogdao.model.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by fatiz on 27.11.2017.
 */
public class UserService {

    private static UserDAO userManager = new UserDAO();

    public static boolean checkUser(String login, String password){
        User curUser = new User();
        curUser.setId(0);
        curUser.setPrivileged(false);
        curUser.setLogin(login);
        curUser.setPass(password);

        List<User> users = null;

        try {
            users = userManager.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users.contains(curUser);
    }

    public static boolean addUser(String login, String password){
        User curUser = new User();

        curUser.setId(0);
        curUser.setLogin(login);
        curUser.setPrivileged(false);
        curUser.setPass(password);

        try {
            return userManager.insert(curUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
