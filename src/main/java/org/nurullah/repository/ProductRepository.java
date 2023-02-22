package org.nurullah.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.connection.DBConnection;
import org.nurullah.model.Product;
import org.nurullah.repository.query.ProductQuery;

import java.sql.*;

public class ProductRepository {
    private final Logger logger = LogManager.getLogger();
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ProductRepository(){
        connection = DBConnection.getConnection();
    }

    public void saveProduct(Product product){
        try {
            preparedStatement = connection.prepareStatement(ProductQuery.saveProductQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, new Timestamp(product.getCreatedAt().getTime()));
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                product.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.warn("ERROR while saving product: " + e);
        }
    }

    public void deleteProduct(int productId){
        try {
            preparedStatement = connection.prepareStatement(ProductQuery.deleteProductQuery);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("ERROR while deleting product: " + e);
        }
    }
}
