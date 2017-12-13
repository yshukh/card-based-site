package com.fatizprojects.filters;

import com.fatizprojects.services.PostService;
import com.fatizprojects.services.ShowPostsService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by fatiz on 30.11.2017.
 */

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String user = null;
        ShowPostsService showPostsService = null;
        try {
            user = (String) req.getSession(false).getAttribute("login");
            showPostsService = (ShowPostsService) req.getSession(false).getAttribute("POSTSERVICE");
        }
        catch (NullPointerException e){

        }
        finally {
            if (user != null) {
                request.setAttribute("logined", true);
                request.setAttribute("login", user);
            }
            else {
                request.setAttribute("logined", false);
                if(req.getRequestURI().contains("/Blog/addPost") ||
                        req.getRequestURI().contains("/Blog/editPost") ||
                        req.getRequestURI().contains("/Blog/logout") ||
                        req.getRequestURI().contains("/Blog/sendComment")){
                    req.getRequestDispatcher("/WEB-INF/forms/accessError.jsp").forward(req, response);
                }
            }

            if(req.getRequestURI().substring(6).equals("showPosts") ||
                    req.getRequestURI().substring(6).equals("search")||
                    req.getRequestURI().substring(6).equals("")){
                if(showPostsService == null) {
                    req.getSession().setAttribute("POSTSERVICE", new ShowPostsService());
                }
            } else {
                if(showPostsService != null) {
                    req.getSession(false).removeAttribute("POSTSERVICE");
                }
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
