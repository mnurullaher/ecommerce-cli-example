package org.nurullah.repository;

import org.nurullah.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository {
    void saveOrder(Order order);
    void deleteOrder(int orderId);
    List<Order> listOrders();
    void addProductsToOrder(int orderId, Map<Integer, Integer> itemMap);
}
