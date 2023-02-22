package org.nurullah.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static final Logger logger = LogManager.getLogger();
    private static String driver;
    private static String url;
    private static String userName;
    private static String password;

    static {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/database.properties");
            properties.load(inputStream);

            driver = properties.getProperty("db_driver");
            url = properties.getProperty("db_url");
            userName = properties.getProperty("db_username");
            password = properties.getProperty("db_password");
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            System.exit(-1);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.error("Database driver not found.\n ERROR: " + e);
            System.exit(-1);
        }

        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            logger.error("ERROR while establishing connection: " + e);
            System.exit(-1);
        }
        return connection;
    }

    public static void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.warn("ERROR while closing result set " + e);
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.warn("ERROR while closing prepared statement: " + e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.warn("ERROR while closing connection: " + e);
            }
        }
    }
}
