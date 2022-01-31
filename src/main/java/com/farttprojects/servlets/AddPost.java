package com.farttprojects.servlets;

import com.farttprojects.blogdao.model.Post;
import com.farttprojects.blogdao.model.Tag;
import com.farttprojects.services.PostService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fatiz on 28.11.2017.
 */

@WebServlet("/addPost")
@MultipartConfig
public class AddPost extends HttpServlet {

    private final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        request.setAttribute("help", true);
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        String hashtags = request.getParameter("hashtags");
        String[] tags = hashtags.split("#");

        List<Tag> tags_forPost = new ArrayList<>();

        for (String tag: tags){
            if(tag.equals(""))
                continue;
            Tag tagl = new Tag(tag);
            tagl.setId_tag(PostService.getTag(tag).getId_tag());
            tags_forPost.add(tagl);
        }


        String isvisible = request.getParameter("isvisible");
        boolean isVisible;
        if(isvisible == null)
            isVisible = false;
        else
            isVisible = true;

        Part filePart = request.getPart("file");
        String appPath = request.getServletContext().getRealPath("");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String image_link = "";
        filePart.write(appPath + "../pictures/" + fileName);
        image_link = "../pictures/" + fileName;

        Post post = new Post();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        post.setDate(sdf.format(cal.getTime()));

        post.setLinkImage(image_link);
        post.setText(text);
        post.setTitle(title);
        post.setPublished(isVisible);
        post.setTags(tags_forPost);
        post.setAuthor(request.getAttribute("login").toString());

        boolean success = PostService.addPost(post);
        if(success)
            request.setAttribute("success", true);
        else
            request.setAttribute("success", false);

        request.getRequestDispatcher("/WEB-INF/forms/addPost.jsp").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        request.getRequestDispatcher("/WEB-INF/forms/addPost.jsp").forward(request, response);
    }
}
