package by.javaguru.je.jdbc.dao;

import by.javaguru.je.jdbc.entity.User;
import by.javaguru.je.jdbc.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (name, birthday, email, password, role, gender) VALUES (?,?,?,?,?,?)
            """;

    @Override
    public User save(User user) {
        try (Connection db = ConnectionManager.get();
             PreparedStatement statement = db.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setObject(2, user.getBirthday());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, String.valueOf(user.getRole()));
            statement.setString(6, String.valueOf(user.getGender()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getObject("id", Integer.class));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }


    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean update(User user) {
        return false;
    }


    public static UserDao getInstance() {
        return INSTANCE;
    }
}
