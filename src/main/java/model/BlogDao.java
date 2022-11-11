package model;

import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogDao {
    public void insert(Blog blog) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        //
        connection = DBUtil.getConnection();
        //
        String sql = "insert into blog values(null,?,?,?,now())";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,blog.getTitle());
        preparedStatement.setString(2,blog.getContent());
        preparedStatement.setInt(3,blog.getUserId());

        int ret = preparedStatement.executeUpdate();
        if(ret == 1){
            System.out.println("successful!");
        }else{
            System.out.println("failure!");
        }
        DBUtil.close(connection, preparedStatement, null);
    }

    public List<Blog> selectAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Blog> blogList = new ArrayList<>();
        connection = DBUtil.getConnection();
        String sql = "select * from blog order by postTime desc";
        preparedStatement = connection.prepareStatement(sql);

        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Blog blog = new Blog();
            blog.setTitle(resultSet.getString("title"));
            blog.setUserId(resultSet.getInt("userId"));
            blog.setBlogId(resultSet.getInt("blogId"));
            String content = resultSet.getString("content");
            if(content.length() > 100){
                content = content.substring(0,100);
            }
            blog.setContent(content);
            blog.setPostTime(resultSet.getTimestamp("timestamp"));
            blogList.add(blog);
        }
        DBUtil.close(connection,preparedStatement,resultSet);

        return blogList;
    }

    public Blog selectOne(int blogId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        connection = DBUtil.getConnection();
        String sql = "select * from blog where blogId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,blogId);

        resultSet = preparedStatement.executeQuery();

        Blog blog = new Blog();
        if(resultSet.next()){

            blog.setPostTime(resultSet.getTimestamp("postTime"));
            blog.setContent(resultSet.getString("content"));
            blog.setBlogId(resultSet.getInt("blogId"));
            blog.setUserId(resultSet.getInt("userId"));
            blog.setTitle(resultSet.getString("title"));
        }

        DBUtil.close(connection,preparedStatement,resultSet);
        return blog;
    }

    public void delete(int blogId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        connection = DBUtil.getConnection();
        String sql = "delete * from blog where blogId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,blogId);
        int ret = preparedStatement.executeUpdate();
        if(ret == 1){
            System.out.println("successful");
        }else{
            System.out.println("failure");
        }
        DBUtil.close(connection,preparedStatement,null);
    }
}
