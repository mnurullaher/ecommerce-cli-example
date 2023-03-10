package org.nurullah.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.connection.DBConnection;
import org.nurullah.model.Order;
import org.nurullah.repository.OrderRepository;
import org.nurullah.repository.jdbc.query.OrderQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepositoryJDBC implements OrderRepository {
    private final Logger logger = LogManager.getLogger();
    private final Connection connection;

    public OrderRepositoryJDBC(){
        connection = DBConnection.getConnection();
    }

    @Override
    public void saveOrder(Order order) {
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.saveOrderQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setInt(2, order.getUserId());
            preparedStatement.setTimestamp(3, new Timestamp(order.getCreatedAt().getTime()));
            preparedStatement.setInt(4, order.getUserId());
            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                order.setId(resultSet.getInt(1));
            }

            saveOrderItems(order);

        } catch (SQLException e) {
            logger.warn("ERROR while saving order: " + e);
        }

    }

    private void saveOrderItems(Order order){
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.saveOrderItemsQuery);
            order.getOrderItems().forEach(oi ->{
                try {
                    preparedStatement.setInt(1, order.getId());
                    preparedStatement.setInt(2, oi.getProductId());
                    preparedStatement.setInt(3, oi.getQuantity());
                    preparedStatement.addBatch();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            logger.warn("ERROR while saving order: " + e);
        }
    }

    @Override
    public void deleteOrder(Order order) {
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.deleteFromOrderItems);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(OrderQuery.deleteOrderQuery);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("ERROR while deleting order: " + e);
        }
    }

    public List<Order> listOrders(){
        List<Order> orders = new ArrayList<>();
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.listOrdersQuery);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                var orderId = resultSet.getInt("id");
                var userId = resultSet.getInt("userId");
                var createdAt = resultSet.getTimestamp("createdAt");

                Order order = new Order(userId, createdAt);
                order.setId(orderId);
                order.setItems(findItemsOfOrder(orderId));
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.warn("ERROR while listing orders: " + e);
        }
        return orders;
    }

    @Override
    public Order findById(int id) {
        Order order = new Order();
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.findById);
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var orderId = resultSet.getInt("id");
                var userId = resultSet.getInt("userId");
                var createdAt = resultSet.getTimestamp("createdAt");

                order.setId(orderId);
                order.setUserId(userId);
                order.setCreatedAt(createdAt);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    private Map<String , Integer> findItemsOfOrder(int orderId){
        Map<String, Integer> itemMap = new HashMap<>();
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.listOrderItemsQuery);
            preparedStatement.setInt(1, orderId);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                var productName = resultSet.getString("name");
                var quantity = resultSet.getInt("quantity");

                itemMap.put(productName, quantity);
            }
        } catch (SQLException e) {
            logger.warn("ERROR while searching the items of the order with id " + orderId + ": " + e);
        }
        return itemMap;
    }
}
