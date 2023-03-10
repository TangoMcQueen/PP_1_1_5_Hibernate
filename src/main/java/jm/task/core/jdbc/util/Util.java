package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static final String URL = "jdbc:mysql://localhost:3306/mydbjpp1.1.4";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    //подключение JDBC
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Соединение не установленно");
            e.printStackTrace();
        }
        return connection;
    }
    //подключение Hibernate
    private static final SessionFactory sessionFactory = buildMySessionFactory();
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    public static SessionFactory buildMySessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, DIALECT);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(settings).build();
                MetadataSources metadataSources = new MetadataSources(serviceRegistry);
                metadataSources.addAnnotatedClass(User.class);
                Metadata metadata = metadataSources.buildMetadata();
                System.out.println("SessionFactory successfully created");
                return metadata.getSessionFactoryBuilder().build();

            } catch (HibernateException e) {
                System.out.println("Creation error SessionFactory");
                e.printStackTrace();
            }
        } else {
            System.out.println("Previously created SessionFactory");
        }
        return sessionFactory;
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}