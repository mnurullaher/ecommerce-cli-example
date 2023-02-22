package org.nurullah;

import org.nurullah.service.CategoryService;
import org.nurullah.service.ProductService;
import org.nurullah.service.UserService;

import java.util.Scanner;

public class Operation {
    Scanner scanner = new Scanner(System.in);
    UserService userService = new UserService();
    ProductService productService = new ProductService();
    CategoryService categoryService = new CategoryService();

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
        userService.listUsers().forEach(user -> System.out.println(user.toString()));;
    }

    public void createProduct(){
        System.out.println("Name: ");
        var name = scanner.next();

        System.out.println("Price: ");
        var price = scanner.nextDouble();

        productService.createProduct(name, price);
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
}
