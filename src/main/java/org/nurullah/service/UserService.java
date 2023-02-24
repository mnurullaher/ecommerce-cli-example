package org.nurullah.service;

import org.nurullah.model.User;
import org.nurullah.repository.UserRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public void createUser(String name, String email) {
        User user = new User(name, email, Date.from(Instant.now()));
        userRepository.saveUser(user);
    }

    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }

    public List<User> listUsers() {
        return userRepository.listUsers();
    }
}
