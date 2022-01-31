package com.farttprojects.services;

import com.farttprojects.blogdao.dao.PostDAO;
import com.farttprojects.blogdao.model.Post;
import com.farttprojects.blogdao.model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatiz on 28.11.2017.
 */
public class PostService {

    public static boolean addPost(Post post){
        PostDAO postDAO = new PostDAO();
        boolean result = false;
        try {
            List<Tag> tags = post.getTags();
            PostDAO.TagDAO tagDAO = postDAO. new TagDAO();

            int id = getMaxId() + 1;
            post.setId(id);
            result = postDAO.insert(post);

            for (Tag tag: tags) {
                Tag tagTemp = new Tag("default");
                tagTemp = tagDAO.getTag(tag.getTag_title());


                if(tagTemp.getId_tag() == -1)
                    tagDAO.insert(tag);
                tag.setId_tag(tagDAO.getTag(tag.getTag_title()).getId_tag());
                tagDAO.insertToPost_Tag(tag.getId_tag(), post.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Post getPost(Integer id){
        PostDAO postDAO = new PostDAO();
        Post post = null;
        try {
            post = postDAO.getEntityById(id);
            post.setTags(postDAO.new TagDAO().getAllTagsByPost(id));
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return post;
        }
    }

    public static Post updatePost(Post postNew){
        PostDAO postDAO = new PostDAO();
        Post post = new Post();
        try {
            List<Tag> tags = postNew.getTags();

            PostDAO.TagDAO tagDAO = postDAO. new TagDAO();

            List<Tag> oldTags = tagDAO.getAllTagsByPost(postNew.getId());

            post = postDAO.update(postNew);

            if(!tags.equals(oldTags)) {
                for (Tag tag : tags) {
                    Tag tagTemp;
                    tagTemp = tagDAO.getTag(tag.getTag_title());

                    if (tagTemp.getId_tag() == -1)
                        tagDAO.insert(tag);

                    tag.setId_tag(tagDAO.getTag(tag.getTag_title()).getId_tag());

                    if(!oldTags.contains(tag))
                        tagDAO.insertToPost_Tag(tag.getId_tag(), post.getId());
                }
            }

            post.setTags(tags);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public static Tag getTag(String title){
        PostDAO.TagDAO tagDAO = new PostDAO().new TagDAO();
        Tag result = new Tag("default");
        try {
            result = tagDAO.getTag(title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getMaxId(){
        try {
            return new PostDAO().getMaxId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
