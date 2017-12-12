package com.fatizprojects.blogdao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatiz on 27.11.2017.
 */
public class Post {
    private int id;
    private String title;
    private String text;
    private String imageLink;
    private boolean published;
    private String date;
    private ArrayList<Tag> tags;
    private String author;

    public Post() {
        this.id = 0;
        this.title = "tittle";
        this.text = "text";
        this.imageLink = "imageLink";
        this.published = false;
        this.date = "2017-11-25 12:00:00";
        this.tags = new ArrayList<>();
        this.author = "Author";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLinkImage() {
        return imageLink;
    }

    public void setLinkImage(String imageLink) {
        this.imageLink = imageLink;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = new ArrayList<Tag>(tags);
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", published=" + published +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != post.id) return false;
        if (published != post.published) return false;
        if (title != null ? !title.equals(post.title) : post.title != null) return false;
        if (text != null ? !text.equals(post.text) : post.text != null) return false;
        if (imageLink != null ? !imageLink.equals(post.imageLink) : post.imageLink != null) return false;
        if (date != null ? !date.equals(post.date) : post.date != null) return false;
        if (tags != null ? !tags.equals(post.tags) : post.tags != null) return false;
        return author != null ? author.equals(post.author) : post.author == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (imageLink != null ? imageLink.hashCode() : 0);
        result = 31 * result + (published ? 1 : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

}
