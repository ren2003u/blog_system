package model;

import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public User selectByName(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        connection = DBUtil.getConnection();
        String sql = "select * from user where userId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,username);

        resultSet = preparedStatement.executeQuery();
        User user = new User();
        if(resultSet.next()){
            user.setUserId(resultSet.getInt("userId"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
        }
        DBUtil.close(connection,preparedStatement,resultSet);
        return user;
    }

    public User selectByUserId(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        connection = DBUtil.getConnection();
        String sql = "select * from user where userId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,userId);

        resultSet = preparedStatement.executeQuery();
        User user = new User();
        if(resultSet.next()){
            user.setUserId(resultSet.getInt("userId"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
        }
        DBUtil.close(connection,preparedStatement,resultSet);
        return user;
    }
}
