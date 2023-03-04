package org.nurullah.repository.hb;

import org.hibernate.Session;
import org.nurullah.model.User;
import org.nurullah.repository.UserRepository;

import java.util.List;

public class UserRepositoryHB implements UserRepository {

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
    public void deleteUser(User user) {
        var txn = session.beginTransaction();
        session.remove(user);
        txn.commit();
    }

    @Override
    public void deleteUser(int userID) {

    }

    @Override
    public List<User> listUsers() {
        return session.createQuery("select u from users u", User.class)
            .list();
    }

    public User findById(int id) {
        var txn = session.beginTransaction();
        var user = session.find(User.class, id);
        txn.commit();
        return user;
    }
}
