package messenger.service;

import messenger.db.Database;
import messenger.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseUserService implements UserService {
    @Override
    public List<User> getAllUsers() {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM users ORDER BY username";
            ResultSet rs = stmt.executeQuery(sql);

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getString("username")
                        , rs.getString("firstname")
                        , rs.getString("lastName")
                        , new Date(rs.getTimestamp("created").getTime())
                ));
            }
            rs.close();
            stmt.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(String username) {
        if(username == null)
            return null;
        try {
            Connection con = Database.getConnection();
            PreparedStatement p = con.prepareStatement("SELECT * FROM  users WHERE username = ?");
            p.setString(1, username);
            ResultSet rs = p.executeQuery();
            rs.next();
            User user = new User(rs.getString("username")
                    , rs.getString("firstname")
                    , rs.getString("lastName")
                    , new Date(rs.getTimestamp("created").getTime())
            );
            rs.close();
            p.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User removeUser(String username) {
        try {
            Connection con = Database.getConnection();
            User user = getUser(username);
            PreparedStatement p = con.prepareStatement("DELETE FROM users WHERE username = ?");
            p.setString(1, username);
            boolean ok = p.execute();
            p.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
