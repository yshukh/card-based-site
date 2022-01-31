package com.farttprojects.blogdao.model;

/**
 * Created by fatiz on 03.12.2017.
 */
public class Tag {

    private int id_tag;
    private String tag_title;

    public Tag(String tag_title) {
        this.id_tag = -1;
        this.tag_title = tag_title;
    }

    public int getId_tag() {
        return id_tag;
    }

    public void setId_tag(int id_tag) {
        this.id_tag = id_tag;
    }

    public String getTag_title() {
        return tag_title;
    }

    public void setTag_title(String tag_title) {
        this.tag_title = tag_title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (id_tag != tag.id_tag) return false;
        return tag_title != null ? tag_title.equals(tag.tag_title) : tag.tag_title == null;

    }

    @Override
    public int hashCode() {
        int result = id_tag;
        result = 31 * result + (tag_title != null ? tag_title.hashCode() : 0);
        return result;
    }
}
