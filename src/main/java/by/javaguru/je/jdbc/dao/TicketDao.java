package by.javaguru.je.jdbc.dao;

import by.javaguru.je.jdbc.entity.Ticket;
import by.javaguru.je.jdbc.exceptions.DaoException;
import by.javaguru.je.jdbc.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao {

    private final static TicketDao INSTANCE = new TicketDao();
    private final static String SAVE_SQL = """
            INSERT INTO ticket (passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?,?,?,?,?);
            """;
    private final static String DELETE_SQL = """
            DELETE FROM ticket WHERE id = ?;
            """;
    private final static String FIND_ALL_SQL = """
            SELECT id, passport_no, passenger_name, flight_id, seat_no, cost
            FROM ticket
            """;
    private final static String FIND_BY_ID_SQL = """
            SELECT id, passport_no, passenger_name, flight_id, seat_no, cost
            FROM ticket
            WHERE id = ?;
            """;
    private final static String UPDATE_SQL = """
            UPDATE ticket
            SET passport_no = ?,
                passenger_name = ?,
                flight_id = ?,
                seat_no = ?,
                cost = ?
            WHERE id = ?;
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
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    //Возращает опциональное значение билета с заданным ID, если найден, иначе возвращает пустой опциональ. Optional
    //- обертка над возможным отсутствием значения. Если значение есть, то методом get() можно получить его значение.
    //Это удобно, потому что в некоторых ситуациях метод может не находить значение и возвращать null.
    //И это может привести к ошибке при использовании этого значения.
    public Optional<Ticket> findById(Long id) {
        Ticket findTicket = null;
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                findTicket = buildTicket(resultSet);
            }

            return Optional.ofNullable(findTicket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Ticket ticket) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1,ticket.getPassportNo());
            statement.setString(2,ticket.getPassengerName());
            statement.setLong(3,ticket.getFlightId());
            statement.setLong(4,ticket.getSeatNo());
            statement.setBigDecimal(5,ticket.getCost());
            statement.setLong(6,ticket.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public static TicketDao getInstance() {
        return INSTANCE;
    }


    private TicketDao() {

    }

    private Ticket buildTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(
                resultSet.getLong("id"),
                resultSet.getLong("passport_no"),
                resultSet.getString("passenger_name"),
                resultSet.getLong("flight_id"),
                resultSet.getLong("seat_no"),
                resultSet.getBigDecimal("cost"));
    }
}
