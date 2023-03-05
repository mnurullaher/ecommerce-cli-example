package org.nurullah.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.connection.DBConnection;
import org.nurullah.repository.UserRepository;
import org.nurullah.repository.jdbc.query.UserQuery;
import org.nurullah.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJDBC implements UserRepository {
    private final Logger logger = LogManager.getLogger();
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UserRepositoryJDBC() {
        connection = DBConnection.getConnection();
    }

    public void saveUser(User user) {
        try {
            preparedStatement = connection.prepareStatement(UserQuery.saveUserQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setTimestamp(3, new Timestamp(user.getCreatedAt().getTime()));
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.warn("ERROR while saving user: " + e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            preparedStatement = connection.prepareStatement(UserQuery.deleteUserQuery);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("ERROR while deleting user: " + e);
        }
    }

    public List<User> listUsers() {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(UserQuery.listUsersQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("name");
                String userEmail = resultSet.getString("email");
                Timestamp createdAt = resultSet.getTimestamp("createdAt");

                User user = new User(userName, userEmail, createdAt);
                user.setId(userId);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.warn("ERROR while listing users: " + e);
        }
        return users;
    }

    @Override
    public User findById(int id) {
        User user = new User();
        try {
            var preparedStatement = connection.prepareStatement(UserQuery.findById);
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var userId = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var email = resultSet.getString("email");
                var createdAt = resultSet.getTimestamp("createdAt");

                user.setId(userId);
                user.setName(name);
                user.setEmail(email);
                user.setCreatedAt(createdAt);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
