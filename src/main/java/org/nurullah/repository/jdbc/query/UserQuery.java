package org.nurullah.repository.jdbc.query;

public class UserQuery {
    public static final String saveUserQuery = "INSERT INTO users(name, email, createdAt) " +
            "VALUES(?, ?, ?)";
    public static final String deleteUserQuery = "DELETE FROM users WHERE id = ?";
    public static final String listUsersQuery = "SELECT * FROM users";
    public static final String findById = "SELECT * FROM users WHERE id = ?";
}