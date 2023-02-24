package org.nurullah;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.model.Category;
import org.nurullah.model.Order;
import org.nurullah.model.Product;
import org.nurullah.model.User;
import org.nurullah.service.CategoryService;
import org.nurullah.service.OrderService;
import org.nurullah.service.ProductService;
import org.nurullah.service.UserService;
import pl.mjaron.etudes.Table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLIController {
    private final Logger logger = LogManager.getLogger();
    Scanner scanner = new Scanner(System.in);
    UserService userService = new UserService();
    ProductService productService = new ProductService();
    CategoryService categoryService = new CategoryService();
    OrderService orderService = new OrderService();

    public void createUser() {
        System.out.print("Name: ");
        var name = scanner.nextLine();

        System.out.print("e-mail: ");
        var email = scanner.nextLine();

        userService.createUser(name, email);
        logger.info("User " + name + " has successfully saved");
    }

    public void deleteUser() {
        System.out.print("The ID of user you want to delete: ");
        var id = scanner.nextInt();
        userService.deleteUser(id);
        logger.info("User with id number " + id + " has successfully deleted");
    }

    public void listUsers() {
        var users = userService.listUsers();
        Table.render(users, User.class).run();
    }

    public void createProduct(){
        System.out.println("Name: ");
        var name = scanner.nextLine();

        System.out.println("Price: ");
        var price = scanner.nextDouble();

        System.out.println("Enter the category IDs of this product (Separate each id with commas) ");
        var categories = scanner.next();
        String[] categoryArray = categories.split(",");
        var categoryIds = Arrays.stream(categoryArray).map(Integer::parseInt).toList();

        productService.createProduct(name, price, categoryIds);
        logger.info("The product " + name + " has successfully saved");
    }

    public void deleteProduct(){
        System.out.println("The ID of product you want to delete");
        var id = scanner.nextInt();
        productService.deleteProduct(id);
        logger.info("The product with id number " + id + " has successfully deleted");
    }

    public void createCategory(){
        System.out.println("Name: ");
        var name= scanner.nextLine();
        categoryService.createCategory(name);
        logger.info("The category " + name + " has successfully saved");
    }

    public void deleteCategory(){
        System.out.println("The id of the category you want to delete: ");
        var id = scanner.nextInt();
        categoryService.deleteCategory(id);
        logger.info("Category with id number " + id + " has successfully deleted");
    }

    public void listCategories(){
        var categories = categoryService.listCategories();
        Table.render(categories, Category.class).run();
    }

    public void listProducts() {
        var products = productService.listProducts();
        Table.render(products, Product.class).run();
    }

    public void createOrder(){
        System.out.println("The ID of the owner of the order: ");
        var userId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("The status of the order: ");
        var status = scanner.nextLine();

        Map<Integer, Integer> itemMap = new HashMap<>();
        System.out.println("Hom many products you want to add this order");
        var numberOfOrders = scanner.nextInt();
        for (int i = 0; i < numberOfOrders; i++) {
            System.out.printf("""
                    Enter the id of %s. product
                    """, i+1);
            var id = scanner.nextInt();
            System.out.printf("""
                    Enter the quantity of %s. product
                    """, i+1);
            var quantity = scanner.nextInt();
            itemMap.put(id, quantity);
        }
        orderService.createOrder(userId, status, itemMap);
        logger.info("Order successfully saved");
    }

    public void deleteOrder(){
        System.out.println("Please enter the ID of the order you want to delete");
        var id = scanner.nextInt();
        orderService.deleteOrder(id);
        logger.info("Order with id number " + id + " successfully deleted");
    }

    public void listOrders() {
        var orders = orderService.listOrders();
        Table.render(orders, Order.class).run();
    }
}
