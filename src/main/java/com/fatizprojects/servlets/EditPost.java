package com.fatizprojects.servlets;

import com.fatizprojects.blogdao.model.Post;
import com.fatizprojects.blogdao.model.Tag;
import com.fatizprojects.services.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fatiz on 04.12.2017.
 */

@WebServlet("/editPost")
@MultipartConfig
public class EditPost extends HttpServlet {

    private final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Post post = PostService.getPost(id);
        req.setAttribute("post", post);
        req.setAttribute("tags", post.getTags());
        req.setAttribute("edit", true);
        req.getRequestDispatcher("/WEB-INF/forms/addPost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("help", true);

        int id = Integer.parseInt(req.getParameter("post_id"));
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String hashtags = req.getParameter("hashtags");
        String[] tags = hashtags.split("#");

        List<Tag> tags_forPost = new ArrayList<>();
        for (String tag: tags) {
            if(tag.equals(""))
                continue;
            Tag tagl = new Tag(tag);
            tagl.setId_tag(PostService.getTag(tag).getId_tag());
            tags_forPost.add(tagl);
        }
        Collections.sort(tags_forPost, comparator);



        String isvisible = req.getParameter("isvisible");
        boolean isVisible;
        if(isvisible == null)
            isVisible = false;
        else
            isVisible = true;

        Part filePart = req.getPart("file");
        String appPath = req.getServletContext().getRealPath("");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String image_link = "";
        if(fileName == null || fileName.equals("")){
            image_link = PostService.getPost(id).getLinkImage();
//            filePart.write(appPath + image_link);
        }else {
            filePart.write(appPath + "../pictures/" + fileName);
            image_link = "../pictures/" + fileName;
        }


        Post post = new Post();
        post.setTags(tags_forPost);

        Post oldVersion = PostService.getPost(id);
        tags_forPost = oldVersion.getTags();
        Collections.sort(tags_forPost, comparator);
        oldVersion.setTags(tags_forPost);

        post.setId(id);
        post.setLinkImage(image_link);
        post.setText(text);
        post.setTitle(title);
        post.setPublished(isVisible);
        post.setAuthor(oldVersion.getAuthor());
        post.setDate(oldVersion.getDate());

        boolean wasUpdated = !post.equals(oldVersion);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

        post.setAuthor(req.getSession(false).getAttribute("login").toString());
        post.setDate(sdf.format(cal.getTime()));

        boolean changedInDB = false;
        if(wasUpdated)
            changedInDB = !PostService.updatePost(post) .equals(oldVersion);

        if(changedInDB)
            req.setAttribute("success", true);
        else
            req.setAttribute("success", false);

        req.setAttribute("edit", true);
        req.setAttribute("post", post);
        req.setAttribute("tags", post.getTags());
        req.getRequestDispatcher("/WEB-INF/forms/addPost.jsp").forward(req, resp);
    }

    private Comparator<Tag> comparator = (o1, o2) ->{
        return o1.getTag_title().compareTo(o2.getTag_title());
    };
}
