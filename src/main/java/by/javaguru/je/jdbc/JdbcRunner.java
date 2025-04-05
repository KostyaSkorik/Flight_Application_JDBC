package by.javaguru.je.jdbc;


import by.javaguru.je.jdbc.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JdbcRunner {
    public static void main(String[] args) throws InterruptedException {


        ExecutorService service = Executors.newFixedThreadPool(5);
        Runnable task = () -> {
            try (Connection db = ConnectionManager.get()) {
                System.out.println("Connected " + Thread.currentThread().getName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        };
        for (int i = 0; i < 4; i++) {
            service.execute(task);
        }
        service.shutdown();
    }

    public static List<String> getTicketsByFlightId(Long cost) {
        List<String> result = new ArrayList<>();
        String sql = """
                SELECT passenger_name FROM ticket
                WHERE cost > ?
                """;
        try (Connection db = ConnectionManager.get(); PreparedStatement statement = db.prepareStatement(sql)) {
            statement.setLong(1, cost);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void writeUniqueCountries() {
        String sql = """
                SELECT DISTINCT country FROM airport""";

        try (Connection db = ConnectionManager.get(); Statement statement = db.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("country"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) {
        List<Long> result = new ArrayList<>();
        String sql = """
                SELECT id FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;
        try (Connection db = ConnectionManager.get(); PreparedStatement statement = db.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            //
            statement.setFetchSize(2);
            statement.setMaxRows(2);
            statement.setQueryTimeout(1);
            //
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void getMetadata() {
        try (Connection db = ConnectionManager.get()) {
            DatabaseMetaData metaData = db.getMetaData();
            ResultSet catalog = metaData.getCatalogs();
            while (catalog.next()) {
                System.out.println(catalog.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
