package by.javaguru.je.jdbc.dao;

import by.javaguru.je.jdbc.entity.Ticket;
import by.javaguru.je.jdbc.exceptions.DaoException;
import by.javaguru.je.jdbc.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDao {

    private final static TicketDao INSTANCE = new TicketDao();
    private final static String SAVE_SQL = """
            INSERT INTO ticket (passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?,?,?,?,?);
            """;
    private final static String DELETE_SQL = """
            DELETE FROM ticket WHERE id = ?;
            """;


    public Ticket save(Ticket ticket) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlightId());
            statement.setLong(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.executeUpdate();

            ResultSet resultSetKeys = statement.getGeneratedKeys();
            while (resultSetKeys.next()) {
                ticket.setId(resultSetKeys.getLong("id"));
            }
            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public static TicketDao getInstance() {
        return INSTANCE;
    }


    private TicketDao() {

    }
}
