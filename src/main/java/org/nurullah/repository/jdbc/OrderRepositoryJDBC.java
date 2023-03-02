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

    public void saveOrder(Order order, Map<Integer, Integer> itemMap){
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.saveOrderQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setTimestamp(2, new Timestamp(order.getCreatedAt().getTime()));
            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                order.setId(resultSet.getInt(1));
            }

            preparedStatement = connection.prepareStatement(OrderQuery.saveOrderItemsQuery);
            for (Map.Entry<Integer, Integer> items : itemMap.entrySet()){
                preparedStatement.setInt(1, order.getId());
                preparedStatement.setInt(2, items.getKey());
                preparedStatement.setInt(3, items.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            logger.warn("ERROR while saving order: " + e);
        }
    }

    @Override
    public void saveOrder(Order order) {
        throw new RuntimeException("not implemented!");
    }

    public void deleteOrder(int orderId){
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.deleteFromOrderItems);
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(OrderQuery.deleteOrderQuery);
            preparedStatement.setInt(1, orderId);
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
    public void addProductsToOrder(int orderId, Map<Integer, Integer> itemMap) {
        try {
            var preparedStatement = connection.prepareStatement(
                    OrderQuery.addProductsToOrderQuery);
            for (Map.Entry<Integer, Integer> item : itemMap.entrySet()){
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, item.getKey());
                preparedStatement.setInt(3, item.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            logger.warn("ERROR while adding products to order: " + e);
        }
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
