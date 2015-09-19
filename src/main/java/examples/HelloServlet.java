package examples;

@javax.servlet.annotation.WebServlet(name = "hello", urlPatterns = {"/hello"})
public class HelloServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, java.io.IOException {
        String name = req.getParameter("name");
        resp.getWriter().println("Hello " + name);
    }
}
