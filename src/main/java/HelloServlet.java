
@javax.servlet.annotation.WebServlet(name = "hello", urlPatterns = {"/hello"})
public class HelloServlet extends javax.servlet.http.HttpServlet{

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, java.io.IOException {
        resp.getWriter().println("Hello world from servlet");
    }
}
