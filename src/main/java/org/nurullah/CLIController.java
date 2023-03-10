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
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;

    public CLIController(UserService userService, ProductService productService,
                         CategoryService categoryService, OrderService orderService){
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
    }

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
        if (userService.findById(id) == null) {
            logger.error("Invalid user ID");
            return;
        }
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

        productService.createProduct(name, price);
        logger.info("The product " + name + " has successfully saved");
    }

    public void deleteProduct(){
        System.out.println("The ID of product you want to delete");
        var id = scanner.nextInt();

        if (productService.findById(id) == null){
            logger.error("Invalid product ID");
            return;
        }
        productService.deleteProduct(id);
        logger.info("The product with id number " + id + " has successfully deleted");
    }

    public void updateProduct(){
        System.out.print("The ID of the product you want to update: ");
        var id = scanner.nextInt();

        if (productService.findById(id) == null){
            logger.error("Invalid product ID");
            return;
        }

        scanner.nextLine();
        System.out.print("New name: ");
        var newName = scanner.nextLine();
        System.out.print("New price: ");
        var newPrice = scanner.nextDouble();
        productService.updateProduct(id, newName, newPrice);
        logger.info("Product updated successfully.");
    }

    public void listProducts() {
        var products = productService.listProducts();
        Table.render(products, Product.class).run();
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
        if (categoryService.findById(id) == null) {
            logger.error("Invalid category ID");
            return;
        }
        categoryService.deleteCategory(id);
        logger.info("Category with id number " + id + " has successfully deleted");
    }

    public void updateCategory(){
        System.out.print("The ID of the category you want to update: ");
        var id = scanner.nextInt();
        if (categoryService.findById(id) == null) {
            logger.error("Invalid category ID");
            return;
        }
        scanner.nextLine();
        System.out.print("New name: ");
        var newName = scanner.nextLine();
        categoryService.updateCategory(id, newName);
        logger.info("Category updated successfully.");
    }

    public void listCategories(){
        var categories = categoryService.listCategories();
        Table.render(categories, Category.class).run();
    }

    public void addProductsToCategory(){
        System.out.println("Specify the ID of the category you want to add products: ");
        var categoryId = scanner.nextInt();
        if (categoryService.findById(categoryId) == null) {
            logger.error("Invalid category ID");
            return;
        }
        System.out.println("Enter the IDs of the products you want to add");
        var products = scanner.next();
        String[] productArray = products.split(",");
        var productIds = Arrays.stream(productArray).map(Integer::parseInt).toList();
        categoryService.addProductsToCategory(categoryId, productIds);
        logger.info("Products added to the category");
    }

    public void createOrder(){
        System.out.println("The ID of the owner of the order: ");
        var userId = scanner.nextInt();
        scanner.nextLine();

        Map<Integer, Integer> itemMap = new HashMap<>();
        System.out.print("Hom many products you want to add this order: ");
        var numberOfItems = scanner.nextInt();
        setItemMap(itemMap, numberOfItems);
        orderService.createOrder(userId,itemMap);
        logger.info("Order successfully saved");
    }

    public void addProductsToOrder(){
        System.out.print("The ID of the order you want to add products: ");
        var orderId = scanner.nextInt();
        if (orderService.findById(orderId) == null){
            logger.error("Invalid order ID");
            return;
        }
        Map<Integer, Integer> itemMap = new HashMap<>();
        System.out.println("Hom many products you want to add this order");
        var numberOfItems = scanner.nextInt();
        setItemMap(itemMap, numberOfItems);
        orderService.addProductsToOrder(orderId, itemMap);

        logger.info("Order successfully updated.");
    }

    private void setItemMap(Map<Integer, Integer> itemMap, int numberOfItems) {
        for (int i = 0; i < numberOfItems; i++) {
            System.out.printf("""
                    Enter the id of %s. product
                    """, i+1);
            var productId = scanner.nextInt();
            if (productService.findById(productId) == null){
                System.out.println("Invalid product ID");
                continue;
            }
            System.out.printf("""
                    Enter the quantity of %s. product
                    """, i+1);
            var quantity = scanner.nextInt();
            itemMap.put(productId, quantity);
        }
    }

    public void deleteOrder(){
        System.out.println("Please enter the ID of the order you want to delete");
        var id = scanner.nextInt();
        if (orderService.findById(id) == null) {
            logger.error("Invalid order ID");
            return;
        }
        orderService.deleteOrder(id);
        logger.info("Order with id number " + id + " successfully deleted");
    }

    public void listOrders() {
        var orders = orderService.listOrders();
        Table.render(orders, Order.class).run();
    }
}