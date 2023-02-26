package org.nurullah.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.connection.DBConnection;
import org.nurullah.model.Category;
import org.nurullah.model.Product;
import org.nurullah.repository.query.ProductQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryJDBC {
    private final Logger logger = LogManager.getLogger();
    private final Connection connection;

    public ProductRepositoryJDBC(){
        connection = DBConnection.getConnection();
    }

    public void saveProduct(Product product, List<Integer> categories){
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.saveProductQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, new Timestamp(product.getCreatedAt().getTime()));
            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                product.setId(resultSet.getInt(1));
            }

            preparedStatement = connection.prepareStatement(ProductQuery.saveProductCategoryQuery);
            for (Integer category : categories){
                preparedStatement.setInt(1, product.getId());
                preparedStatement.setInt(2, category);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            logger.warn("ERROR while saving product: " + e);
        }
    }

    public void deleteProduct(int productId){
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.deleteFromProductCategoryList);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(ProductQuery.deleteFromOrderItems);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(ProductQuery.deleteProductQuery);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("ERROR while deleting product: " + e);
        }
    }

    public List<Product> listProducts(){
        List<Product> products = new ArrayList<>();
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.listProducts);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                var productId = resultSet.getInt("product_id");
                var productName = resultSet.getString("product_name");
                var productPrice = resultSet.getDouble("product_price");
                var createdAt = resultSet.getTimestamp("createdAt");

                Product product = new Product(productName, productPrice, createdAt);
                product.setId(productId);
                product.setCategories(findCategoriesOfProduct(productId));
                products.add(product);
            }
        } catch (SQLException e) {
            logger.warn("ERROR while listing products: " + e);
        }
        return products;
    }

    public List<Category> findCategoriesOfProduct(int productId) {
        List<Category> categories = new ArrayList<>();
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.listProductCategories);
            preparedStatement.setInt(1, productId);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                var categoryId = resultSet.getInt("category_id");
                var categoryName = resultSet.getString("category_name");
                var createdAt = resultSet.getTimestamp("createdAt");
                Category category = new Category(categoryName, createdAt);
                category.setId(categoryId);
                categories.add(category);
            }
        } catch (SQLException e) {
            logger.warn("ERROR while searching the categories of the product with ID: " + productId
            + "e: " + e);
        }
        return categories;
    }
}
