package org.nurullah;

import java.util.Scanner;

public class ApplicationEntryPoint {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Operation operation = new Operation();
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
                if (selectedOperation == 1) operation.createUser();
                else if (selectedOperation == 2) operation.deleteUser();
                else if (selectedOperation == 3) operation.listUsers();
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
                if (selectedOperation == 1) operation.createProduct();
                else if (selectedOperation == 2) operation.deleteProduct();
            } else if (selectedEntity == 3) {
                System.out.println("""
                        Please select the operation you want to execute
                        [1]\tCreate
                        [2]\tDelete
                        [3]\tUpdate
                        [4]\tList
                        """);
                var selectedOperation = scanner.nextInt();
                if (selectedOperation == 1) operation.createCategory();
            } else System.out.println("Invalid Selection!");
        }
    }
}
