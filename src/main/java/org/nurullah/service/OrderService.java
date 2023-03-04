package org.nurullah.service;

import org.nurullah.model.Order;
import org.nurullah.model.OrderItem;
import org.nurullah.repository.OrderRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService){
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public void createOrder(int userId, Map<Integer, Integer> itemMap) {
        var order = new Order(userId, Date.from(Instant.now()));
        itemMap.forEach((productId, quantity) -> {
            var oi = new OrderItem(productId, quantity);
            order.getOrderItems().add(oi);
        });
        orderRepository.saveOrder(order);
    }

    public void addProductsToOrder(int orderId, Map<Integer, Integer> itemMap){
        var order = orderRepository.findById(orderId);
        itemMap.forEach((productId, quantity) -> {
            var oi = new OrderItem(productId, quantity);
            order.getOrderItems().add(oi);
        });
        orderRepository.saveOrder(order);
    }

    public void deleteOrder(int orderId){
        var order = orderRepository.findById(orderId);
        var user = userService.findById(order.getUserId());
        user.removeOrder(order);
        orderRepository.deleteOrder(order);
    }

    public List<Order> listOrders(){
        return orderRepository.listOrders();
    }

    public Order findById(int id) {
        return orderRepository.findById(id);
    }
}
