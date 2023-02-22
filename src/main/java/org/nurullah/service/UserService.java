package org.nurullah.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.model.User;
import org.nurullah.repository.UserRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class UserService {
    private final Logger logger = LogManager.getLogger();
    UserRepository userRepository = new UserRepository();

    public void createUser(String name, String email) {
        User user = new User(name, email, Date.from(Instant.now()));
        userRepository.saveUser(user);
        logger.info("User with id number " + user.getId() + " has succesflly saved");
    }

    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
        logger.info("User with id number " + userId + " has succesfully deleted");
    }

    public List<User> listUsers() {
        return userRepository.listUsers();
    }
}
