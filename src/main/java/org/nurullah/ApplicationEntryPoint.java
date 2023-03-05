package org.nurullah;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.nurullah.repository.hb.CategoryRepositoryHB;
import org.nurullah.repository.hb.OrderRepositoryHB;
import org.nurullah.repository.hb.ProductRepositoryHB;
import org.nurullah.repository.hb.UserRepositoryHB;
import org.nurullah.service.CategoryService;
import org.nurullah.service.OrderService;
import org.nurullah.service.ProductService;
import org.nurullah.service.UserService;

import java.util.List;
import java.util.Scanner;

public class ApplicationEntryPoint {

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        var registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        var factory = new MetadataSources(registry).buildMetadata()
                .buildSessionFactory();
        var session = factory.openSession();
        var userService = new UserService(new UserRepositoryHB(session));
        var productService = new ProductService(new ProductRepositoryHB(session));
        CLIController controller = new CLIController(
                userService,
                productService,
                new CategoryService(new CategoryRepositoryHB(session), productService),
                new OrderService(new OrderRepositoryHB(session), userService)
        );
        while (true) {
            System.out.println();
            session.clear();
            System.out.print("""
                    Please select the entity that you want to working on ("0" for exit)
                    [1]Users [2]Products [3]Categories [4]Orders Choice:"""
            );
            var selectedEntity = scanner.nextInt();

            if (selectedEntity == 0) {
                session.close();
                factory.close();
                return;
            }
            else if (selectedEntity == 1) {
                var selectedOperation = getChoice(
                    List.of("Create", "Delete", "List")
                );
                if (selectedOperation == 1) controller.createUser();
                else if (selectedOperation == 2) controller.deleteUser();
                else if (selectedOperation == 3) controller.listUsers();
                else System.out.println("Invalid Operation");
            } else if (selectedEntity == 2) {
                var selectedOperation = getChoice(
                    List.of("Create", "Delete", "Update", "List")
                );
                if (selectedOperation == 1) controller.createProduct();
                else if (selectedOperation == 2) controller.deleteProduct();
                else if (selectedOperation == 3) controller.updateProduct();
                else if (selectedOperation == 4) controller.listProducts();
                else System.out.println("Invalid Operation");
            } else if (selectedEntity == 3) {
                var selectedOperation = getChoice(
                    List.of("Create", "Delete", "Update", "List", "Set products")
                );
                if (selectedOperation == 1) controller.createCategory();
                else if (selectedOperation == 2) controller.deleteCategory();
                else if (selectedOperation == 3) controller.updateCategory();
                else if (selectedOperation == 4) controller.listCategories();
                else if (selectedOperation == 5) controller.addProductsToCategory();
                else System.out.println("Invalid Operation");
            } else if (selectedEntity == 4) {
                var selectedOperation = getChoice(
                    List.of("Create", "Delete", "List", "Add products")
                );
                if (selectedOperation == 1) controller.createOrder();
                else if (selectedOperation == 2) controller.deleteOrder();
                else if (selectedOperation == 3) controller.listOrders();
                else if (selectedOperation == 4) controller.addProductsToOrder();
                else System.out.println("Invalid Operation");
            } else System.out.println("Invalid Selection!");
        }
    }

    private static int getChoice(List<String> choices) {
        System.out.println("Please select the operation you want to execute");
        var choicesStr = new StringBuilder();
        var index = 1;
        for (var choice : choices) {
            choicesStr.append("[%s]%s ".formatted(index++, choice));
        }
        choicesStr.append("Choice: ");
        System.out.print(choicesStr);
        return scanner.nextInt();
    }
}
