package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf8");
        BlogDao blogDao = new BlogDao();
        List<Blog> blogs = new ArrayList<>();
        String query = req.getParameter("blogId");
        if(query == null){
            try {
                blogs = blogDao.selectAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(blogs);
            resp.getWriter().write(jsonString);
        }else{
            Blog blog = new Blog();
            try {
                blog = blogDao.selectOne(Integer.parseInt(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(blog);
            resp.getWriter().write(jsonString);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        Blog blog = new Blog();
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        HttpSession httpSession = req.getSession(false);
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            resp.setStatus(403);
            resp.sendRedirect("login.html");
        }
        if (title == null || title.equals("") || content == null || content.equals("")) {
            resp.setStatus(400);
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("请求中的标题或正文不完整");
            return;
        }
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUserId(user.getUserId());
        BlogDao blogDao = new BlogDao();
        try {
            blogDao.insert(blog);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("blog_list.html");
    }
}
