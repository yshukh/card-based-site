package com.fatizprojects.services;

import com.fatizprojects.blogdao.dao.PostDAO;
import com.fatizprojects.blogdao.model.Post;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fatiz on 07.12.2017.
 */
public class ShowPostsService {

    private String searchCriterion;
    private boolean onlyMyPosts;
    private SortCriterion sortCriterion;
    private int pageNumber;

    public ShowPostsService() {
        searchCriterion = "";
        onlyMyPosts = false;
        sortCriterion = SortCriterion.DEFAULT;
        pageNumber = 1;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getSearchCriterion() {
        return searchCriterion;
    }

    public SortCriterion getSortCriterion() {
        return sortCriterion;
    }

    public boolean isOnlyMyPosts() {
        return onlyMyPosts;
    }

    public List<Post> getPosts_search(String search, String login){
        searchCriterion = search;

        System.out.println("PAGES NUMBER = " + this.getPagesCount(login));
        List<Post> posts = new ArrayList<Post>();

        try {
            if (onlyMyPosts) {
                if (search.startsWith("#")) {
                    if (sortCriterion.equals(SortCriterion.PUBLISHED_FIRST)) {
                        posts = new PostDAO().getMyPostsByHTagPublFirst(new ArrayList<>(Arrays.asList(search.split("#"))), login, pageNumber);
                    } else if (sortCriterion.equals(SortCriterion.UNPUBLISHED_FIRST)) {
                        posts = new PostDAO().getMyPostsByHTagNotPublFirst(new ArrayList<>(Arrays.asList(search.split("#"))), login, pageNumber);
                    } else {
                        posts = new PostDAO().getMyPostsByHTagDef(new ArrayList<>(Arrays.asList(search.split("#"))), login, pageNumber);
                    }
                } else {
                    if (sortCriterion.equals(SortCriterion.PUBLISHED_FIRST)) {
                        posts = new PostDAO().getMyPostsPubl(new ArrayList<>(Arrays.asList(search.split(" "))), login, pageNumber);
                    } else if (sortCriterion.equals(SortCriterion.UNPUBLISHED_FIRST)) {
                        posts = new PostDAO().getMyPostsNotPubl(new ArrayList<>(Arrays.asList(search.split(" "))), login, pageNumber);
                    } else {
                        posts = new PostDAO().getMyPostsDefault(new ArrayList<>(Arrays.asList(search.split(" "))), login, pageNumber);
                    }
                }
            } else {
                if (search.startsWith("#")) {
                    if (sortCriterion.equals(SortCriterion.BY_ALIEN_POSTS_FIRST)) {
                        posts = new PostDAO().getAllPostsByHTagAllienFirst(login, new ArrayList<>(Arrays.asList(search.split("#"))), pageNumber);
                    } else if (sortCriterion.equals(SortCriterion.BY_MY_POSTS_FIRST)) {
                        posts = new PostDAO().getAllPostsByHTagMyFirst(login, new ArrayList<>(Arrays.asList(search.split("#"))), pageNumber);
                    } else {
                        posts = new PostDAO().getAllPostsByHTagDef(new ArrayList<>(Arrays.asList(search.split("#"))), pageNumber);
                    }
                } else {
                    if (sortCriterion.equals(SortCriterion.BY_ALIEN_POSTS_FIRST)) {
                        posts = new PostDAO().getAllPostsByWordsInnerFirst(login, new ArrayList<>(Arrays.asList(search.split(" "))), pageNumber);
                    } else if (sortCriterion.equals(SortCriterion.BY_MY_POSTS_FIRST)) {
                        posts = new PostDAO().getAllPostsByWordsMyFirst(login, new ArrayList<>(Arrays.asList(search.split(" "))), pageNumber);
                    } else {
                        posts = new PostDAO().getAllPostsByWordsDef(new ArrayList<>(Arrays.asList(search.split(" "))), pageNumber);
                    }
                }
            }
        }
        catch (SQLException e){

        }

        return posts;
    }

    public List<Post> getPosts_author(boolean onlyMyPosts, String login) {
        searchCriterion = "";
        sortCriterion = SortCriterion.DEFAULT;
        this.onlyMyPosts = onlyMyPosts;
        pageNumber = 1;
        List<Post> posts = new ArrayList<Post>();
        if(onlyMyPosts)
            try {
                posts = new PostDAO().getMyPostsDefault(new ArrayList<String>(), login, pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        else{
            try {
                posts = new PostDAO().getAllPostsByWordsDef(new ArrayList<String>(), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    public List<Post> getPosts_sort(SortCriterion sortCriterion, String login){
        this.sortCriterion = sortCriterion;

        return getPosts_search(this.searchCriterion, login);
    }

    public List<Post> getPosts_page(int pageNumber, String login){
        this.pageNumber = pageNumber;

        return getPosts_search(this.searchCriterion, login);
    }

    public int getPagesCount(String login) {
        try {
            if (onlyMyPosts)
                return (int) Math.ceil((double) new PostDAO().countRowsMyPosts(this.searchCriterion, login) / 12);
            else
                return (int) Math.ceil((double) new PostDAO().countRowsAllPosts(this.searchCriterion) / 12);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
