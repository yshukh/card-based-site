package com.farttprojects.blogdao.model;

/**
 * Created by fatiz on 27.11.2017.
 */
public class Comment {
    private int id;
    private String text;
    private String login_author;
    private int id_post;
    private String date;

    public Comment() {
        id = 0;
        text = "Brilliant";
        login_author = "admin";
        id_post = 0;
        date = "2017-11-25 12:00:00";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLogin_author() {
        return login_author;
    }

    public void setLogin_author(String login_author) {
        this.login_author = login_author;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", login_author=" + login_author +
                ", id_post=" + id_post +
                ", date='" + date + '\'' +
                '}';
    }
}
