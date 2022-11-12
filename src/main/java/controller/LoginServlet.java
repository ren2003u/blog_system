package controller;

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
import java.util.Objects;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println(username + " " + password);

        if(username == null || password == null || username.equals("") || password.equals("")){
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("输入的用户名或者密码为空");
            return;
        }
        UserDao userDao = new UserDao();
        User user = new User();
        try {
            user = userDao.selectByName(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user == null){
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("输入的用户名或者密码错误");
            return;
        }
        if(!Objects.equals(user.getPassword(), password)){
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("输入的用户名或者密码错误");
            return;
        }
        HttpSession httpSession = req.getSession(true);
        httpSession.setAttribute("user",user);
        resp.sendRedirect("blog_list.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(false);
        if(httpSession == null){
            resp.setStatus(403);
            return;
        }
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            resp.setStatus(403);
            return;
        }
        resp.setStatus(200);
    }
}
