package org.nurullah;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nurullah.model.Order;
import org.nurullah.model.Product;
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
        var name = scanner.next();

        System.out.print("e-mail: ");
        var email = scanner.next();

        userService.createUser(name, email);
    }

    public void deleteUser() {
        System.out.print("The ID of user you want to delete: ");
        var id = scanner.nextInt();
        userService.deleteUser(id);
    }

    public void listUsers() {
        userService.listUsers().forEach(user -> System.out.println(user.toString()));
    }

    public void createProduct(){
        System.out.println("Name: ");
        var name = scanner.next();

        System.out.println("Price: ");
        var price = scanner.nextDouble();

        System.out.println("Enter the category IDs of this product (Seperate each id with commas) ");
        var categories = scanner.next();
        String[] categoryArray = categories.split(",");
        var categoryIds = Arrays.stream(categoryArray).map(Integer::parseInt).toList();

        productService.createProduct(name, price, categoryIds);
    }

    public void deleteProduct(){
        System.out.println("The ID of product you want to delete");
        var id = scanner.nextInt();
        productService.deleteProduct(id);
    }

    public void createCategory(){
        System.out.println("Name: ");
        var name= scanner.next();
        categoryService.createCategory(name);
    }

    public void deleteCategory(){
        System.out.println("The id of the category you want to delete: ");
        var id = scanner.nextInt();
        categoryService.deleteCategory(id);
    }

    public void listProducts() {
        var products = productService.listProducts();
        Table.render(products, Product.class).run();
    }

    public void createOrder(){
        System.out.println("The ID of the owner of the order: ");
        var userId = scanner.nextInt();

        System.out.println("The status of the order: ");
        var status = scanner.next();

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

    public void listOrders() {
        var orders = orderService.listOrders();
        Table.render(orders, Order.class).run();
    }
}
