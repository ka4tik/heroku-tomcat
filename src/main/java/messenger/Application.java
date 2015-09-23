package messenger;

import messenger.db.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Application extends HttpServlet {

    public void init() throws ServletException {
        System.out.println("----------");
        System.out.println("---------- Initializing Database ----------");
        try {
            Database.initConnection();
        } catch (Exception e) {
            throw new RuntimeException("unable to init database");
        }
        System.out.println("---------- Initialized Database successfully ----------");
        System.out.println("----------");
    }
}
