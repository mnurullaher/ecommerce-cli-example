package org.nurullah.repository.query;

public class UserQuery {
    public static final String saveUserQuery = "INSERT INTO users(user_name, user_email, createdAt) " +
            "VALUES(?, ?, ?)";
    public static final String deleteUserQuery = "DELETE FROM users WHERE user_id = ?";
    public static final String listUsersQuery = "SELECT * FROM users";
}