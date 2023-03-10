package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 21);
        userService.saveUser("Anastasia", "Mihailova", (byte) 27);
        userService.saveUser("Sergey", "stechin", (byte) 44);
        userService.saveUser("Evgeniy", "Zorin", (byte) 38);
        userService.getAllUsers().forEach(user -> System.out.println(user.toString()));

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
