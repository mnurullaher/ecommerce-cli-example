package org.nurullah.service;

import org.nurullah.model.Order;
import org.nurullah.repository.OrderRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderService {
    OrderRepository orderRepository = new OrderRepository();

    public void createOrder(int userId, String status, Map<Integer, Integer> itemMap){
        Order order = new Order(userId, status, Date.from(Instant.now()));
        orderRepository.saveOrder(order, itemMap);
    }
}
