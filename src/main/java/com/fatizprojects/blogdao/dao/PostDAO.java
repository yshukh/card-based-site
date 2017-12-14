package com.fatizprojects.blogdao.dao;

import com.fatizprojects.blogdao.model.Post;
import com.fatizprojects.blogdao.model.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

/**
 * Created by fatiz on 27.11.2017.
 */
public class PostDAO extends GeneralDAO<Post, Integer> {

    private final String SELECT_ALL_POSTS = "SELECT * FROM posts";
    private final String SELECT_BY_ID = "SELECT * FROM posts WHERE id_post = ?";
    private final String DELETE_BY_ID = "DELETE FROM posts WHERE id_post = ?";
    private final String UPDATE_POST = "UPDATE posts set title = ?, text = ?, published = ?, date = ?, image_link = ?, author = ?"
            + "where id_post = ?";
    private final String INSERT_POST = "INSERT INTO posts (title, text, published, date, image_link, author) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public List<Post> getAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(SELECT_ALL_POSTS);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setText(rs.getString(3));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    @Override
    public Post update(Post entity) throws SQLException {

        PreparedStatement ps = getPrepareStatement(UPDATE_POST);

        ps.setString(1, entity.getTitle());
        ps.setString(2, entity.getText());
        ps.setBoolean(3, entity.isPublished());

        Timestamp timestamp = Timestamp.valueOf(entity.getDate());
        ps.setTimestamp(4, timestamp);

        ps.setString(5, entity.getLinkImage());

        ps.setInt(7, entity.getId());
        ps.setString(6, entity.getAuthor());
        ps.executeUpdate();

        return getEntityById(entity.getId());
    }

    @Override
    public Post getEntityById(Integer id) throws SQLException {
        Post post = new Post();
        PreparedStatement ps = getPrepareStatement(SELECT_BY_ID);
        ps.setInt(1, id.intValue());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setText(rs.getString(3));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
        }
        return post;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement ps = getPrepareStatement(DELETE_BY_ID);
        ps.setInt(1, id.intValue());
        int result = ps.executeUpdate();
        return result != 0;
    }

    @Override
    public boolean insert(Post entity) throws SQLException {
        PreparedStatement ps = getPrepareStatement(INSERT_POST);
        ps.setString(1, entity.getTitle());
        ps.setString(2, entity.getText());
        ps.setBoolean(3, entity.isPublished());

        Timestamp timestamp = Timestamp.valueOf(entity.getDate());
        ps.setTimestamp(4, timestamp);

        ps.setString(5, entity.getLinkImage());
        ps.setString(6, entity.getAuthor());

        int result = ps.executeUpdate();
        return result != 0;
    }


//    queries for searching and pagination(SELECT1 - SELECT12): ...
//
//
//    query search by word(-s) in my posts default sort(by date)
    private String SELECT1 = "SELECT * FROM posts WHERE ((LOWER(title) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?))) AND (author=?) order BY date DESC LIMIT 12 OFFSET ?";

    public List<Post> getMyPostsDefault(ArrayList<String> criterionWords, String login, int pageNumber) throws SQLException {
        if(criterionWords.size() > 1) {
            for (int i = 1; i < criterionWords.size(); i++) {
                String resQuery = " OR (LOWER(title) LIKE LOWER('%" + criterionWords.get(i) + "%') OR LOWER(text) LIKE LOWER('%" + criterionWords.get(i) + "%'))";
                SELECT1 = SELECT1.substring(0, 84) + resQuery + SELECT1.substring(84);
            }
        }

        List<Post> posts = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(SELECT1);
        if(criterionWords.size() == 0){
            ps.setString(1, "%");
            ps.setString(2, "%");
        }
        else{
            ps.setString(1, "%" + criterionWords.get(0) + "%");
            ps.setString(2, "%" + criterionWords.get(0) + "%");
        }
        ps.setString(3, login);
        ps.setInt(4, (pageNumber - 1) * 12);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search by word(-s) in my posts published sort(publ first)
    private String SELECT3 = "SELECT * FROM posts WHERE ((LOWER(title) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?))) AND (author=?) order BY published DESC, date DESC LIMIT 12 OFFSET ?";
    public List<Post> getMyPostsPubl(ArrayList<String> criterionWords, String login, int pageNumber) throws SQLException {
        if(criterionWords.size() > 1) {
            for (int i = 1; i < criterionWords.size(); i++) {
                String resQuery = " OR (LOWER(title) LIKE LOWER('%" + criterionWords.get(i) + "%') OR LOWER(text) LIKE LOWER('%" + criterionWords.get(i) + "%'))";
                SELECT3 = SELECT3.substring(0, 84) + resQuery + SELECT3.substring(84);
            }
        }

        PreparedStatement ps = getPrepareStatement(SELECT3);
        List<Post> posts = new ArrayList<>();
        if(criterionWords.size() == 0){
            ps.setString(2, "%");
            ps.setString(1, "%");
        }
        else{
            ps.setString(1, "%" + criterionWords.get(0) + "%");
            ps.setString(2, "%" + criterionWords.get(0) + "%");
        }

        ps.setString(3, login);
        ps.setInt(4, (pageNumber - 1) * 12);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search by word(-s) in my posts published sort(not publ first)
    private String SELECT2 = "SELECT * FROM posts WHERE ((LOWER(title) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?))) AND (author=?) order BY published, date DESC LIMIT 12 OFFSET ?";
    public List<Post> getMyPostsNotPubl(ArrayList<String> criterionWords, String login, int pageNumber) throws SQLException {

        if(criterionWords.size() > 1) {
            for (int i = 1; i < criterionWords.size(); i++) {
                String resQuery = " OR (LOWER(title) LIKE LOWER('%" + criterionWords.get(i) + "%') OR LOWER(text) LIKE LOWER('%" + criterionWords.get(i) + "%'))";
                SELECT2 = SELECT2.substring(0, 84) + resQuery + SELECT2.substring(84);
            }
        }

        PreparedStatement ps = getPrepareStatement(SELECT2);
        List<Post> posts = new ArrayList<>();
        if(criterionWords.size() == 0){
            ps.setString(2, "%");
            ps.setString(1, "%");
        }
        else{
            ps.setString(1, "%" + criterionWords.get(0) + "%");
            ps.setString(2, "%" + criterionWords.get(0) + "%");
        }
        ps.setString(3, login);
        ps.setInt(4, (pageNumber - 1) * 12);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search by words in all posts default sort(by date)
    private String SELECT4= "SELECT * FROM posts WHERE ((LOWER(title) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?))) AND (published=true) ORDER BY date DESC LIMIT 12 OFFSET ?";
    public List<Post> getAllPostsByWordsDef(ArrayList<String> criterionWords, int pageNumber) throws SQLException {
        if(criterionWords.size() > 1) {
            for (int i = 1; i < criterionWords.size(); i++) {
                String resQuery = " OR (LOWER(title) LIKE LOWER('%" + criterionWords.get(i) + "%') OR LOWER(text) LIKE LOWER('%" + criterionWords.get(i) + "%'))";
                SELECT4 = SELECT4.substring(0, 84) + resQuery + SELECT4.substring(84);
            }
        }

        PreparedStatement ps = getPrepareStatement(SELECT4);
        List<Post> posts = new ArrayList<>();
        if(criterionWords.size() == 0){
            ps.setString(2, "%");
            ps.setString(1, "%");
        }
        else{
            ps.setString(1, "%" + criterionWords.get(0) + "%");
            ps.setString(2, "%" + criterionWords.get(0) + "%");
        }
        ps.setInt(3, (pageNumber - 1) * 12);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }

        return posts;
    }

    //query search by words in all posts sort for inner first
    private String SELECT5 = "SELECT *, author=? AS col FROM posts WHERE ((LOWER(title) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?))) AND (published=true) ORDER BY col, date DESC LIMIT 12 OFFSET ?";
    public List<Post> getAllPostsByWordsInnerFirst(String login, ArrayList<String> criterionWords, int pageNumber) throws SQLException {
        if(criterionWords.size() > 1) {
            for (int i = 1; i < criterionWords.size(); i++) {
                String resQuery = " OR (LOWER(title) LIKE LOWER('%" + criterionWords.get(i) + "%') OR LOWER(text) LIKE LOWER('%" + criterionWords.get(i) + "%'))";
                SELECT5 = SELECT5.substring(0, 101) + resQuery + SELECT5.substring(101);
            }
        }


        PreparedStatement ps = getPrepareStatement(SELECT5);
        ps.setString(1, login);
        List<Post> posts = new ArrayList<>();
        if(criterionWords.size() == 0){
            ps.setString(2, "%");
            ps.setString(3, "%");
        }
        else{
            ps.setString(2, "%" + criterionWords.get(0) + "%");
            ps.setString(3, "%" + criterionWords.get(0) + "%");
        }
        ps.setInt(4, (pageNumber - 1) * 12);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //    query search by words in all posts sort for my first
    private String SELECT6 = "SELECT *, author=? AS col FROM posts WHERE ((LOWER(title) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?))) AND (published=true) ORDER BY col DESC, date DESC LIMIT 12 OFFSET ?";
    public List<Post> getAllPostsByWordsMyFirst(String login, ArrayList<String> criterionWords, int pageNumber) throws SQLException {
        if(criterionWords.size() > 1) {
            for (int i = 1; i < criterionWords.size(); i++) {
                String resQuery = " OR (LOWER(title) LIKE LOWER('%" + criterionWords.get(i) + "%') OR LOWER(text) LIKE LOWER('%" + criterionWords.get(i) + "%'))";
                SELECT6 = SELECT6.substring(0, 101) + resQuery + SELECT6.substring(101);
            }
        }
        PreparedStatement ps = getPrepareStatement(SELECT6);
        ps.setString(1, login);
        List<Post> posts = new ArrayList<>();
        if(criterionWords.size() == 0){
            ps.setString(2, "%");
            ps.setString(3, "%");
        }
        else{
            ps.setString(2, "%" + criterionWords.get(0) + "%");
            ps.setString(3, "%" + criterionWords.get(0) + "%");
        }
        ps.setInt(4, (pageNumber - 1) * 12);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search hash-tag in my posts default sort
    private String SELECT7 = "SELECT DISTINCT posts.*, posts_tags.post_id FROM posts JOIN posts_tags ON (posts.id_post=posts_tags.post_id) JOIN tags ON (posts_tags.tag_id=tags.tag_id AND (tag_title=LOWER(?))) WHERE(author=?) ORDER BY date DESC limit 12 offset ?";
    public List<Post> getMyPostsByHTagDef(ArrayList<String> tags, String login, int pageNumber) throws SQLException {

        tags = tags.stream()
                .filter(e -> !e.equals(""))
                .collect(toCollection(ArrayList::new));

        if(tags.size() > 1) {
            for (int i = 1; i < tags.size(); i++) {
                String resQuery = " OR tag_title=LOWER('" + tags.get(i) + "')";
                SELECT7 = SELECT7.substring(0, 177) + resQuery + SELECT7.substring(177);
            }
        }

        PreparedStatement ps = getPrepareStatement(SELECT7);
        ps.setString(2, login);
        List<Post> posts = new ArrayList<>();
        if(tags.size() == 0){
            ps.setString(1, "%");
        }
        else{
            ps.setString(1, tags.get(0));
        }
        ps.setInt(3, (pageNumber - 1) * 12);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search hash-tag in my posts published first sort
    private String SELECT8 = "SELECT DISTINCT posts.*, posts_tags.post_id FROM posts JOIN posts_tags ON (posts.id_post=posts_tags.post_id) JOIN tags ON (posts_tags.tag_id=tags.tag_id AND (tag_title=LOWER(?))) WHERE(author=?) ORDER BY published DESC, date DESC limit 12 offset ?";
    public List<Post> getMyPostsByHTagPublFirst(ArrayList<String> tags, String login, int pageNumber) throws SQLException {

        tags = tags.stream()
                .filter(e -> !e.equals(""))
                .collect(toCollection(ArrayList::new));

        if(tags.size() > 1) {
            for (int i = 1; i < tags.size(); i++) {
                String resQuery = " OR tag_title=LOWER('" + tags.get(i) + "')";
                SELECT8 = SELECT8.substring(0, 177) + resQuery + SELECT8.substring(177);
            }
        }

        PreparedStatement ps = getPrepareStatement(SELECT8);
        ps.setString(2, login);
        List<Post> posts = new ArrayList<>();
        if(tags.size() == 0){
            ps.setString(1, "%");
        }
        else{
            ps.setString(1, tags.get(0));
        }
        ps.setInt(3, (pageNumber - 1) * 12);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search hash-tag in my posts not published first sort
    private String SELECT9 = "SELECT DISTINCT posts.*, posts_tags.post_id FROM posts JOIN posts_tags ON (posts.id_post=posts_tags.post_id) JOIN tags ON (posts_tags.tag_id=tags.tag_id AND (tag_title=LOWER(?))) WHERE(author=?) ORDER BY published, date DESC limit 12 offset ?";
    public List<Post> getMyPostsByHTagNotPublFirst(ArrayList<String> tags, String login, int pageNumber) throws SQLException {

        tags = tags.stream()
                .filter(e -> !e.equals(""))
                .collect(toCollection(ArrayList::new));

        if(tags.size() > 1) {
            for (int i = 1; i < tags.size(); i++) {
                String resQuery = " OR tag_title=LOWER('" + tags.get(i) + "')";
                SELECT9 = SELECT9.substring(0, 177) + resQuery + SELECT9.substring(177);
            }
        }

        PreparedStatement ps = getPrepareStatement(SELECT9);
        ps.setString(2, login);
        List<Post> posts = new ArrayList<>();
        if(tags.size() == 0){
            ps.setString(1, "%");
        }
        else{
            ps.setString(1, tags.get(0));
        }
        ps.setInt(3, (pageNumber - 1) * 12);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search hash tag in all posts default sort by date
    private String SELECT10 = "SELECT DISTINCT posts.*, posts_tags.post_id FROM posts JOIN posts_tags ON (posts.id_post=posts_tags.post_id) JOIN tags ON (posts_tags.tag_id=tags.tag_id AND (tag_title=LOWER(?))) WHERE(published=true) ORDER BY date DESC limit 12 offset ?";
    public List<Post> getAllPostsByHTagDef(ArrayList<String> tags, int pageNumber) throws SQLException {

        tags = tags.stream()
                .filter(e -> !e.equals(""))
                .collect(toCollection(ArrayList::new));

        if(tags.size() > 1) {
            for (int i = 1; i < tags.size(); i++) {
                String resQuery = " OR tag_title=LOWER('" + tags.get(i) + "')";
                SELECT10 = SELECT10.substring(0, 176) + resQuery + SELECT10.substring(176);
            }
        }

        PreparedStatement ps = getPrepareStatement(SELECT10);
        List<Post> posts = new ArrayList<>();
        if(tags.size() == 0){
            ps.setString(1, "%");
        }
        else{
            ps.setString(1, tags.get(0));
        }

        ps.setInt(2, (pageNumber - 1) * 12);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search hash tag in all posts sort by my posts first
    private String SELECT11 = "SELECT DISTINCT posts.*, posts_tags.post_id, author=? AS col FROM posts JOIN posts_tags ON (posts.id_post=posts_tags.post_id) JOIN tags ON (posts_tags.tag_id=tags.tag_id AND (tag_title=LOWER(?))) WHERE(published=true) ORDER BY col DESC, date DESC limit 12 offset ?";
    public List<Post> getAllPostsByHTagMyFirst(String login, ArrayList<String> tags, int pageNumber) throws SQLException {

        tags = tags.stream()
                .filter(e -> !e.equals(""))
                .collect(toCollection(ArrayList::new));

        List<Post> posts = new ArrayList<>();
        if(tags.size() > 1) {
            for (int i = 1; i < tags.size(); i++) {
                String resQuery = " OR tag_title=LOWER('" + tags.get(i) + "')";
                SELECT11 = SELECT11.substring(0, 193) + resQuery + SELECT11.substring(193);
            }
        }
        PreparedStatement ps = getPrepareStatement(SELECT11);

        ps.setString(1, login);

        if(tags.size() == 0){
            ps.setString(2, "%");
        }
        else{
            ps.setString(2, tags.get(0));
        }
        ps.setInt(3, (pageNumber - 1) * 12);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    //query search hash tag in all posts sort by alien posts first
    private String SELECT12 = "SELECT DISTINCT posts.*, posts_tags.post_id, author=? AS col FROM posts JOIN posts_tags ON (posts.id_post=posts_tags.post_id) JOIN tags ON (posts_tags.tag_id=tags.tag_id AND (tag_title=LOWER(?))) WHERE(published=true) ORDER BY col, date DESC limit 12 offset ?";
    public List<Post> getAllPostsByHTagAllienFirst(String login, ArrayList<String> tags, int pageNumber) throws SQLException {

        tags = tags.stream()
                .filter(e -> !e.equals(""))
                .collect(toCollection(ArrayList::new));

        if(tags.size() > 1) {
            for (int i = 1; i < tags.size(); i++) {
                String resQuery = " OR tag_title=LOWER('" + tags.get(i) + "')";
                SELECT12 = SELECT12.substring(0, 193) + resQuery + SELECT12.substring(193);
            }
        }

        PreparedStatement ps = getPrepareStatement(SELECT12);
        ps.setString(1, login);
        List<Post> posts = new ArrayList<>();
        if(tags.size() == 0){
            ps.setString(2, "%");
        }
        else{
            ps.setString(2, tags.get(0));
        }
        ps.setInt(3, (pageNumber - 1) * 12);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Post post = new Post();
            post.setId(rs.getInt(1));
            post.setTitle(rs.getString(2));
            post.setPublished(rs.getBoolean(4));
            post.setDate(rs.getString(6));
            post.setText(rs.getString(3));
            post.setLinkImage(rs.getString(5));
            post.setAuthor(rs.getString(7));
            posts.add(post);
        }
        return posts;
    }

    private String countRowsAllPostsWordsSearch = "SELECT COUNT(*) AS col FROM posts WHERE (LOWER(title) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?)) AND (published=true)";
    private String countRowsMyPostsWordsSearch = "SELECT COUNT(*) AS col FROM posts WHERE (LOWER(title) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?)) AND (author=?)";
    private String countRowsAllPostsHTagSearch = "SELECT DISTINCT COUNT(*) FROM posts JOIN posts_tags ON (posts.id_post=posts_tags.post_id) JOIN tags ON (posts_tags.tag_id=tags.tag_id AND (tag_title=LOWER(?))) WHERE (published=true)";
    private String countRowsMyPostsHTagSearch = "SELECT DISTINCT COUNT(*) FROM posts JOIN posts_tags ON (posts.id_post=posts_tags.post_id) JOIN tags ON (posts_tags.tag_id=tags.tag_id AND (tag_title=LOWER(?))) WHERE (author=?)";

    public int countRowsMyPosts(String searchCriterion, String login) throws SQLException {
        ArrayList<String> search;
        if(searchCriterion.startsWith("#")) {
            search = new ArrayList<>(Arrays.asList(searchCriterion.split("#")));
        } else{
            search = new ArrayList<>(Arrays.asList(searchCriterion.split(" ")));
        }

        search = search.stream()
                .filter(e -> !e.equals(""))
                .collect(toCollection(ArrayList::new));

        if(searchCriterion.startsWith("#")) {
            if (search.size() > 1) {
                for (int i = 1; i < search.size(); i++) {
                    String resQuery = " OR tag_title=LOWER('" + search.get(i) + "')";
                    countRowsMyPostsHTagSearch = countRowsMyPostsHTagSearch.substring(0, 157) + resQuery +
                            countRowsMyPostsHTagSearch.substring(157);
                }
            }

            PreparedStatement ps = getPrepareStatement(countRowsMyPostsHTagSearch);
            if(search.size() == 0){
                ps.setString(1, "%");
            }
            else{
                ps.setString(1, search.get(0));
            }
            ps.setString(2, login);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } else {
            if (search.size() > 1) {
                for (int i = 1; i < search.size(); i++) {
                    String resQuery = " OR (LOWER(title) LIKE LOWER('%" + search.get(i) + "%') OR LOWER(text) LIKE LOWER('%" + search.get(i) + "%'))";
                    countRowsMyPostsWordsSearch = countRowsMyPostsWordsSearch.substring(0, 96) + resQuery + countRowsMyPostsWordsSearch.substring(96);
                }
            }
            PreparedStatement ps = getPrepareStatement(countRowsMyPostsWordsSearch);
            if (search.size() == 0) {
                ps.setString(1, "%");
                ps.setString(2, "%");
            } else {
                ps.setString(1, "%" + search.get(0) + "%");
                ps.setString(2, "%" + search.get(0) + "%");
            }
            ps.setString(3, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        }
        return 0;
    }

    public int countRowsAllPosts(String searchCriterion) throws SQLException {
        ArrayList<String> search;
        if(searchCriterion.startsWith("#")) {
            search = new ArrayList<>(Arrays.asList(searchCriterion.split("#")));
        } else{
            search = new ArrayList<>(Arrays.asList(searchCriterion.split(" ")));
        }

        search = search.stream()
                .filter(e -> !e.equals(""))
                .collect(toCollection(ArrayList::new));

        if(searchCriterion.startsWith("#")) {
            if (search.size() > 1) {
                for (int i = 1; i < search.size(); i++) {
                    String resQuery = " OR tag_title=LOWER('" + search.get(i) + "')";
                    countRowsAllPostsHTagSearch = countRowsAllPostsHTagSearch.substring(0, 157) + resQuery +
                            countRowsAllPostsHTagSearch.substring(157);
                }
            }

            PreparedStatement ps = getPrepareStatement(countRowsAllPostsHTagSearch);
            if(search.size() == 0){
                ps.setString(1, "%");
            }
            else{
                ps.setString(1, search.get(0));
            }
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        }else{
            if(search.size() > 1) {
                for (int i = 1; i < search.size(); i++) {
                    String resQuery = " OR (LOWER(title) LIKE LOWER('%" + search.get(i) + "%') OR LOWER(text) LIKE LOWER('%" + search.get(i) + "%'))";
                    countRowsAllPostsWordsSearch = countRowsAllPostsWordsSearch.substring(0, 96) + resQuery + countRowsAllPostsWordsSearch.substring(96);
                }
            }

            PreparedStatement ps = getPrepareStatement(countRowsAllPostsWordsSearch);
            if(search.size() == 0){
                ps.setString(1, "%");
                ps.setString(2, "%");
            }
            else{
                ps.setString(1, "%" + search.get(0) + "%");
                ps.setString(2, "%" + search.get(0) + "%");
            }
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        }

        return 0;
    }

    public int getMaxId() throws SQLException {
        final String GET_MAX_ID = "SELECT id_post FROM posts WHERE id_post = (select max(id_post) FROM posts)";
        PreparedStatement ps = getPrepareStatement(GET_MAX_ID);
        ResultSet rs = ps.executeQuery();
        int result = -1;
        if(rs.next()){
            result = rs.getInt(1);
        }
        return result;
    }

    public class TagDAO extends GeneralDAO<Tag, Integer>{

        private final String SELECT_ALL_TAGS = "SELECT * FROM tags";
        private final String SELECT_BY_ID = "SELECT * FROM tags WHERE tag_id = ?";
        private final String DELETE_BY_ID = "DELETE FROM tags WHERE tag_id = ?";
        private final String UPDATE_TAG = "UPDATE tags set tag_title = ? "
                + "where tag_id = ?";
        private final String INSERT_TAG = "INSERT INTO tags (tag_title) "
                + "VALUES (LOWER(?))";

        @Override
        public List<Tag> getAll() throws SQLException {
            List<Tag> tags = new ArrayList<>();
            PreparedStatement ps = getPrepareStatement(SELECT_ALL_TAGS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag(rs.getString(2));
                tag.setId_tag(rs.getInt(1));
                tags.add(tag);
            }
            return tags;
        }

        @Override
        public Tag update(Tag entity) throws SQLException {
            PreparedStatement ps = getPrepareStatement(UPDATE_TAG);

            ps.setString(1, entity.getTag_title());
            ps.setInt(2, entity.getId_tag());
            ps.executeUpdate();

            return getEntityById(entity.getId_tag());
        }

        @Override
        public Tag getEntityById(Integer id) throws SQLException {
            PreparedStatement ps = getPrepareStatement(SELECT_BY_ID);
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();
            Tag tag = new Tag("default");
            if (rs.next()) {
                tag.setTag_title(rs.getString(2));
                tag.setId_tag(rs.getInt(1));
            }
            return tag;
        }

        @Override
        public boolean delete(Integer id) throws SQLException {
            PreparedStatement ps = getPrepareStatement(DELETE_BY_ID);
            ps.setInt(1, id.intValue());
            int result = ps.executeUpdate();
            return result != 0;
        }

        @Override
        public boolean insert(Tag entity) throws SQLException {
            PreparedStatement ps = getPrepareStatement(INSERT_TAG);
            ps.setString(1, entity.getTag_title());
            int result = ps.executeUpdate();
            return result != 0;
        }

        //posts_tags table

        private final String SELECT_TAGS_BY_POST_ID = "SELECT * FROM posts_tags WHERE post_id = ?";
        private final String INSERT_TAG_POST = "INSERT INTO posts_tags (tag_id, post_id) VALUES (?, ?)";
        private final String SELECT_TAG_BY_TITLE = "SELECT * FROM tags WHERE tag_title = LOWER(?)";

        public List<Tag> getAllTagsByPost(int id_post) throws SQLException {
            List<Tag> tags = new ArrayList<>();
            PreparedStatement ps = getPrepareStatement(SELECT_TAGS_BY_POST_ID);
            ps.setInt(1, id_post);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag("default");
                int tag_id = rs.getInt(2);
                tag.setTag_title(getEntityById(tag_id).getTag_title());
                tag.setId_tag(tag_id);

                tags.add(tag);
            }
            return tags;
        }

        public boolean insertToPost_Tag(int tag_id, int id_post) throws SQLException {
            PreparedStatement ps = getPrepareStatement(INSERT_TAG_POST);
            ps.setInt(1, tag_id);
            ps.setInt(2, id_post);
            int result = ps.executeUpdate();
            return result != 0;
        }

        public Tag getTag(String tag_title) throws SQLException {
            Tag tag = new Tag("default");
            PreparedStatement ps = getPrepareStatement(SELECT_TAG_BY_TITLE);
            ps.setString(1, tag_title);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tag.setTag_title(rs.getString(2));
                tag.setId_tag(rs.getInt(1));
            }
            return tag;
        }

    }
}