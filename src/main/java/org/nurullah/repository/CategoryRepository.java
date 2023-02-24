package org.nurullah.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.connection.DBConnection;
import org.nurullah.model.Category;
import org.nurullah.repository.query.CategoryQuery;

import java.sql.*;

public class CategoryRepository {
    private final Logger logger = LogManager.getLogger();
    private final Connection connection;

    public CategoryRepository(){
        connection = DBConnection.getConnection();
    }

    public void saveCategory(Category category){
        try {
            var preparedStatement = connection.prepareStatement(CategoryQuery.saveCategoryQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, category.getId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setTimestamp(3, new Timestamp(category.getCreatedAt().getTime()));
            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                category.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.warn("ERROR while saving category: " + e);
        }
    }

    public void deleteCategory(int categoryId){
        try {
            var preparedStatement = connection.prepareStatement(CategoryQuery.deleteFromProductCategories);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(CategoryQuery.deleteCategoryQuery);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("ERROR while deleting category: " + e);
        }
    }
}
