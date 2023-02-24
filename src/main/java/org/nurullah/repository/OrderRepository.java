package org.nurullah.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.connection.DBConnection;
import org.nurullah.model.Order;
import org.nurullah.repository.query.OrderQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class OrderRepository {
    private final Logger logger = LogManager.getLogger();
    private final Connection connection;

    public OrderRepository(){
        connection = DBConnection.getConnection();
    }

    public void saveOrder(Order order, Map<Integer, Integer> itemMap){
        try {
            var preparedStatement = connection.prepareStatement(OrderQuery.saveOrderQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setString(2, order.getStatus());
            preparedStatement.setTimestamp(3, new Timestamp(order.getCreatedAt().getTime()));
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
}
