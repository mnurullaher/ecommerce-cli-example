package org.nurullah.service;

import org.nurullah.model.Order;
import org.nurullah.repository.OrderRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public void createOrder(int userId, String status){
        Order order = new Order(userId, status, Date.from(Instant.now()));
        orderRepository.saveOrder(order, userId);
    }

    public void addProductsToOrder(int orderId, List<Integer> productIds){
        orderRepository.addProductsToOrder(orderId, productIds);
    }

    public void deleteOrder(int orderId){
        orderRepository.deleteOrder(orderId);
    }

    public List<Order> listOrders(){
        return orderRepository.listOrders();
    }
}
