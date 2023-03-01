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
        productService.deleteProduct(id);
        logger.info("The product with id number " + id + " has successfully deleted");
    }

    public void updateProduct(){
        System.out.print("The ID of the product you want to update: ");
        var id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("New name: ");
        var newName = scanner.nextLine();
        System.out.print("New price: ");
        var newPrice = scanner.nextDouble();
        productService.updateProduct(id, newName, newPrice);
        logger.info("Product updated successfully.");
    }

    public void createCategory(){
        System.out.println("Name: ");
        var name= scanner.nextLine();

        categoryService.createCategory(name);
        logger.info("The category " + name + " has successfully saved");
    }

    public void addProductsToCategory(){
        System.out.println("Specify the ID of the category you want to add products: ");
        var categoryId = scanner.nextInt();

        System.out.println("Enter the IDs of the products you want to add");
        var products = scanner.next();
        String[] productArray = products.split(",");
        var productIds = Arrays.stream(productArray).map(Integer::parseInt).toList();

        categoryService.addProductsToCategory(categoryId, productIds);
        logger.info("Products added to the category");
    }

    public void deleteCategory(){
        System.out.println("The id of the category you want to delete: ");
        var id = scanner.nextInt();
        categoryService.deleteCategory(id);
        logger.info("Category with id number " + id + " has successfully deleted");
    }

    public void updateCategory(){
        System.out.print("The ID of the category you want to update: ");
        var id = scanner.nextInt();
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

        orderService.createOrder(userId, status);
        logger.info("Order successfully saved");
    }

    public void deleteOrder(){
        System.out.println("Please enter the ID of the order you want to delete");
        var id = scanner.nextInt();
        orderService.deleteOrder(id);
        logger.info("Order with id number " + id + " successfully deleted");
    }

    public void addProductsToOrder(){
        System.out.print("Enter the ID of the order you want to add product: ");
        var categoryId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the IDs of the products you want to add: ");
        var products = scanner.next();
        var productArray = products.split(",");
        var productIds = Arrays.stream(productArray).map(Integer::parseInt).toList();
        orderService.addProductsToOrder(categoryId, productIds);
    }

    public void listOrders() {
        var orders = orderService.listOrders();
        Table.render(orders, Order.class).run();
    }
}
