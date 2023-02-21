package org.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.connection.DBConnection;
import org.model.Queries.UserQuery;
import org.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepository {
    private final Logger logger = LogManager.getLogger();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void saveUser(User user){
        connection = DBConnection.getConnection();
        //INSERT INTO users(user_name, user_email, createdAt) " +
        //            "VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(UserQuery.saveUserQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserEmail());
            preparedStatement.setTimestamp(3,  new Timestamp(user.getCreatedAt().getTime()));
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                user.setUserId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.warn("ERROR while saving user: " + e);
        } finally {
            DBConnection.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    public void deleteUser(int userID){
        connection = DBConnection.getConnection();
//        DELERE FROM users WHERE user_id = ?"
        try {
            preparedStatement = connection.prepareStatement(UserQuery.deleteUserQuery);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("ERROR while deleting user: " + e);
        } finally {
            DBConnection.closeConnection(connection, preparedStatement, null);
        }
    }

    public List<User> listUsers(){
        connection = DBConnection.getConnection();
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(UserQuery.listUsersQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userEmail = resultSet.getString("user_email");
                Date createdAt = resultSet.getDate("createdAt");

                User user = new User(userName, userEmail, createdAt);
                user.setUserId(userId);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.warn("ERROR while listing users: " + e);
        } finally {
            DBConnection.closeConnection(connection, preparedStatement, resultSet);
        }
        return users;
    }
}
