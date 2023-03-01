package org.nurullah.repository;

import org.hibernate.Session;
import org.nurullah.model.User;

import java.util.List;

public class UserRepositoryHB implements UserRepository{

    private final Session session;

    public UserRepositoryHB(Session session){
        this.session = session;
    }
    @Override
    public void saveUser(User user) {
        var txn = session.beginTransaction();
        session.persist(user);
        txn.commit();
    }

    @Override
    public void deleteUser(int userID) {
        var txn = session.beginTransaction();
        var user = session.find(User.class, userID);
        session.remove(user);
        txn.commit();
    }

    @Override
    public List<User> listUsers() {
        return null;
    }
}
