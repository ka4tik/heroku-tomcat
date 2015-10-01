package messenger.service;

import messenger.db.Database;
import messenger.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseCommentService implements CommentService {
    @Override
    public List<Comment> getAllComments(long postId) {

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM  comments WHERE postId = ?");
            stmt.setInt(1, (int) postId);
            ResultSet rs = stmt.executeQuery();

            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                comments.add(new Comment(rs.getLong("id"), rs.getString("content"), new Date(rs.getTimestamp("created").getTime()), rs.getString("author")));
            }
            rs.close();
            stmt.close();
            return comments;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Comment getComment(long postId, long commentId) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM  comments WHERE postId = ? and id = ?");
            stmt.setInt(1, (int) postId);
            stmt.setInt(2, (int) commentId);

            ResultSet rs = stmt.executeQuery();

            List<Comment> comments = new ArrayList<>();
            if (rs.next())
                return new Comment(rs.getLong("id"), rs.getString("content"), new Date(rs.getTimestamp("created").getTime()), rs.getString("author"));
            else
                return null;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Comment addComment(long postId, Comment comment) {
        try {
            Connection connection = Database.getConnection();

            PreparedStatement p = connection.prepareStatement("INSERT INTO comments (postid, content,created,author) VALUES(?,?,?,?)");
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            p.setInt(1, (int) postId);
            p.setString(2, comment.getContent());
            p.setTimestamp(3, timestamp);
            p.setString(4, comment.getAuthor());
            boolean ok = p.execute();

            p = connection.prepareStatement("SELECT id,created FROM  comments WHERE postid = ? AND content = ? AND created = ? AND author = ?");
            p.setInt(1, (int) postId);
            p.setString(2, comment.getContent());
            p.setTimestamp(3, timestamp);
            p.setString(4, comment.getAuthor());
            ResultSet resultSet = p.executeQuery();
            resultSet.next();
            comment.setId(resultSet.getInt("id"));
            comment.setCreated(new Date(resultSet.getTimestamp("created").getTime()));
            resultSet.close();
            p.close();
            return comment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Comment updateComment(long postId, Comment comment) {
        return null;
    }

    @Override
    public Comment removeComment(long postId, long commentId) {
        return null;
    }
}
