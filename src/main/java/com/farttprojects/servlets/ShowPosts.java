package com.farttprojects.servlets;

import com.farttprojects.blogdao.model.Post;
import com.farttprojects.services.ShowPostsService;
import com.farttprojects.services.SortCriterion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatiz on 07.12.2017.
 */

@WebServlet({"/showPosts", "/search"})
public class ShowPosts extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	System.out.print("Show POSTS do get");
        List<Post> posts;
        String login = "";
        String criterion;
        int param = -1;


        try{
            if(req.getAttribute("login") != null)
                login = (String) req.getAttribute("login");
            if(req.getParameter("par") != null)
                param = Integer.parseInt(req.getParameter("par"));
        }
        catch (NullPointerException e){

        }
        finally {
            ShowPostsService showPostsService = (ShowPostsService) req.getSession(false).getAttribute("POSTSERVICE");
            if (req.getRequestURI().substring(6).equals("search")) {
                criterion = (String) req.getParameter("criterion");
                posts = showPostsService.getPosts_search(criterion, login, true);
            } else {
                switch (param){
                    case -1:
                        posts = showPostsService.getPosts_author(false, login);
                        break;
                    case 1:
                        posts = showPostsService.getPosts_sort(SortCriterion.BY_MY_POSTS_FIRST, login);
                        req.setAttribute("my", true);
                        break;
                    case 2:
                        posts = showPostsService.getPosts_sort(SortCriterion.BY_ALIEN_POSTS_FIRST, login);
                        break;
                    case 3:
                        posts = showPostsService.getPosts_sort(SortCriterion.PUBLISHED_FIRST, login);
                        req.setAttribute("spubl", true);
                        break;
                    case 4:
                        posts = showPostsService.getPosts_sort(SortCriterion.UNPUBLISHED_FIRST, login);
                        req.setAttribute("sunpubl", true);
                        break;
                    case 5:
                        posts = showPostsService.getPosts_author(true, login);
                        break;
                    case 6:
                        posts = showPostsService.getPosts_page(Integer.parseInt(req.getParameter("page")), login);
                        break;
                    default:
                        posts = showPostsService.getPosts_author(false, login);
                        break;
                }
            }
            if(showPostsService.isOnlyMyPosts())
                req.setAttribute("src", "myposts");
            else
                req.setAttribute("src", "blogindex");
            int pages = showPostsService.getPagesCount(login);

            req.setAttribute("pagesAmount", pages);

            req.setAttribute("criterion", showPostsService.getSearchCriterion());

            switch (showPostsService.getSortCriterion()){
                case BY_ALIEN_POSTS_FIRST:
                    req.setAttribute("alien", true);
                    break;
                case BY_MY_POSTS_FIRST:
                    req.setAttribute("my", true);
                    break;
                case PUBLISHED_FIRST:
                    req.setAttribute("spubl", true);
                    break;
                case UNPUBLISHED_FIRST:
                    req.setAttribute("sunpubl", true);
                    break;
                default:
                    break;
            }

            req.setAttribute("currentPage", showPostsService.getPageNumber());

            req.getSession(false).removeAttribute("POSTSERVICE");
            req.getSession(false).setAttribute("POSTSERVICE", showPostsService);

            req.setAttribute("posts", posts);
            req.getRequestDispatcher("/WEB-INF/forms/showPosts.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
