package org.test;

import org.service.UserService;
import java.util.Scanner;

public class Operation {
    Scanner scanner = new Scanner(System.in);
    UserService userService = new UserService();

    public void createUser(){
        System.out.print("Name: ");
        var name = scanner.next();

        System.out.print("e-mail: ");
        var email = scanner.next();

        userService.createUser(name, email);
    }

    public void deleteUser(){
        System.out.print("The ID of user you want to delete: ");
        var id = scanner.nextInt();
        userService.deleteUser(id);
    }
    public void listUsers(){
        userService.listUsers();
    }
}
