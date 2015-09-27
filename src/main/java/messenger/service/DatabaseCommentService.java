package messenger.service;

import messenger.db.Database;
import messenger.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseCommentService implements CommentService {
    @Override
    public List<Comment> getAllComments(long messageId) {

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM  comments WHERE messageid = ?");
            stmt.setInt(1, (int) messageId);
            ResultSet rs = stmt.executeQuery();

            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                comments.add(new Comment(rs.getLong("id"), rs.getString("message"), new Date(rs.getTimestamp("created").getTime()), rs.getString("author")));
            }
            rs.close();
            stmt.close();
            return comments;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Comment getComment(long messageId, long commentId) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM  comments WHERE messageid = ? and id = ?");
            stmt.setInt(1, (int) messageId);
            stmt.setInt(2, (int) commentId);

            ResultSet rs = stmt.executeQuery();

            List<Comment> comments = new ArrayList<>();
            if (rs.next())
                return new Comment(rs.getLong("id"), rs.getString("message"), new Date(rs.getTimestamp("created").getTime()), rs.getString("author"));
            else
                return null;


        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Comment addComment(long messageId, Comment comment) {
        try {
            Connection connection = Database.getConnection();

            PreparedStatement p = connection.prepareStatement("INSERT INTO comments (messageid, message,created,author) VALUES(?,?,?,?)");
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            p.setInt(1, (int) messageId);
            p.setString(2, comment.getMessage());
            p.setTimestamp(3, timestamp);
            p.setString(4, comment.getAuthor());
            boolean ok = p.execute();

            p = connection.prepareStatement("SELECT id,created FROM  comments WHERE messageid = ? AND message = ? AND created = ? AND author = ?");
            p.setInt(1,(int) messageId);
            p.setString(2, comment.getMessage());
            p.setTimestamp(3, timestamp);
            p.setString(4, comment.getAuthor());
            ResultSet resultSet = p.executeQuery();
            resultSet.next();
            comment.setId(resultSet.getInt("id"));
            comment.setCreated(new Date(resultSet.getTimestamp("created").getTime()));
            resultSet.close();
            p.close();
            return comment;
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Comment updateComment(long messageId, Comment comment) {
        return null;
    }

    @Override
    public Comment removeComment(long messageId, long commentId) {
        return null;
    }
}
