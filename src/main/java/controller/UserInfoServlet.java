package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blogId = req.getParameter("blogId");

        HttpSession httpSession = req.getSession(false);
        User user = new User();
        user = (User) httpSession.getAttribute("user");
        if(httpSession == null){
            resp.setStatus(403);
            return;
        }
        if(user == null){
            resp.setStatus(403);
            return;
        }
        if(blogId == null){
            user.setPassword("");
            resp.setContentType("application/json;charset=utf8");
            String jsonString = objectMapper.writeValueAsString(user);
            resp.getWriter().write(jsonString);
        }else{
            BlogDao blogDao = new BlogDao();
            Blog blog = new Blog();
            try {
                blog = blogDao.selectOne(Integer.parseInt(blogId));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int userId = blog.getUserId();
            UserDao userDao = new UserDao();
            User blogUser = new User();
            try {
                blogUser = userDao.selectByUserId(userId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(blogUser == null){
                resp.setContentType("text/html;charset=utf8");
                resp.setStatus(403);
                resp.getWriter().write("该篇文章的作者不存在");
                return;
            }else{
                blogUser.setPassword("");
                if(user.getUserId() == blogUser.getUserId()){
                    blogUser.setIsYourBlog(1);
                }else{
                    blogUser.setIsYourBlog(0);
                }
                String jsonString = objectMapper.writeValueAsString(blogUser);
                resp.setContentType("application/json;charset=utf8");
                resp.getWriter().write(jsonString);
                System.out.println(jsonString);
            }

        }
    }
}
