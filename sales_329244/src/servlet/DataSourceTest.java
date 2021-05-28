package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/DataSourceTest")
public class DataSourceTest extends HttpServlet {

    private DataSource ds = null;

    public void init() throws ServletException {
        Context ctx;
        try {
            ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:/comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/postgres");
        } catch (NamingException e) {
            throw new ServletException(e);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection con = null;
        try {
            con = ds.getConnection();
            out.println("con=" + con);
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }

    }
}
