package org.nurullah;

import org.nurullah.repository.CategoryRepositoryJDBC;
import org.nurullah.repository.OrderRepositoryJDBC;
import org.nurullah.repository.ProductRepositoryJDBC;
import org.nurullah.repository.UserRepositoryJDBC;
import org.nurullah.service.CategoryService;
import org.nurullah.service.OrderService;
import org.nurullah.service.ProductService;
import org.nurullah.service.UserService;

import java.util.Scanner;

public class ApplicationEntryPoint {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CLIController controller = new CLIController(
                new UserService(new UserRepositoryJDBC()),
                new ProductService(new ProductRepositoryJDBC()),
                new CategoryService(new CategoryRepositoryJDBC()),
                new OrderService(new OrderRepositoryJDBC()));
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
                if (selectedOperation == 1) controller.createUser();
                else if (selectedOperation == 2) controller.deleteUser();
                else if (selectedOperation == 3) controller.listUsers();
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
                if (selectedOperation == 1) controller.createProduct();
                else if (selectedOperation == 2) controller.deleteProduct();
                else if (selectedOperation == 4) controller.listProducts();
            } else if (selectedEntity == 3) {
                System.out.println("""
                        Please select the operation you want to execute
                        [1]\tCreate
                        [2]\tDelete
                        [3]\tUpdate
                        [4]\tList
                        """);
                var selectedOperation = scanner.nextInt();
                if (selectedOperation == 1) controller.createCategory();
                else if (selectedOperation == 2) controller.deleteCategory();
                else if (selectedOperation == 4) controller.listCategories();
            } else if (selectedEntity == 4) {
                System.out.println("""
                        Please select the operation you want to execute
                        [1]\tCreate
                        [2]\tDelete
                        [3]\tUpdate
                        [4]\tList
                        """);
                var selectedOperation = scanner.nextInt();
                if (selectedOperation == 1) controller.createOrder();
                else if (selectedOperation == 2) controller.deleteOrder();
                else if (selectedOperation == 4) controller.listOrders();
            } else System.out.println("Invalid Selection!");
        }
    }
}
