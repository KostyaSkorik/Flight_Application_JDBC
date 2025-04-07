package by.javaguru.je.jdbc.dao;

import by.javaguru.je.jdbc.entity.Flight;
import by.javaguru.je.jdbc.entity.FlightStatus;
import by.javaguru.je.jdbc.exceptions.DaoException;
import by.javaguru.je.jdbc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
    private static final FlightDao INSTANCE = new FlightDao();
    private static final String FIND_ALL_SQL = """
            SELECT id, flight_no, departure_date,
                   departure_airport_code, arrival_date,
                   arrival_airport_code, aircraft_id, status
            FROM flight
            """;
    private static final String SAVE_SQL = """
            INSERT INTO flight (flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String DELETE_SQL = """
            DELETE FROM flight
            WHERE id = ?;
            """;
    private static final String FIND_BY_ID = FIND_ALL_SQL + """
            WHERE id = ?;
            """;
    private static final String UPDATE_SQL = """
            UPDATE flight
            SET flight_no = ?,
                departure_date = ?,
                departure_airport_code = ?,
                arrival_date = ?,
                arrival_airport_code = ?,
                aircraft_id = ?,
                status = ?
            WHERE id = ?;
            """;

    @Override
    public Flight save(Flight flight) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement preparedStatement = db.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            SetStatementForFlightWithoutId(flight, preparedStatement);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                flight.setId(resultSet.getLong("id"));
            }
            return flight;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
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
    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flights.add(buildFlight(resultSet));
            }
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {
        try (Connection db = ConnectionManager.get()) {
            return findById(id, db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Optional<Flight> findById(Long id, Connection db) {
        Flight flight = null;
        try (PreparedStatement statement = db.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flight = buildFlight(resultSet);
            }
            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(Flight flight) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement preparedStatement = db.prepareStatement(UPDATE_SQL)) {
            SetStatementForFlightWithoutId(flight, preparedStatement);
            preparedStatement.setLong(8, flight.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void SetStatementForFlightWithoutId(Flight flight, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, flight.getFlightNo());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
        preparedStatement.setLong(3, flight.getDepartureAirportCode());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
        preparedStatement.setLong(5, flight.getArrivalAirportCode());
        preparedStatement.setLong(6, flight.getAircraftId());
        preparedStatement.setString(7, flight.getStatus().name());
    }


    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return new Flight(
                resultSet.getLong("id"),
                resultSet.getLong("flight_no"),
                resultSet.getTimestamp("departure_date").toLocalDateTime(),
                resultSet.getLong("departure_airport_code"),
                resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                resultSet.getLong("arrival_airport_code"),
                resultSet.getLong("aircraft_id"),
                FlightStatus.valueOf(resultSet.getString("status")));
    }

    private FlightDao() {

    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }
}
