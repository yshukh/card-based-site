package com.farttprojects.blogdao.model;

/**
 * Created by fatiz on 27.11.2017.
 */
public class User {

    private int id;
    private String login;
    private String pass;
    private boolean privileged;

    public User() {
        id = 0;
        login = "admin";
        pass = "admin";
        privileged = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", privileged=" + privileged +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!User.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        return ((User) obj).getLogin().equals(this.getLogin()) && ((User) obj).getPass().equals(this.getPass());
    }
}
