package org.nurullah.repository;

import org.hibernate.Session;
import org.nurullah.model.Order;
import org.nurullah.model.Product;
import org.nurullah.model.User;

import java.util.List;
import java.util.Map;

public class OrderRepositoryHB implements OrderRepository{
    private final Session session;

    public OrderRepositoryHB(Session session) {
        this.session = session;
    }

    @Override
    public void saveOrder(Order order, Map<Integer, Integer> itemMap) {

    }

    @Override
    public void saveOrder(Order order, int userId) {
        var txn = session.beginTransaction();
        var user = session.find(User.class, userId);
        user.addOrder(order);
        session.persist(order);
        txn.commit();
    }

    public void addProductsToOrder(int orderId, List<Integer> productIds){
        var txn = session.beginTransaction();
        Order order = session.find(Order.class, orderId);
        for (var id : productIds){
            var product = session.find(Product.class, id);
            order.getProducts().add(product);
        }
        session.persist(order);
        txn.commit();
    }

    @Override
    public void deleteOrder(int orderId) {
        var txn = session.beginTransaction();
        var order = session.find(Order.class, orderId);
        order.getUser().removeOrder(order);
        session.remove(order);
        txn.commit();
    }

    @Override
    public List<Order> listOrders() {
        return null;
    }

}
