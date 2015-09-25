package messenger.service;

import messenger.db.Database;
import messenger.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseMessageService implements MessageService {


    @Override
    public Message addMessage(Message message) {
        try {

            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("INSERT INTO messages (message,created,author) VALUES(?,?,?)");

            p.setString(1, message.getMessage());
            p.setTimestamp(2, timestamp);
            p.setString(3, message.getAuthor());
            boolean ok = p.execute();

            p = con.prepareStatement("SELECT id,created FROM  messages WHERE message = ? AND created = ? AND author = ?");
            p.setString(1, message.getMessage());
            p.setTimestamp(2, timestamp);
            p.setString(3, message.getAuthor());
            ResultSet resultSet = p.executeQuery();
            resultSet.next();
            message.setId(resultSet.getInt("id"));
            message.setCreated(new Date(resultSet.getTimestamp("created").getTime()));
            resultSet.close();
            p.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Message> getAllMessages() {

        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM messages";
            ResultSet rs = stmt.executeQuery(sql);

            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                messages.add(new Message(rs.getInt("id"), rs.getString("message"), new Date(rs.getTimestamp("created").getTime()), rs.getString("author")));
            }
            rs.close();
            stmt.close();
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public List<Message> getAllMessagesForYear(int year) {
        return null;
    }

    @Override
    public List<Message> getAllMessagesPaginated(int start, int size) {
        return null;
    }

    @Override
    public Message getMessage(long id) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("SELECT * FROM  messages WHERE id = ?");
            p.setInt(1, (int) id);
            ResultSet rs = p.executeQuery();
            rs.next();
            Message message = new Message(rs.getInt("id"), rs.getString("message"), new Date(rs.getTimestamp("created").getTime()), rs.getString("author"));
            rs.close();
            p.close();
            return message;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    @Override
    public Message updateMessage(Message message) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("UPDATE messages SET author= ?, message =? WHERE id = ?");
            p.setString(1, message.getMessage());
            p.setString(2, message.getAuthor());
            p.setInt(3, (int) message.getId());
            boolean ok = p.execute();
            p.close();
            return message;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Message removeMessage(long id) {
        try {
            Connection con = Database.getConnection();
            Message message = getMessage(id);
            PreparedStatement p = con.prepareStatement("DELETE FROM  messages WHERE id = ?");
            p.setInt(1, (int) id);
            boolean ok = p.execute();
            p.close();
            return message;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
