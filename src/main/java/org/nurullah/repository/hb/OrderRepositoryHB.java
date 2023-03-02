package org.nurullah.repository.hb;

import org.hibernate.Session;
import org.nurullah.model.Order;
import org.nurullah.model.OrderItem;
import org.nurullah.model.Product;
import org.nurullah.model.User;
import org.nurullah.repository.OrderRepository;

import java.util.List;
import java.util.Map;

public class OrderRepositoryHB implements OrderRepository {
    private final Session session;

    public OrderRepositoryHB(Session session) {
        this.session = session;
    }

    @Override
    public void saveOrder(Order order, Map<Integer, Integer> itemMap) {
        if (checkInvalidProduction(itemMap)) return;
        var txn = session.beginTransaction();
        var user = session.find(User.class, order.getUserId());
        session.persist(order);
        for (Map.Entry<Integer, Integer> item : itemMap.entrySet()){
            var orderItem = new OrderItem(order.getId(), item.getKey(), item.getValue());
            order.getOrderItems().add(orderItem);
            session.persist(orderItem);
        }
        user.addOrder(order);
        session.persist(order);
        txn.commit();
    }

    public void addProductsToOrder(int orderId, Map<Integer, Integer> itemMap){
        if (checkInvalidProduction(itemMap)) return;
        var txn = session.beginTransaction();
        var order = session.find(Order.class, orderId);
        for (Map.Entry<Integer, Integer> item : itemMap.entrySet()){
            var orderItem = new OrderItem(order.getId(), item.getKey(), item.getValue());
            order.getOrderItems().add(orderItem);
            session.persist(orderItem);
        }
        session.persist(order);
        txn.commit();
    }

    private boolean checkInvalidProduction(Map<Integer, Integer> itemMap) {
        for (Map.Entry<Integer, Integer> item : itemMap.entrySet()){
            if (session.find(Product.class, item.getKey()) == null) {
                System.out.println("""
                        Order couldn't be created!
                        Invalid Product ID Detected
                        """);
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteOrder(int orderId) {
        var txn = session.beginTransaction();
        var order = session.find(Order.class, orderId);
        var user = session.find(User.class, order.getUserId());
        user.removeOrder(order);
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
}
