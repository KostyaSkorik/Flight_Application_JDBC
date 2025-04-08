package by.javaguru.je.jdbc.utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    //2 способа получить данные из проперти файла
    //первый:
    //    private static final String URL = PropertiesUtil.get("db.url");
    //    private static final String USER = PropertiesUtil.get("db.username");
    //    private static final String PASSWORD = PropertiesUtil.get("db.password");

    //второй:
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final ResourceBundle rd = ResourceBundle.getBundle("application");
    private static final String URL = rd.getString("db.url");
    private static final String USER = rd.getString("db.username");
    private static final String PASSWORD = rd.getString("db.password");
    private static final int POOL_SIZE = Integer.parseInt(rd.getString("db.pool.size"));
    private static BlockingQueue<Connection> pool;
    private static final Object monitor = new Object();

    //выполняется 1 раз при запуске класса
    static {
        loadDriver();
        initConnectionPool();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private static void initConnectionPool() {
        int size = POOL_SIZE > 0 ? POOL_SIZE : DEFAULT_POOL_SIZE;
        pool = new ArrayBlockingQueue<>(size);
        /*
        Каждое реальное соединение требует свой прокси.
        Если создать один прокси для всех соединений,
        все они будут ссылаться на одно и то же соединение,
        что приведет к ошибкам.
         */
        for (int i = 0; i < size; i++) {
            Connection connection = open();
            ClassLoader loader = ConnectionManager.class.getClassLoader();
            Class<?>[] interfaces = {Connection.class};
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(loader, interfaces,
                    ((proxy, method, args) ->
                        method.getName().equals("close")? pool.add((Connection) proxy):
                                method.invoke(connection, args)));

            pool.add(proxyConnection);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static Connection open() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {

    }
}
