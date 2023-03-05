package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS user (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NOT NULL,
                      `age` INT(3) NULL,
                      PRIMARY KEY (`id`));""");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу user");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pStatement = getConnection().prepareStatement(
                "INSERT INTO user (name, lastName, age) VALUES(?, ?, ?)")) {
            pStatement.setString(1, name);
            pStatement.setString(2, lastName);
            pStatement.setByte(3, age);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("При сохранении пользователя произошла ошибка");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pStatement = getConnection().prepareStatement(
                "DELETE FROM user WHERE id = ?")) {
            pStatement.setLong(1, id);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("При удалении пользователя произошла ошибка");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT name, lastName, age FROM user");
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("При получении списка пользователей произошла ошибка");
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM user");
        } catch (SQLException e) {
            System.out.println("При очистке таблицы произошла ошибка");
            e.printStackTrace();
        }
    }
}
