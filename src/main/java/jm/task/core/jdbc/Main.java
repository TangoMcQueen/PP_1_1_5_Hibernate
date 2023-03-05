package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.util.ArrayList;
import java.util.List;

public class Main extends Util {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        List<User> tempList = new ArrayList<>();
        tempList.add(new User("Иван", "Иванов", (byte) 21));
        tempList.add(new User("Анастасия", "Михайлова", (byte) 27));
        tempList.add(new User("Сергей", "Стешин", (byte) 44));
        tempList.add(new User("Евгений", "Зорин", (byte) 38));
        tempList.forEach(x -> {
            userService.saveUser(x.getName(), x.getLastName(), x.getAge());
            System.out.format("Пользователь с именем – %s %s добавлен в базу данных%n", x.getName(), x.getLastName());
        });
        userService.getAllUsers().forEach(user -> System.out.println(user.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
