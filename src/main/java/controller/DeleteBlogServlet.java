package controller;

import model.BlogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete")
public class DeleteBlogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blogId = req.getParameter("blogId");
        BlogDao blogDao = new BlogDao();
        try {
            blogDao.delete(Integer.parseInt(blogId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("blog_list.html");
    }
}
