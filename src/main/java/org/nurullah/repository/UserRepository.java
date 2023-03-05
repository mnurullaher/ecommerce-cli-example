package org.nurullah.repository;

import org.nurullah.model.User;

import java.util.List;

public interface UserRepository {
    void saveUser(User user);
    void deleteUser(User user);
    List<User> listUsers();
    User findById(int id);
}