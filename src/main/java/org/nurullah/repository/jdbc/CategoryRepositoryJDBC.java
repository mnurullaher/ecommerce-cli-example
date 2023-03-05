package org.nurullah.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.connection.DBConnection;
import org.nurullah.model.Category;
import org.nurullah.model.Product;
import org.nurullah.repository.CategoryRepository;
import org.nurullah.repository.jdbc.query.CategoryQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryRepositoryJDBC implements CategoryRepository {
    private final Logger logger = LogManager.getLogger();
    private final Connection connection;

    public CategoryRepositoryJDBC(){
        connection = DBConnection.getConnection();
    }

    public void saveCategory(Category category){
        try {
            var preparedStatement = connection.prepareStatement(CategoryQuery.saveCategoryQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, category.getId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setTimestamp(3, new Timestamp(category.getCreatedAt().getTime()));
            preparedStatement.setString(4, category.getName());
            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                category.setId(resultSet.getInt(1));
            }
            var productIds = category.getProducts().stream().map(Product::getId).toList();
            addProductsToCategory(category.getId(), productIds);

        } catch (SQLException e) {
            logger.warn("ERROR while saving category: " + e);
        }
    }
    private void addProductsToCategory(int categoryId, List<Integer> productIds) {
        try {
            var preparedStatement = connection.prepareStatement(
                    CategoryQuery.addProductsToCategoryQuery);
            for (var productId : productIds){
                preparedStatement.setInt(1, categoryId);
                preparedStatement.setInt(2, productId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            logger.warn("ERROR while adding products to category: " + e);
        }
    }

    @Override
    public void deleteCategory(Category category) {
        try {
            var preparedStatement = connection.prepareStatement(CategoryQuery.deleteFromProductCategories);
            preparedStatement.setInt(1, category.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(CategoryQuery.deleteCategoryQuery);
            preparedStatement.setInt(1, category.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("ERROR while deleting category: " + e);
        }
    }

    @Override
    public Category findById(int id) {
        var category = new Category();
        try {
            var preparedStatement = connection.prepareStatement(
                    CategoryQuery.findByIdQuery);
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                var categoryId = resultSet.getInt("id");
                var categoryName = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("createdAt");
                category.setId(categoryId);
                category.setName(categoryName);
                category.setCreatedAt(createdAt);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    public List<Category> listCategories(){
        List<Category> categories = new ArrayList<>();
        try {
            var preparedStatement = connection.prepareStatement(CategoryQuery.listCategoriesQuery);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                var categoryID = resultSet.getInt("id");
                var categoryName = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("createdAt");

                Category category = new Category(categoryName, createdAt);
                category.setId(categoryID);
                category.setProducts(getProductsOfCategory(categoryID));
                categories.add(category);
            }
        } catch (SQLException e) {
            logger.warn("ERROR while listing categories: " + e);
        }
        return categories;
    }

    private Set<Product> getProductsOfCategory(int categoryId) {
        Set<Product> products = new HashSet<>();
        try {
            var preparedStatement = connection.prepareStatement(
                    CategoryQuery.listProductsOfCategory);
            preparedStatement.setInt(1, categoryId);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var price = resultSet.getDouble("price");
                var createdAt = resultSet.getTimestamp("createdAt");
                Product product = new Product();
                product.setId(id);
                product.setName(name);
                product.setPrice(price);
                product.setCreatedAt(createdAt);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.warn("ERROR while searching products in this category");
        }
        return products;
    }
}
