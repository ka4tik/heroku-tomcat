package examples;

import messenger.db.Database;
import messenger.model.Message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCExample {

    public static void main(String[] argv) throws java.sql.SQLException {

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/test", "root", "root");

//            connection = DriverManager.getConnection("jdbc:postgresql://localhost/singalk", "singalk", "");

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        if (connection != null) {
            java.sql.Statement stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM users";
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println(name + " with email " + email);
            }
            rs.close();
            stmt.close();

            Message message = new Message(1, "hello", "ka4tik");
            System.out.println(addMessage(message, connection));

            System.out.println(getAllMessages(connection));
            connection.close();
        }


    }

    public static Message addMessage(Message message, Connection con) {
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("INSERT INTO messages (message,created,author) VALUES(?,?,?)");

            p.setString(1, message.getMessage());
            p.setDate(2, sqlDate);
            p.setString(3, message.getMessage());
            boolean ok = p.execute();

            p = con.prepareStatement("select id,created from  messages where message = ? and created = ? and author = ?");
            p.setString(1, message.getMessage());
            p.setDate(2, sqlDate);
            p.setString(3, message.getMessage());
            ResultSet resultSet = p.executeQuery();
            resultSet.next();
            message.setId(resultSet.getInt("id"));
            message.setCreated(resultSet.getDate("created"));
            resultSet.close();
            p.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static List<Message> getAllMessages(Connection connection) {

        try {
//            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM messages";
            ResultSet rs = stmt.executeQuery(sql);

            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                messages.add(new Message(rs.getInt("id"),rs.getString("message"),rs.getDate("created"),rs.getString("author")));
            }
            rs.close();
            stmt.close();
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}