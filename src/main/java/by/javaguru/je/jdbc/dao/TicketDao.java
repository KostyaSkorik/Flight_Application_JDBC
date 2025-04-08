package by.javaguru.je.jdbc.dao;

import by.javaguru.je.jdbc.dto.TicketFilter;
import by.javaguru.je.jdbc.entity.Flight;
import by.javaguru.je.jdbc.entity.FlightStatus;
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
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long, Ticket> {

    private final static TicketDao INSTANCE = new TicketDao();
    private final static FlightDao flightDao = FlightDao.getInstance();

    private final static String SAVE_SQL = """
            INSERT INTO ticket (passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?,?,?,?,?);
            """;
    private final static String DELETE_SQL = """
            DELETE FROM ticket WHERE id = ?;
            """;
    private final static String FIND_ALL_SQL = """
            SELECT t.id, t.passport_no, t.passenger_name, t.flight_id, t.seat_no, t.cost,
                   f.flight_no, f.departure_date,
                   f.departure_airport_code, f.arrival_date,
                   f.arrival_airport_code, f.aircraft_id, f.status
            
            FROM ticket t JOIN flight f ON t.flight_id = f.id
            """;
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE t.id = ?;
            """;
    private final static String FIND_BY_FLIGHT_ID = FIND_ALL_SQL + """
            WHERE t.flight_id = ?
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

    @Override
    public Ticket save(Ticket ticket) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            SetStatementForTicketWithoutId(ticket, statement);
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

    private static void SetStatementForTicketWithoutId(Ticket ticket, PreparedStatement statement) throws SQLException {
        statement.setLong(1, ticket.getPassportNo());
        statement.setString(2, ticket.getPassengerName());
        statement.setLong(3, ticket.getFlight().getId());
        statement.setString(4, ticket.getSeatNo());
        statement.setBigDecimal(5, ticket.getCost());
    }

    @Override
    public boolean delete(Long id) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet,db));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();

        if (filter.getPassengerName() != null) {
            parameters.add(filter.getPassengerName());
            whereSql.add("passenger_name = ?");
        }
        if (filter.getSeatNo() != null) {
            parameters.add("%" + filter.getSeatNo() + "%");
            whereSql.add("seat_no LIKE ?");
        }
        parameters.add(filter.getLimit());
        parameters.add(filter.getOffset());
        String filteredSql = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : " ",
                " LIMIT ? OFFSET ?"
        ));


        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(FIND_ALL_SQL + filteredSql)) {
            List<Ticket> tickets = new ArrayList<>();

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet,db));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAllByFlightId(Long flightId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(FIND_BY_FLIGHT_ID)) {
            statement.setLong(1,flightId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                tickets.add(buildTicket(resultSet,db));
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Возвращает опциональное значение билета с заданным ID, если найден, иначе возвращает пустой опциональ. Optional
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
                findTicket = buildTicket(resultSet,db);
            }

            return Optional.ofNullable(findTicket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Ticket ticket) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(UPDATE_SQL)) {
            SetStatementForTicketWithoutId(ticket, statement);
            statement.setLong(6, ticket.getId());
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

    private Ticket buildTicket(ResultSet resultSet,Connection db) throws SQLException {
        return new Ticket(
                resultSet.getLong("id"),
                resultSet.getLong("passport_no"),
                resultSet.getString("passenger_name"),
                flightDao.findById(resultSet.getLong("flight_id"), db)
                        .orElse(null),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost"));
    }
}
