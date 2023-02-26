package org.nurullah.service;

import org.nurullah.model.Order;
import org.nurullah.repository.OrderRepositoryJDBC;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final OrderRepositoryJDBC orderRepository;

    public OrderService(OrderRepositoryJDBC orderRepository){
        this.orderRepository = orderRepository;
    }

    public void createOrder(int userId, String status, Map<Integer, Integer> itemMap){
        Order order = new Order(userId, status, Date.from(Instant.now()));
        orderRepository.saveOrder(order, itemMap);
    }

    public void deleteOrder(int orderId){
        orderRepository.deleteOrder(orderId);
    }

    public List<Order> listOrders(){
        return orderRepository.listOrders();
    }
}
