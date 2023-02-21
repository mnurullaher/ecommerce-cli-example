package org.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.User;
import org.repository.UserRepository;
import java.time.Instant;
import java.util.Date;

public class UserService {
    private final Logger logger = LogManager.getLogger();
    UserRepository userRepository = new UserRepository();
    public void createUser(String name, String email){
        User user = new User(name, email, Date.from(Instant.now()));
        userRepository.saveUser(user);
        logger.info("User with id number " + user.getUserId() +  " has succesflly saved");
    }

    public void deleteUser(int userId){
        userRepository.deleteUser(userId);
        logger.info("User with id number " + userId + " has succesfully deleted");
    }
    public void listUsers(){
        var users = userRepository.listUsers();
        users.forEach(user ->{
            System.out.println(user.toString());
        });
    }
}
