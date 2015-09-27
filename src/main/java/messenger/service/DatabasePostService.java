package messenger.service;

import messenger.db.Database;
import messenger.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabasePostService implements PostService {


    @Override
    public Post addPost(Post post) {
        try {

            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("INSERT INTO posts (content,created,author) VALUES(?,?,?)");

            p.setString(1, post.getContent());
            p.setTimestamp(2, timestamp);
            p.setString(3, post.getAuthor());
            boolean ok = p.execute();

            p = con.prepareStatement("SELECT id,created FROM  posts WHERE content = ? AND created = ? AND author = ?");
            p.setString(1, post.getContent());
            p.setTimestamp(2, timestamp);
            p.setString(3, post.getAuthor());
            ResultSet resultSet = p.executeQuery();
            resultSet.next();
            post.setId(resultSet.getInt("id"));
            post.setCreated(new Date(resultSet.getTimestamp("created").getTime()));
            resultSet.close();
            p.close();
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Post> getAllPosts() {

        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM posts ORDER BY id";
            ResultSet rs = stmt.executeQuery(sql);

            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                posts.add(new Post(rs.getInt("id"), rs.getString("content"), new Date(rs.getTimestamp("created").getTime()), rs.getString("author")));
            }
            rs.close();
            stmt.close();
            return posts;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Post> getAllPostsForYear(int year) {
        return null;
    }

    @Override
    public List<Post> getAllPostsPaginated(int start, int size) {
        return null;
    }

    @Override
    public Post getPost(long id) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("SELECT * FROM  posts WHERE id = ?");
            p.setInt(1, (int) id);
            ResultSet rs = p.executeQuery();
            rs.next();
            Post post = new Post(rs.getInt("id"), rs.getString("content"), new Date(rs.getTimestamp("created").getTime()), rs.getString("author"));
            rs.close();
            p.close();
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public Post updatePost(Post post) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("UPDATE posts SET author= ?, content =? WHERE id = ?");
            p.setString(1, post.getAuthor());
            p.setString(2, post.getContent());
            p.setInt(3, (int) post.getId());
            boolean ok = p.execute();
            p.close();
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post removePost(long id) {
        try {
            Connection con = Database.getConnection();
            Post post = getPost(id);
            PreparedStatement p = con.prepareStatement("DELETE FROM posts WHERE id = ?");
            p.setInt(1, (int) id);
            boolean ok = p.execute();
            p.close();
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
