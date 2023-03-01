package org.nurullah.repository;

import org.nurullah.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository {
    void saveOrder(Order order, Map<Integer, Integer> itemMap);
    void deleteOrder(int orderId);
    List<Order> listOrders();
    public void saveOrder(Order order, int userId);
}
