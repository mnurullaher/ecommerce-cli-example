package org.nurullah.repository.hb;

import org.hibernate.Session;
import org.nurullah.model.Order;
import org.nurullah.repository.OrderRepository;

import java.util.List;

public class OrderRepositoryHB implements OrderRepository {
    private final Session session;

    public OrderRepositoryHB(Session session) {
        this.session = session;
    }

    public void saveOrder(Order order) {
        var txn = session.beginTransaction();
        session.persist(order);
        txn.commit();
    }

    @Override
    public void deleteOrder(int orderId) {

    }

    @Override
    public void deleteOrder(Order order) {
        var txn = session.beginTransaction();
        session.remove(order);
        txn.commit();
    }

    @Override
    public List<Order> listOrders() {
        var txn = session.beginTransaction();
        var orders = session.createQuery("SELECT o FROM orders o", Order.class)
                .getResultList();
        txn.commit();
        return orders;
    }

    public Order findById(int id) {
        var txn = session.beginTransaction();
        var order = session.find(Order.class, id);
        txn.commit();
        return order;
    }
}
