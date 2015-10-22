package messenger.db;

import messenger.model.Post;
import messenger.service.DatabasePostService;
import messenger.service.PostService;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    public static void initConnection() throws SQLException, URISyntaxException {
        String onHeroku = System.getenv().get("ON_HEROKU");
        if (onHeroku != null && onHeroku.equals("1")) {
            System.out.println("Using Heroku");
            connection = getHerokuConnection();
        }
        else {
            String onRDS = System.getenv().get("ON_RDS");
            if (onRDS != null && onRDS.equals("1")) {
                System.out.println("Using RDS");
                connection = getHerokuConnection();
            }
            else {
                System.out.println("Using Local database");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/singalk", "singalk", "");
            }
        }
    }

    private static Connection getHerokuConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    //database url format
    //postgres://username:pass@messenger.cwtgzmczwgw8.us-west-2.rds.amazonaws.com:5432/tablename
    private static Connection getAWSConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }


    public static Connection getConnection() {
        return connection;
    }

    public static void main(String args[]) throws URISyntaxException, SQLException {
         connection = getAWSConnection();
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

            Post post = new Post(1, "hello", "ka4tik");
            new DatabasePostService().addPost(post);
        }
    }
}
