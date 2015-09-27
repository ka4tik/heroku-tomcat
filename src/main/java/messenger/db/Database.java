package messenger.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    public static void initConnection() throws SQLException, URISyntaxException {
        String onHeroku = System.getenv().get("ON_HEROKU");
        if (onHeroku != null && onHeroku.equals("1"))
            connection = getHerokuConnection();
        else
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/singalk", "singalk", "");
//          connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/test", "root", "root");
    }

    private static Connection getHerokuConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    public static Connection getConnection() {
        return connection;
    }
}
