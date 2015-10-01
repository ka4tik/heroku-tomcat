package messenger.service;

import messenger.db.Database;
import messenger.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class AuthenticationService {

    private PostService postService = new DatabasePostService();
    private UserService userService = new DatabaseUserService();

    public String getToken(String username, String password) {
        try {
            String sql = "select password from users where username = ?";
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
             password = md5(password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String retrieved_password = resultSet.getString("password");
                if (retrieved_password.equals(password)) {
                    String token = UUID.randomUUID().toString();
                    if (saveAuthToken(username, token))
                        return token;
                    else
                        return null;
                } else
                    return null;
            } else
                return null;
        } catch (Exception e) {
            return null;
        }

    }

    private boolean saveAuthToken(String username, String token) {
        try {
            String sql = "INSERT into tokens (token,username) values (?,?)";
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            ;
            throw new RuntimeException(e);
        }
    }

    public User getTokenOwner(String token) {
        try {
            String sql = "select username from tokens where token = ?";
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                return userService.getUser(username);
            } else
                return null;
        } catch (Exception e) {
            return null;
        }
    }


    public boolean authenticateMessageRequest(long messageId, String authHeader) {
        if (null == authHeader)
            return false;
        final String token = authHeader.replaceFirst("Bearer ", "");
        String author = postService.getAuthor(messageId);
        User user = getTokenOwner(token);
        return user != null && user.getUsername().equals(author);
    }

    public void addUser(String username, String password) {
        try {
            password = md5(password);
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("INSERT INTO users (username,password,created) VALUES(?,?,?)");

            p.setString(1, username);
            p.setString(2, password);
            p.setTimestamp(3, timestamp);
            p.execute();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
