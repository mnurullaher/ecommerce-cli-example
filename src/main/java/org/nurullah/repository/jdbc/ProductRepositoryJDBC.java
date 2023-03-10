package org.nurullah.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.connection.DBConnection;
import org.nurullah.model.Category;
import org.nurullah.model.Product;
import org.nurullah.repository.ProductRepository;
import org.nurullah.repository.jdbc.query.ProductQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductRepositoryJDBC implements ProductRepository {
    private final Logger logger = LogManager.getLogger();
    private final Connection connection;

    public ProductRepositoryJDBC(){
        connection = DBConnection.getConnection();
    }

    public void saveProduct(Product product){
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.saveProductQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setTimestamp(4, new Timestamp(product.getCreatedAt().getTime()));
            preparedStatement.setString(5, product.getName());
            preparedStatement.setDouble(6, product.getPrice());
            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                product.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.warn("ERROR while saving product: " + e);
        }
    }

    @Override
    public void deleteProduct(Product product) {
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.deleteFromProductCategoryList);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(ProductQuery.deleteProductQuery);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("ERROR while deleting product: " + e);
        }
    }

    @Override
    public Product findById(int id) {
        Product product = new Product();
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.findById);
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var productId = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var price = resultSet.getDouble("price");
                var createdAt = resultSet.getTimestamp("createdAt");

                product.setId(productId);
                product.setName(name);
                product.setPrice(price);
                product.setCreatedAt(createdAt);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public List<Product> listProducts(){
        List<Product> products = new ArrayList<>();
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.listProducts);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                var productId = resultSet.getInt("id");
                var productName = resultSet.getString("name");
                var productPrice = resultSet.getDouble("price");
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

    private Set<Category> findCategoriesOfProduct(int productId) {
        Set<Category> categories = new HashSet<>();
        try {
            var preparedStatement = connection.prepareStatement(ProductQuery.listProductCategories);
            preparedStatement.setInt(1, productId);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                var categoryId = resultSet.getInt("id");
                var categoryName = resultSet.getString("name");
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
