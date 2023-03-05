package org.nurullah.repository;

import org.nurullah.model.Order;

import java.util.List;

public interface OrderRepository {
    void saveOrder(Order order);
    void deleteOrder(Order order);
    List<Order> listOrders();
    Order findById(int givenId);
}
