package model;

import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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


}
