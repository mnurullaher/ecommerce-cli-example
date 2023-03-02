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

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

//    public void createOrder(int userId, Map<Integer, Integer> itemMap){
//        Order order = new Order(userId, Date.from(Instant.now()));
//        orderRepository.saveOrder(order, itemMap);
//    }

    public void createOrder(int userId, Map<Integer, Integer> itemMap) {
        var order = new Order();
        order.setUserId(userId);
        itemMap.forEach((productId, quantity) -> {
            var oi = new OrderItem(productId, quantity);
            order.getOrderItems().add(oi);
        });
        orderRepository.saveOrder(order);
    }

    public void deleteOrder(int orderId){
        orderRepository.deleteOrder(orderId);
    }

    public List<Order> listOrders(){
        return orderRepository.listOrders();
    }

    public void addProductsToOrder(int orderId, Map<Integer, Integer> itemMap){
        orderRepository.addProductsToOrder(orderId, itemMap);
    }
}
