package org.nurullah;

import org.nurullah.repository.CategoryRepository;
import org.nurullah.repository.OrderRepository;
import org.nurullah.repository.ProductRepository;
import org.nurullah.repository.UserRepository;
import org.nurullah.service.CategoryService;
import org.nurullah.service.OrderService;
import org.nurullah.service.ProductService;
import org.nurullah.service.UserService;

import java.util.Scanner;

public class ApplicationEntryPoint {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CLIController userController = new CLIController(new UserService(new UserRepository()));
        CLIController productController = new CLIController(new ProductService(new ProductRepository()));
        CLIController orderController = new CLIController(new OrderService(new OrderRepository()));
        CLIController categoryController = new CLIController(new CategoryService(new CategoryRepository()));
        while (true) {
            System.out.println();
            System.out.println("""
                    Please select the entity that you want to working on ("0" for exit)
                    [1]\tUsers
                    [2]\tProducts
                    [3]\tCategories
                    [4]\tOrders""");
            var selectedEntity = scanner.nextInt();

            if (selectedEntity == 0) return;
            else if (selectedEntity == 1) {
                System.out.println("""
                        Please select the operation you want to execute
                        [1]\tCreate
                        [2]\tDelete
                        [3]\tList
                        """);
                var selectedOperation = scanner.nextInt();
                if (selectedOperation == 1) userController.createUser();
                else if (selectedOperation == 2) userController.deleteUser();
                else if (selectedOperation == 3) userController.listUsers();
                else System.out.println("Invalid Operation");
            } else if (selectedEntity == 2) {
                System.out.println("""
                        Please select the operation you want to execute
                        [1]\tCreate
                        [2]\tDelete
                        [3]\tUpdate
                        [4]\tList
                        """);
                var selectedOperation = scanner.nextInt();
                if (selectedOperation == 1) productController.createProduct();
                else if (selectedOperation == 2) productController.deleteProduct();
                else if (selectedOperation == 4) productController.listProducts();
            } else if (selectedEntity == 3) {
                System.out.println("""
                        Please select the operation you want to execute
                        [1]\tCreate
                        [2]\tDelete
                        [3]\tUpdate
                        [4]\tList
                        """);
                var selectedOperation = scanner.nextInt();
                if (selectedOperation == 1) categoryController.createCategory();
                else if (selectedOperation == 2) categoryController.deleteCategory();
                else if (selectedOperation == 4) categoryController.listCategories();
            } else if (selectedEntity == 4) {
                System.out.println("""
                        Please select the operation you want to execute
                        [1]\tCreate
                        [2]\tDelete
                        [3]\tUpdate
                        [4]\tList
                        """);
                var selectedOperation = scanner.nextInt();
                if (selectedOperation == 1) orderController.createOrder();
                else if (selectedOperation == 2) orderController.deleteOrder();
                else if (selectedOperation == 4) orderController.listOrders();
            } else System.out.println("Invalid Selection!");
        }
    }
}
